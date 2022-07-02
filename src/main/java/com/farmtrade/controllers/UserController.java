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


    @GetMapping
    public Page<User> findAllUsers(){
        return null;
    }

    @GetMapping("/currentUser")
    public String getUserName(@RequestParam(required = false) String username){
        return userServiceImpl.loadUserByUsername(username).getUsername();
    }

    //UPDATE
    @GetMapping("/{id}/edit")
    public HttpStatus editUser(@PathVariable("id") Long id){
        return null;
    }
}
