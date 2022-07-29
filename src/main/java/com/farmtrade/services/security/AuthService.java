package com.farmtrade.services.security;

import com.farmtrade.entities.User;

public interface AuthService {
    User getUserFromContext();
}
