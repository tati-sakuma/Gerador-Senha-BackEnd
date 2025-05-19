package com.gs.geradorSenha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.exception.GsException;
import com.gs.geradorSenha.model.entity.Senha;
import com.gs.geradorSenha.model.repository.SenhaRepository;

@Service
public class SenhaService {

	@Autowired
	private SenhaRepository senhaRepository;

	public Senha criarSenha(Senha senha) throws GsException {
		return senhaRepository.save(senha);
	}

	public void excluirSenha(Long senhaId, Long usuarioId) throws GsException {

		Senha sugestao = senhaRepository.findById(senhaId)
				.orElseThrow(() -> new GsException("Senha não encontrada.", HttpStatus.NOT_FOUND));
	}

	public List<Senha> pesquisarSugestaoTodas() throws GsException {
		List<Senha> senhas = senhaRepository.findAll();

		return senhas;
	}

	public Senha procurarPorId(Long senhaId) throws GsException {
		Senha sugestao = senhaRepository.findById(senhaId)
				.orElseThrow(() -> new GsException("Esta senha não foi encontrada!", HttpStatus.NOT_FOUND));

		return sugestao;
	}

}
