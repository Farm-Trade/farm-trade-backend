package com.farmtrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String fileName) {
        super(String.format("Файл %s не знайдено", fileName));
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
