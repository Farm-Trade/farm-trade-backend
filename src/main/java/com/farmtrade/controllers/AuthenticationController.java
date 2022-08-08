package com.farmtrade.controllers;

import com.farmtrade.dto.AuthenticationDto;
import com.farmtrade.dto.TokenDto;

import com.farmtrade.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication Controller", description = "The Authentication API")
public class AuthenticationController {

    final private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/login")
    @Operation(summary = "Get auth token")
    public TokenDto login(@RequestBody AuthenticationDto authenticationDto) {
        return userService.login(authenticationDto);
    }
}
