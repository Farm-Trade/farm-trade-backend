package com.farmtrade.dto;


import com.farmtrade.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDto {
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private Role role;
}
