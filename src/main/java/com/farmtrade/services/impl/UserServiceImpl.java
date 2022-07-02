package com.farmtrade.services.impl;

import com.farmtrade.entities.User;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return userRepository.findByFullName(username);
    }



    @Override
    public Page<User> findAllUsers() {
        return null;
    }

    @Override
    public HttpStatus createUser(User user) {
        return null;
    }

    @Override
    public HttpStatus deleteUser(Long id) {
        return null;
    }

    @Override
    public HttpStatus updateUser(User user) {
        return null;
    }

    @Override
    public User getUser(Long id) {
        return null;
    }
}
