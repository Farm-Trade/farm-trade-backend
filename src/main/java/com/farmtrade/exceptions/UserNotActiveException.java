package com.farmtrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException() {
        super("Користувач не активний");
    }
}
