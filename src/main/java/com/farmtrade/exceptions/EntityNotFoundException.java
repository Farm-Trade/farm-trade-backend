package com.farmtrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> cls, String id) {
        super(String.format("%s not found with id %s", cls.getSimpleName(), id));
    }
}
