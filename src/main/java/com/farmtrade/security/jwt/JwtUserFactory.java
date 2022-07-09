package com.farmtrade.security.jwt;

import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {
    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getFullName(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                mapToGrantedAuthorities(user.getRole())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(role.getAuthority()));
        return list;


    }
}
