package com.farmtrade.security;

import com.farmtrade.entities.User;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.security.jwt.JwtUser;
import com.farmtrade.security.jwt.JwtUserFactory;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone);

        JwtUser jwtUser = JwtUserFactory.create(user);

        return jwtUser;
    }
}
