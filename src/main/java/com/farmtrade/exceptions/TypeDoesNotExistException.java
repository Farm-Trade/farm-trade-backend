package com.farmtrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TypeDoesNotExistException extends RuntimeException {
    public TypeDoesNotExistException(Class<?> cls, String wrongType) {
        super(String.format("%s types is not matching with %s", cls.getSimpleName(), wrongType));
    }
}
