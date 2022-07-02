package com.farmtrade.controllers;

import com.farmtrade.entities.User;
import com.farmtrade.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    final private UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/all")
    public Page<User> findAllUsers(){
        return null;
    }

    //CREATE
    @PostMapping
    public HttpStatus createUser(@RequestParam User user){
        return userServiceImpl.createUser(user);

    }

    //READ
    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id){
        return userServiceImpl.getUser(id);
    }

    //UPDATE
    @PutMapping
    public HttpStatus editUser(@RequestParam User user){
        return userServiceImpl.updateUser(user);
    }
    //DELETE
    @DeleteMapping
    public HttpStatus deleteUser(Long id){
        return userServiceImpl.deleteUser(id);
    }
}
