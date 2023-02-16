package com.farmtrade.exceptions.handlers;

import com.farmtrade.controllers.UserController;
import com.farmtrade.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map> handleException(EntityNotFoundException e) {
        String message = e.getMessage();
        Map response = Map.of("message", message);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
