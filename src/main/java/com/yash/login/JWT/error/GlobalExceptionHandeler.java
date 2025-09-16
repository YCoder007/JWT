package com.yash.login.JWT.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandeler {


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex) {

    ApiError apiError =new ApiError(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }




}
