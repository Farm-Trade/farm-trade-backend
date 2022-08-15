package com.farmtrade.services.impl.api;

import com.farmtrade.dto.*;
import com.farmtrade.dto.recovering.ForgotPasswordDto;
import com.farmtrade.dto.recovering.ResetPasswordDto;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.exceptions.UserNotActiveException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.security.jwt.JwtTokenProvider;
import com.farmtrade.services.api.UserService;
import com.farmtrade.services.smpp.TwilioService;
import com.farmtrade.utils.RandomUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private TwilioService twilioService;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${user.sendActivation}")
    private boolean sendActivation;

    public UserServiceImpl(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository, TwilioService twilioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.twilioService = twilioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @Override
    public Page<User> findPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, id.toString()));
    }

    @Override
    public User getUserByPhone(String phone) throws UsernameNotFoundException {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the phone: " + phone));
    }

    @Override
    public User updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = getUser(id);
        BeanUtils.copyProperties(userUpdateDto, user);
        return userRepository.save(user);
    }


    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }



    @Override
    public User createUser(UserCreateDto userCreateDto) {
        User user = User.builder()
                .fullName(userCreateDto.getFullName())
                .password(bCryptPasswordEncoder.encode(userCreateDto.getPassword()))
                .email(userCreateDto.getEmail())
                .phone(userCreateDto.getPhone())
                .isActive(true)
                .role(userCreateDto.getRole())
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User registration(UserCreateDto userCreateDto) throws ApiValidationException {
        if (userCreateDto.getRole() == Role.ADMIN) {
            throw new ApiValidationException("Chose another role");
        }

        User user = User.builder()
                .fullName(userCreateDto.getFullName())
                .phone(userCreateDto.getPhone())
                .email(userCreateDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userCreateDto.getPassword()))
                .role(userCreateDto.getRole())
                .build();

        if (sendActivation) {
            sendActivationCode(user);
            return user;
        }
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public void userActivation(ActivationCodeDto activationCode) throws EntityNotFoundException{
        User user = getUserByActivationCode(activationCode.getActivationCode());
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
    }

    @Override
    public TokenDto login(AuthenticationDto authenticationDto) throws UsernameNotFoundException{
        try{
            String phone = authenticationDto.getPhone();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phone, authenticationDto.getPassword()));

            User user = getUserByPhone(phone);
            List<Role> list = new ArrayList<>();
            list.add(user.getRole())  ;
            String token = jwtTokenProvider.createToken(phone, list);
            return new TokenDto(token);
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid Phone or password");
        }
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws UserNotActiveException, EntityNotFoundException {
        User user = getUserByPhone(forgotPasswordDto.getPhone());

        if (!user.isActive()) {
            throw new UserNotActiveException();
        }

        sendActivationCode(user);
    }

    @Override
    public User getUserByActivationCode(String activationCode) throws UserNotActiveException{
        return userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "activation code", activationCode));
    }

    @Override
    public User resetPassword(Long id, ResetPasswordDto resetPasswordDto) throws UserNotActiveException, EntityNotFoundException, BadRequestException{
        User user = getUser(id);
        String activationCode = resetPasswordDto.getActivationCode();
        String activationCodeFromDB = user.getActivationCode();

        if (activationCodeFromDB == null || !activationCodeFromDB.equals(activationCode)) {
            throw new BadRequestException(String.format("User is not match to %s activation code", activationCode));
        }
        if (!user.isActive()) {
            throw new UserNotActiveException();
        }
        user.setActivationCode(null);
        user.setPassword(bCryptPasswordEncoder.encode(resetPasswordDto.getPassword()));

        return userRepository.save(user);
    }

    private void sendActivationCode(User user) {
        String activationCode = RandomUtil.getRandomNumberString();
        user.setActivationCode(activationCode);
        userRepository.save(user);
        twilioService.sendVerificationMessage(user, activationCode);
    }
}



