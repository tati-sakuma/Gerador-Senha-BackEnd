package com.gerador.senha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationErrors(MethodArgumentNotValidException ex) {
        ValidationError errors = new ValidationError(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação nos campos informados"
        );
        
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.addError(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ValidationError> handleRuntimeException(RuntimeException ex) {
        ValidationError error = new ValidationError(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage() + " AAAA "
        );

        return ResponseEntity.badRequest().body(error);
    }
} 