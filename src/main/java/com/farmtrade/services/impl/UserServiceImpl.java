package com.farmtrade.services.impl;

import com.farmtrade.domains.User;
import com.farmtrade.repos.UserRepository;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.data.domain.Page;
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
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAllUsers() {
        return null;
    }
}
