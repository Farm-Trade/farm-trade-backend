package com.farmtrade.services.interfaces;

import com.farmtrade.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
     Page<User> findAllUsers();
}
