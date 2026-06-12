package com.devstack.taskflow.exception.userexceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException{

    private HttpStatus httpCode;

    public UserAlreadyExistsException(String message, HttpStatus httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }
}
