package com.farmtrade.entities.enums;

import com.farmtrade.exceptions.ApiValidationException;

public enum Role {
    FARMER,
    RESELLER,
    ADMIN;

    public static boolean isCommercialRole(Role role, boolean throwable) throws ApiValidationException {
        boolean isNotCommercialRole = role.equals(ADMIN);

        if (isNotCommercialRole && throwable) {
            throw new ApiValidationException("ADMIN role is not one of commercial roles");
        }
        return !isNotCommercialRole;

    }
}
