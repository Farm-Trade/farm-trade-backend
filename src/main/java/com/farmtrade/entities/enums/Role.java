package com.farmtrade.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    FARMER, BUYER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
