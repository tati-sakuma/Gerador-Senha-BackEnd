package com.gs.geradorSenha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gs.geradorSenha.auth.AuthService;
import com.gs.geradorSenha.exception.GSException;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private AuthService authenticationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Método de login padronizado -> Basic Auth
	 * <p>
	 * O parâmetro Authentication já encapsula login (username) e senha (senha)
	 * Basic <Base64 encoded username and senha>
	 *
	 * @param authentication
	 * @return o JWT gerado
	 */

	@Operation(summary = "Realiza login do usuário", description = "Autentica um usuário e retorna um token de acesso.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
			@ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos") })
	@PostMapping("/login")
	public String login(Authentication authentication) throws GSException {
		return authenticationService.authenticate(authentication);
	}

	@Operation(summary = "Cadastra um novo usuário", description = "Cadastra um usuário com o perfil de usuário comum.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de validação") })
	@PostMapping("/novo")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrar(@RequestBody Usuario novoUsuario) throws GSException {
		try {
			processarCadastro(novoUsuario, false);
		} catch (TransactionSystemException ex) {
		    Throwable cause = ex.getRootCause();
		    if (cause instanceof ConstraintViolationException) {
		        ConstraintViolationException validationEx = (ConstraintViolationException) cause;

		        StringBuilder errorMsg = new StringBuilder("Erro de validação:\n");
		        for (ConstraintViolation<?> violation : validationEx.getConstraintViolations()) {
		            errorMsg.append("- ")
		                    .append(violation.getPropertyPath())
		                    .append(": ")
		                    .append(violation.getMessage())
		                    .append("\n");
		        }

		        throw new GSException(errorMsg.toString(), HttpStatus.BAD_REQUEST);
		    } else {
		        throw new GSException("Erro na transação: " + ex.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		} catch (Exception ex) {
	        throw new GSException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Cadastra um novo administrador", description = "Cadastra um usuário com o perfil de administrador.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Administrador cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de validação") })
	@PostMapping("/novo-admin")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarAdmin(@RequestBody Usuario novoUsuario) throws GSException {
		processarCadastro(novoUsuario, true);
	}

	private void processarCadastro(Usuario usuario, boolean isAdmin) throws GSException {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioService.cadastrar(usuario);
	}

}