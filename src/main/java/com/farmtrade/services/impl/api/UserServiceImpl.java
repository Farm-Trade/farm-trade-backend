package com.farmtrade.services.impl.api;

import com.farmtrade.dto.auth.ActivationCodeDto;
import com.farmtrade.dto.auth.AuthenticationDto;
import com.farmtrade.dto.auth.TokenDto;
import com.farmtrade.dto.businessdetails.UpdateBusinessDetailsDto;
import com.farmtrade.dto.recovering.ForgotPasswordDto;
import com.farmtrade.dto.recovering.ResetPasswordDto;
import com.farmtrade.dto.user.UserCreateDto;
import com.farmtrade.dto.user.UserSettingsUpdateDto;
import com.farmtrade.dto.user.UserUpdateDto;
import com.farmtrade.entities.BusinessDetails;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.PaymentType;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.*;
import com.farmtrade.repositories.BusinessDetailsRepository;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.security.jwt.JwtTokenProvider;
import com.farmtrade.services.api.UserService;
import com.farmtrade.services.security.AuthService;
import com.farmtrade.services.smpp.TwilioService;
import com.farmtrade.utils.RandomUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BusinessDetailsRepository businessDetailsRepository;
    private final boolean sendActivation;

    public UserServiceImpl(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            TwilioService twilioService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthService authService,
            BusinessDetailsRepository businessDetailsRepository,
            @Value("${user.sendActivation}")
            boolean sendActivation
    ) {
        this.userRepository = userRepository;
        this.twilioService = twilioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
        this.businessDetailsRepository = businessDetailsRepository;
        this.sendActivation = sendActivation;
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
                .orElseThrow(() -> new EntityNotFoundException(User.class, "телефоном", phone));
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
    @Transactional
    public User createUser(UserCreateDto userCreateDto) {
        User user = User.builder()
                .fullName(userCreateDto.getFullName())
                .password(bCryptPasswordEncoder.encode(userCreateDto.getPassword()))
                .email(userCreateDto.getEmail())
                .phone(userCreateDto.getPhone())
                .isActive(true)
                .role(userCreateDto.getRole())
                .build();
        if (user.getRole().equals(Role.FARMER)) {
            BusinessDetails businessDetails = BusinessDetails.builder().paymentType(PaymentType.CARD).build();
            user.setBusinessDetails(businessDetailsRepository.save(businessDetails));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User registration(UserCreateDto userCreateDto) throws ApiValidationException {
        if (userCreateDto.getRole() == Role.ADMIN) {
            throw new ApiValidationException("Вибереть іншу роль");
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
        if (user.getRole().equals(Role.FARMER)) {
            BusinessDetails businessDetails = BusinessDetails.builder().paymentType(PaymentType.CARD).build();
            user.setBusinessDetails(businessDetailsRepository.save(businessDetails));
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
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            authenticationDto.getPhone(),
                            authenticationDto.getPassword()
                    );

            authService.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Невірний телефон або пароль");
        }
        User user = userRepository.findByPhone(authenticationDto.getPhone()).orElseThrow(
                () -> new UsernameNotFoundException("Користувача з таким телефоном не інсує: " + authenticationDto.getPhone())
        );
        return buildTokenFromUser(user);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws UserNotActiveException, EntityNotFoundException {
        User user = getUserByPhone(forgotPasswordDto.getPhone());

        if (!user.isActive()) {
            throw new UserNotActiveException();
        }

        if (sendActivation) {
            sendActivationCode(user);
        }
    }

    @Override
    public User getUserByActivationCode(String activationCode) throws UserNotActiveException{
        return userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "кодом активації", activationCode));
    }

    @Override
    public User resetPassword(Long id, ResetPasswordDto resetPasswordDto) throws UserNotActiveException, EntityNotFoundException, BadRequestException{
        User user = getUser(id);
        String activationCode = resetPasswordDto.getActivationCode();
        String activationCodeFromDB = user.getActivationCode();

        if (activationCodeFromDB == null || !activationCodeFromDB.equals(activationCode)) {
            throw new BadRequestException(String.format("Користувач не сківпадє %s з цим кодом активації", activationCode));
        }
        if (!user.isActive()) {
            throw new UserNotActiveException();
        }
        user.setActivationCode(null);
        user.setPassword(bCryptPasswordEncoder.encode(resetPasswordDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public TokenDto userSettingsUpdate(UserSettingsUpdateDto userSettingsUpdateDto) {
        User user = authService.getUserFromContext();
        user.setFullName(userSettingsUpdateDto.getFullName());
        user.setEmail(userSettingsUpdateDto.getEmail());
        if (
                userSettingsUpdateDto.getPassword() != null &&
                    !bCryptPasswordEncoder.matches(userSettingsUpdateDto.getPassword(), user.getPassword())
        ) {
            user.setPassword(bCryptPasswordEncoder.encode(userSettingsUpdateDto.getPassword()));
        }
        userRepository.save(user);
        return buildTokenFromUser(authService.getUserFromContext());
    }

    @Override
    public BusinessDetails findUserBusinessDetails() {
        User user = authService.getUserFromContext();
        if (!user.getRole().equals(Role.FARMER)) {
            throw new ForbiddenException("Деталі підприємства доступні тільки якщо ви фермер");
        }
        return user.getBusinessDetails();
    }

    @Override
    public BusinessDetails updateBusinessDetails(UpdateBusinessDetailsDto updateBusinessDetailsDto) {
        User user = authService.getUserFromContext();
        if (!user.getRole().equals(Role.FARMER)) {
            throw new ForbiddenException("Деталі підприємства доступні тільки якщо ви фермер");
        }
        BusinessDetails businessDetails = user.getBusinessDetails();
        BeanUtils.copyProperties(updateBusinessDetailsDto, businessDetails);

        return businessDetailsRepository.save(businessDetails);
    }

    private TokenDto buildTokenFromUser(User user) {
        List<Role> list = new ArrayList<>();
        list.add(user.getRole())  ;
        String token = jwtTokenProvider.createToken(user, list);
        return new TokenDto(token);
    }

    private void sendActivationCode(User user) {
        String activationCode = RandomUtil.getRandomNumberString();
        user.setActivationCode(activationCode);
        userRepository.save(user);
        twilioService.sendVerificationMessage(user, activationCode);
    }
}



