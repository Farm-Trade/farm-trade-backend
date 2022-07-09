package com.farmtrade.services.impl;

import com.farmtrade.dto.ActivationCodeDto;
import com.farmtrade.dto.UserCreateDto;
import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.interfaces.UserService;
import com.farmtrade.services.smpp.TwilioService;
import com.farmtrade.utils.RandomUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private TwilioService twilioService;
    @Value("${user.sendActivation}")
    private boolean sendActivation;


/*    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/

    public UserServiceImpl(UserRepository userRepository, TwilioService twilioService) {
        this.userRepository = userRepository;
        this.twilioService = twilioService;
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
    public User updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = getUser(id);
        BeanUtils.copyProperties(userUpdateDto, user);
        return userRepository.save(user);
    }

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        User user = User.builder()
                .fullName(userCreateDto.getFullName())
                .password(userCreateDto.getPassword())
                .email(userCreateDto.getEmail())
                .phone(userCreateDto.getPhone())
                .isActive(true)
                .role(userCreateDto.getRole())
                .build();

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }


    @Override
    @Transactional
    public User registration(UserCreateDto userCreateDto) {
        if (userCreateDto.getRole() == Role.ADMIN) {
            throw new ApiValidationException("Chose another role");
        }

        User user = User.builder()
                .fullName(userCreateDto.getFullName())
                .phone(userCreateDto.getPhone())
                .email(userCreateDto.getEmail())
                .role(userCreateDto.getRole())
                .build();
        if (sendActivation) {
            String activationCode = RandomUtil.getRandomNumberString();
            user.setActivationCode(activationCode);
            userRepository.save(user);
            twilioService.sendVerificationMessage(user, activationCode);
            return user;
        }
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public void userActivation(ActivationCodeDto activationCode) {
        User user = userRepository.findByActivationCode(activationCode.getActivationCode())
                .orElseThrow(() -> new ApiValidationException("User is not found"));
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
    }

}
