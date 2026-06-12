package com.devstack.taskflow.exception.userexceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{

    private HttpStatus httpCode;

    public UserNotFoundException(String message, HttpStatus httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }
}
