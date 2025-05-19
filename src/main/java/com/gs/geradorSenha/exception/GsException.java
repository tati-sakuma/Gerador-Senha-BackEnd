package com.gs.geradorSenha.exception;

import org.springframework.http.HttpStatus;

public class GsException extends Exception {
	public GsException(String message, HttpStatus badRequest) {
		super(message);
	}
}