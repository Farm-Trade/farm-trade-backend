package com.farmtrade.controllers;

import com.farmtrade.dto.AuthenticationDto;
import com.farmtrade.dto.TokenDto;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.security.jwt.JwtTokenProvider;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthenticationController {

    final private UserService userService;
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/login")
    public TokenDto login (@RequestBody AuthenticationDto authenticationDto) {
        return userService.login(authenticationDto);
    }


    public UserService getUserService() {
        return userService;
    }
}
