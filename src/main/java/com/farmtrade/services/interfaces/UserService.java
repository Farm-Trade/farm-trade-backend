package com.farmtrade.services.interfaces;

import com.farmtrade.dto.*;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.ApiValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService  {
     Page<User> findPage(Pageable pageable);


     User createUser(UserCreateDto userCreateDto);

     void deleteUser(Long id);

     User updateUser(Long id, @RequestBody UserUpdateDto userUpdateDto);

     User getUser(Long id);

     User registration(UserCreateDto user) throws ApiValidationException;

     User getUserByPhone(String phone);

     void userActivation(ActivationCodeDto activationCode);

     TokenDto login(AuthenticationDto authenticationDto);
}
