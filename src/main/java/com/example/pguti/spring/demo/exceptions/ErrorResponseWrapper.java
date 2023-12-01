package com.example.pguti.spring.demo.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;

@AllArgsConstructor
@ControllerAdvice(annotations = RestController.class)
public class ErrorResponseWrapper {
    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity handleConstraint(NameNotFoundException ex) {
        StatusException errorMessage = new StatusException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
