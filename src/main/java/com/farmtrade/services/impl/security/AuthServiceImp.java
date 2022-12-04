package com.farmtrade.services.impl.security;

import com.farmtrade.entities.User;
import com.farmtrade.exceptions.UnauthorizedException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.security.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public AuthServiceImp(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        String phone = authentication.getName();
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача з таким телефоном не інсує: " + phone));
    }

    @Override
    public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
