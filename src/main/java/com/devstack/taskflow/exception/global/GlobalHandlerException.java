package com.devstack.taskflow.exception.global;

import com.devstack.taskflow.exception.projectexceptions.ProjectAlreadyExistsException;
import com.devstack.taskflow.exception.projectexceptions.ProjectNotFoundException;
import com.devstack.taskflow.exception.userexceptions.UserAlreadyExistsException;
import com.devstack.taskflow.exception.userexceptions.UserNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, String> errorsMap = new HashMap<>();

        for (FieldError fieldError : fieldErrors) {
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getStatusCode());
        problemDetail.setTitle("Erro no preenchimento dos campos.");
        problemDetail.setProperty("campos_inválidos", errorsMap);
        return ResponseEntity.status(ex.getStatusCode()).body(problemDetail);
    }
}
