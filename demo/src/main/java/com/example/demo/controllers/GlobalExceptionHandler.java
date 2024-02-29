package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Invalid parameter provided";
        return new ResponseEntity<>(message, status);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error";
        return new ResponseEntity<>(message, status);
    }

}
