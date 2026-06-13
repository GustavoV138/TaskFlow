package com.devstack.taskflow.exception.global;

import com.devstack.taskflow.exception.projectexceptions.ProjectAlreadyExistsException;
import com.devstack.taskflow.exception.projectexceptions.ProjectNotFoundException;
import com.devstack.taskflow.exception.userexceptions.UserAlreadyExistsException;
import com.devstack.taskflow.exception.userexceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpCode(), ex.getMessage());
        problemDetail.setTitle("Conta já existe.");
        return ResponseEntity.status(ex.getHttpCode()).body(problemDetail);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpCode(), ex.getMessage());
        problemDetail.setTitle("Usuário não encontrado.");
        return ResponseEntity.status(ex.getHttpCode()).body(problemDetail);
    }

    @ExceptionHandler(ProjectAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleProjectAlreadyExistsException(ProjectAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpCode(), ex.getMessage());
        problemDetail.setTitle("Projeto já existe.");
        return ResponseEntity.status(ex.getHttpCode()).body(problemDetail);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getHttpCode(), ex.getMessage());
        problemDetail.setTitle("Projeto não encontrado.");
        return ResponseEntity.status(ex.getHttpCode()).body(problemDetail);
    }
}
