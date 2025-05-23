package com.gs.geradorSenha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.exception.GSException;
import com.gs.geradorSenha.model.dto.SenhaDTO;
import com.gs.geradorSenha.model.dto.SenhaListagemDTO;
import com.gs.geradorSenha.model.entity.Senha;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.model.repository.SenhaRepository;

@Service
public class SenhaService {

	@Autowired
	private SenhaRepository senhaRepository;

	public HttpStatus cadastrarSenha(SenhaDTO dto, Usuario usuario) throws GSException {
		if (senhaRepository.existsByNomeAndUsuarioId(dto.getNome(), usuario.getIdUsuario())) {
			throw new GSException("Nome do item já cadastrado", HttpStatus.CONFLICT);
		}
		Senha senha = new Senha();
		senha.setNome(dto.getNome());
		senha.setSenha(dto.getSenha());
		senha.setUsuario(usuario);
		senhaRepository.save(senha);

		return HttpStatus.CREATED;
	}

	public void excluirSenha(String senhaId) throws GSException {
		System.out.println(" DELETENASDOAJDA: " + senhaId);
		if (!senhaRepository.existsById(senhaId)) {
			throw new GSException("Item nao encontrado", HttpStatus.NOT_FOUND);
		}
		senhaRepository.deleteById(senhaId);
	}

	public List<SenhaListagemDTO> buscarSenhasDoUsuario(Long idUsuario) {
		return senhaRepository.findAllByUsuarioId(idUsuario);
	}

	public Senha procurarPorId(String senhaId) throws GSException {
		Senha sugestao = senhaRepository.findById(senhaId)
				.orElseThrow(() -> new GSException("Esta senha não foi encontrada!", HttpStatus.NOT_FOUND));

		return sugestao;
	}

}
