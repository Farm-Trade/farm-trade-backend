package com.farmtrade.controllers;

import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    final private UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<User> findPage(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        return userServiceImpl.findPage(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userServiceImpl.getUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public User editUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto){
        return userServiceImpl.updateUser(id,userUpdateDto);
    }

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{role}")
    public User createUser(@PathVariable("role") String role,
                           @RequestBody UserUpdateDto userUpdateDto,
                           @RequestParam("password") String password) throws Exception
    {
        return userServiceImpl.createUser(userUpdateDto, role, password);

    }


    //UPDATE

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(Long id){
        userServiceImpl.deleteUser(id);
    }
}
