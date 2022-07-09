package com.farmtrade.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class JwtUser implements UserDetails {

    private final Long id;
    private final String fullName;
    private final String phone;
    private final String email;
    private final String password;
    private final boolean isActive;
    private final List<GrantedAuthority> role;

    public JwtUser(Long id, String fullName, String phone, String email, String password, boolean isActive, List<GrantedAuthority> role) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return fullName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
