package com.farmtrade.services.interfaces;

import com.farmtrade.dto.ActivationCodeDto;
import com.farmtrade.dto.UserCreateDto;
import com.farmtrade.dto.UserUpdateDto;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.ApiValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
     Page<User> findPage(Pageable pageable);


     User createUser(UserCreateDto userCreateDto);

     void deleteUser(Long id);

     User updateUser(Long id, @RequestBody UserUpdateDto userUpdateDto);

     User getUser(Long id);

     User registration(UserCreateDto user) throws ApiValidationException;

     void userActivation(ActivationCodeDto activationCode);
}
