package com.farmtrade.security;

import com.farmtrade.entities.User;
import com.farmtrade.security.jwt.JwtUser;
import com.farmtrade.security.jwt.JwtUserFactory;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userService.getUserByPhone(phone);

        JwtUser jwtUser = JwtUserFactory.create(user);

        return jwtUser;
    }
}
