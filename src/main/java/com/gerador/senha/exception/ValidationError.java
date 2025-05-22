package com.gerador.senha.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError {
    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private List<FieldError> errors = new ArrayList<>();

    public ValidationError(Integer status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldError(fieldName, message));
    }

    @Getter
    private static class FieldError {
        private String campo;
        private String mensagem;

        public FieldError(String campo, String mensagem) {
            this.campo = campo;
            this.mensagem = mensagem;
        }
    }
} 