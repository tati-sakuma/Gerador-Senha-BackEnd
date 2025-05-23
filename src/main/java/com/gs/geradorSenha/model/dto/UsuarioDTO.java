package com.gs.geradorSenha.model.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {

	private String nome;

	@Email
	private String email;

	private LocalDate dataNascimento;

	private String senha;

	private String confirmacaoSenha;

}