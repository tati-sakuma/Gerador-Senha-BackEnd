package com.gs.geradorSenha.exception;

import org.springframework.http.HttpStatus;

public class GSException extends Exception {
	public GSException(String message, HttpStatus badRequest) {
		super(message);
	}
}