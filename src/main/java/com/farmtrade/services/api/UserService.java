package com.farmtrade.services.api;

import com.farmtrade.dto.auth.ActivationCodeDto;
import com.farmtrade.dto.auth.AuthenticationDto;
import com.farmtrade.dto.auth.TokenDto;
import com.farmtrade.dto.businessdetails.UpdateBusinessDetailsDto;
import com.farmtrade.dto.recovering.ForgotPasswordDto;
import com.farmtrade.dto.recovering.ResetPasswordDto;
import com.farmtrade.dto.user.UserCreateDto;
import com.farmtrade.dto.user.UserSettingsUpdateDto;
import com.farmtrade.dto.user.UserUpdateDto;
import com.farmtrade.entities.BusinessDetails;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.exceptions.UserNotActiveException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

     void forgotPassword(ForgotPasswordDto forgotPasswordDto) throws UserNotActiveException;

     User getUserByActivationCode(String activationCode) throws UserNotActiveException;

     User resetPassword( Long id, ResetPasswordDto resetPasswordDto) throws UserNotActiveException, EntityNotFoundException, BadRequestException;

     TokenDto userSettingsUpdate(UserSettingsUpdateDto userPasswordUpdateDto);

     BusinessDetails findUserBusinessDetails();
     BusinessDetails updateBusinessDetails(UpdateBusinessDetailsDto updateBusinessDetailsDto);
}
