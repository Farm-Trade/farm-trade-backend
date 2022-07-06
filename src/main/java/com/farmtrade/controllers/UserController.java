package com.farmtrade.controllers;

import com.farmtrade.dto.ActivationCodeDto;
import com.farmtrade.dto.UserCreateDto;
import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.services.interfaces.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    final private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<User> findPage(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        return userService.findPage(pageable);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public User editUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUser(id, userUpdateDto);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User createUser(@RequestBody UserCreateDto userCreateDto)
    {
        return userService.createUser(userCreateDto);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable  Long id){
        userService.deleteUser(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/registration")
    public User registration(@RequestBody User user){
        return userService.registration(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/activate")
    public void userActivation(@RequestBody ActivationCodeDto activationCode){

        userService.userActivation(activationCode);
    }
}
