package com.farmtrade.dto.user;

import com.farmtrade.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
    private String fullName;
    private String phone;
    private String email;
    private Role role;
}
