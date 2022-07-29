package com.farmtrade.services.impl.security;

import com.farmtrade.entities.User;
import com.farmtrade.exceptions.UnauthorizedException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.security.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        String phone = authentication.getName();
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the phone: " + phone));
    }


}
