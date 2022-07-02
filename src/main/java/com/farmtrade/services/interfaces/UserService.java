package com.farmtrade.services.interfaces;

import com.farmtrade.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
     Page<User> findAllUsers();

     HttpStatus createUser(User user);

     HttpStatus deleteUser(Long id);

     HttpStatus updateUser(User user);

     User getUser(Long id);
}
