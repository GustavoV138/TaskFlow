package com.devstack.taskflow.exception.projectexceptions;

import org.springframework.http.HttpStatus;

public class ProjectAlreadyExistsException extends RuntimeException{

    private HttpStatus httpCode;

    public ProjectAlreadyExistsException(String message, HttpStatus httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }
}
