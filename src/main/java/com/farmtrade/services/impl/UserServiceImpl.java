package com.farmtrade.services.impl;

import com.farmtrade.dto.UserCreateDto;
import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;
/*    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User createUser(UserCreateDto userCreateDto){
        User user = User.builder()
                        .fullName(userCreateDto.getFullName())
                        .password(userCreateDto.getPassword())
                        .email(userCreateDto.getEmail())
                        .phone(userCreateDto.getPhone())
                         .role(userCreateDto.getRole())
                .build();

        return user;
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }





}
