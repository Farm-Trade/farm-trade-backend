package com.farmtrade.services.security;

import com.farmtrade.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface AuthService {
    User getUserFromContext();

    Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);
}
