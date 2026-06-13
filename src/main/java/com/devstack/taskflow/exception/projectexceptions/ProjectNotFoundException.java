package com.devstack.taskflow.exception.projectexceptions;

import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends RuntimeException{

    private HttpStatus httpCode;

    public ProjectNotFoundException(String message, HttpStatus httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }
}
