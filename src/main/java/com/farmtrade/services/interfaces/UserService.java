package com.farmtrade.services.interfaces;

import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
     Page<User> findPage(Pageable pageable);


     User createUser(UserUpdateDto userUpdateDto, String role, String password) throws Exception;

     void deleteUser(Long id);

     User updateUser(Long id, @RequestBody UserUpdateDto userUpdateDto);

     User getUser(Long id);
}
