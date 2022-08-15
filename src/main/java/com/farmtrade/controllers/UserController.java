package com.farmtrade.controllers;

import com.farmtrade.dto.ActivationCodeDto;
import com.farmtrade.dto.UserCreateDto;
import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.services.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "The User API")
public class UserController {

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get user page", security = @SecurityRequirement(name = SECURITY_SCHEME_NAME))
    public Page<User> findPage(@ParameterObject @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.findPage(pageable);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get user", security = @SecurityRequirement(name = SECURITY_SCHEME_NAME))
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Edit user", security = @SecurityRequirement(name = SECURITY_SCHEME_NAME))
    public User editUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create user", security = @SecurityRequirement(name = SECURITY_SCHEME_NAME))
    public User createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", security = @SecurityRequirement(name = SECURITY_SCHEME_NAME))
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/registration")
    @Operation(summary = "Register user")
    public User registration(@RequestBody UserCreateDto user) {
        return userService.registration(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/activate")
    @Operation(summary = "Activate user")
    public void userActivation(@RequestBody ActivationCodeDto activationCode) throws EntityNotFoundException {

        userService.userActivation(activationCode);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/from-code/{activationCode}")
    @Operation(summary = "Get user by activation code")
    public User getUserByActivationCode(@PathVariable String activationCode) throws EntityNotFoundException {
        return userService.getUserByActivationCode(activationCode);
    }
}
