package com.farmtrade.entities.enums;

import com.farmtrade.exceptions.ApiValidationException;
import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    FARMER,
    RESELLER,
    ADMIN;

    public static boolean isCommercialRole(Role role, boolean throwable) throws ApiValidationException {
        boolean isNotCommercialRole = role.equals(ADMIN);

        if (isNotCommercialRole && throwable) {
            throw new ApiValidationException("ADMIN це не одна із комерійних ролей");
        }
        return !isNotCommercialRole;




    }

    @Override
    public String getAuthority() {
        return name();
    }

}
