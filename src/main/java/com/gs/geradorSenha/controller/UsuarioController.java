package com.gs.geradorSenha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs.geradorSenha.auth.AuthService;
import com.gs.geradorSenha.exception.GSException;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios")
@CrossOrigin(origins = { "http://localhost:3000" }, maxAge = 3600)
@MultipartConfig(fileSizeThreshold = 10485760)
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AuthService authService;

	@Operation(summary = "Atualizar dados do usuário", description = "Atualiza os dados do usuário autenticado com as informações fornecidas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos"),
			@ApiResponse(responseCode = "401", description = "Usuário não autenticado") })
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioASerAtualizado) throws GSException {
		Usuario subject = authService.getUsuarioAutenticado();

		usuarioASerAtualizado.setIdUsuario(subject.getIdUsuario());

		Usuario usuarioAtualizado = usuarioService.atualizar(usuarioASerAtualizado);

		return ResponseEntity.ok(usuarioAtualizado);
	}

	@Operation(summary = "Excluir conta de usuário", description = "Exclui a conta do usuário autenticado de forma permanente.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Conta excluída com sucesso"),
			@ApiResponse(responseCode = "401", description = "Usuário não autenticado") })
	@DeleteMapping(path = "/excluir")
	public ResponseEntity<Void> excluir() throws GSException {
		Usuario subject = authService.getUsuarioAutenticado();

		usuarioService.excluir(subject.getIdUsuario());

		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Listar todos os usuários", description = "Retorna uma lista completa com todos os usuários cadastrados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	@GetMapping(path = "/todos")
	public List<Usuario> pesquisarTodos() {
		List<Usuario> usuarios = usuarioService.pesquisarTodos();
		return usuarios;
	}

	@Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário com base no ID informado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@GetMapping(path = "/{idUsuario}")
	public ResponseEntity<Usuario> pesquisarPorId(@PathVariable Long idUsuario) throws GSException {
		Usuario usuario = usuarioService.pesquisarPorId(idUsuario);
		return ResponseEntity.ok((usuario));
	}

	@Operation(summary = "Obter usuário autenticado", description = "Retorna os dados do usuário atualmente autenticado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados do usuário autenticado retornados com sucesso"),
			@ApiResponse(responseCode = "401", description = "Usuário não autenticado") })
	@GetMapping("/usuario-autenticado")
	public ResponseEntity<Usuario> buscarUsuarioAutenticado() throws GSException {
		Usuario usuario = authService.getUsuarioAutenticado();
		return ResponseEntity.ok((usuario));
	}

}