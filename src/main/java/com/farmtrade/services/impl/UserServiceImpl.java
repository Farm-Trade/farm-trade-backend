package com.farmtrade.services.impl;

import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;
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
        Optional<User> user = userRepository.findById(id);
        BeanUtils.copyProperties(userUpdateDto, user.get());
        return userRepository.save(user.get());
    }

    @Override
    public User createUser(UserUpdateDto userUpdateDto, String role) throws Exception {
        User user = User.builder()
                        .fullName(userUpdateDto.getFullName())
                        .password(userUpdateDto.getPassword())
                        .email(userUpdateDto.getEmail())
                        .phone(userUpdateDto.getPhone())
                .build();

        if(role == "farmer"){
            user.setRole(Role.FARMER);
        }
        else if(role == "reseller"){
            user.setRole(Role.RESELLER);
        }
        else throw new Exception("You have no permission to add different role from farmer or reseller");
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }





}
