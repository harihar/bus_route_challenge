package com.goeuro.busroutes.controller;

import com.goeuro.busroutes.exception.Error;
import com.goeuro.busroutes.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handle(ResourceNotFoundException e) {
        return new Error(520, e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handle(MissingServletRequestParameterException e) {
        String message = String.format("Missing required parameter %s", e.getParameterName());
        return new Error(400, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handle(MethodArgumentTypeMismatchException e) {
        String message = String.format("%s should be of type %s", e.getName(), e.getRequiredType().getName());
        return new Error(400, message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handle(Exception e) {
        log.error("Caught unhandled exception", e);
        return new Error(500, "Internal server error");
    }
}
