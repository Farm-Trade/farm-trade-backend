package com.farmtrade.services.api;

import com.farmtrade.dto.*;
import com.farmtrade.dto.recovering.ForgotPasswordDto;
import com.farmtrade.dto.recovering.ResetPasswordDto;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.exceptions.UserNotActiveException;
import org.hibernate.action.internal.EntityActionVetoException;
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

     User updateUser(Long id, UserUpdateDto userUpdateDto);

     User getUser(Long id);

     User registration(UserCreateDto user) throws ApiValidationException;

     User getUserByPhone(String phone);

     void userActivation(ActivationCodeDto activationCode);

     TokenDto login(AuthenticationDto authenticationDto);

     void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws UserNotActiveException;

     User getUserByActivationCode(String activationCode) throws UserNotActiveException;

     User resetPassword( Long id, ResetPasswordDto resetPasswordDto) throws UserNotActiveException, EntityNotFoundException, BadRequestException;
}
