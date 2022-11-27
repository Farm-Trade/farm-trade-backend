package com.farmtrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> cls, String id) {
        super(String.format("%s не вдалось знайти з айді %s", cls.getSimpleName(), id));
    }
    public EntityNotFoundException(Class<?> cls, Long id) {
        super(String.format("%s не вдалось знайти з айді %s", cls.getSimpleName(), id.toString()));
    }

    public EntityNotFoundException(Class<?> cls, String subject, String id) {
        super(String.format("%s не вдалось знайти з %s %s", cls.getSimpleName(), subject, id));
    }
}
