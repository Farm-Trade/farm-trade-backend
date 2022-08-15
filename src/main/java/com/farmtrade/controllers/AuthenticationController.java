package com.farmtrade.controllers;

import com.farmtrade.dto.AuthenticationDto;
import com.farmtrade.dto.TokenDto;
import com.farmtrade.dto.recovering.ForgotPasswordDto;
import com.farmtrade.dto.recovering.ResetPasswordDto;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.exceptions.UserNotActiveException;
import com.farmtrade.services.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Authentication Controller", description = "The Authentication API")
public class AuthenticationController {
    final private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Get auth token")
    public TokenDto login(@RequestBody AuthenticationDto authenticationDto) {
        return userService.login(authenticationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/forgot-password")
    @Operation(summary = "Send activation code to reset password")
    public void forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) throws UserNotActiveException {
        userService.forgotPassword(forgotPasswordDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/reset-password/{id}")
    @Operation(summary = "Reset password by activation code")
    public TokenDto resetPassword(@PathVariable Long id, @RequestBody ResetPasswordDto resetPasswordDto) throws UserNotActiveException, EntityNotFoundException, BadRequestException {
        User user = userService.resetPassword(id, resetPasswordDto);

        AuthenticationDto authenticationDto = AuthenticationDto.builder()
                .password(resetPasswordDto.getPassword()).phone(user.getPhone()).build();
        return userService.login(authenticationDto);
    }
}
