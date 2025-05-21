package com.gs.geradorSenha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.auth.AuthService;
import com.gs.geradorSenha.exception.GSException;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.model.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		return (UserDetails) usuario;
	}

	public void cadastrar(Usuario usuario) throws GSException {
		if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
			throw new GSException("O e-mail informado já está cadastrado.", HttpStatus.BAD_REQUEST);
		}
		
		usuarioRepository.save(usuario);
	}

	public Usuario atualizar(Usuario usuarioASerAtualizado) throws GSException {
		Usuario usuarioExistente = usuarioRepository.findById(usuarioASerAtualizado.getIdUsuario())
				.orElseThrow(() -> new GSException("Usuário não encontrado.", HttpStatus.NOT_FOUND));

		usuarioExistente.setNome(usuarioASerAtualizado.getNome());
		usuarioExistente.setEmail(usuarioASerAtualizado.getEmail());

		if (usuarioASerAtualizado.getSenha() != null && !usuarioASerAtualizado.getSenha().isEmpty()
				&& !usuarioASerAtualizado.getSenha().equals("NO_PASSWORD_UPDATE")) {
			usuarioExistente.setSenha(encoder.encode(usuarioASerAtualizado.getSenha()));
		}

		return usuarioRepository.save(usuarioExistente);
	}

	public void excluir(Long idUsuario) throws GSException {
		usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new GSException("Usuário não encontrado.", HttpStatus.NOT_FOUND));

		usuarioRepository.deleteById(idUsuario);
	}

	public List<Usuario> pesquisarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario pesquisarPorId(Long id) throws GSException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new GSException("Usuário não encontrado.", HttpStatus.NOT_FOUND));
	}

}