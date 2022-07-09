package com.farmtrade.controllers;

import com.farmtrade.dto.AuthenticationDto;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.security.jwt.JwtTokenProvider;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity login (@RequestBody AuthenticationDto authenticationDto){
        try{
            String phone = authenticationDto.getPhone();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phone, authenticationDto.getPassword()));

            User user = userService.getUserByPhone(phone);
            if(user == null){
                throw new UsernameNotFoundException("User with such phone is not found");
            }
            List<Role> list = new ArrayList<>();
            list.add(user.getRole())  ;
            String token = jwtTokenProvider.createToken(phone, list);

            Map<Object,Object> response = new HashMap<>();
            // response.put("phone",phone);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid Phone or password");
        }
    }
}
