package com.gs.geradorSenha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.exception.GSException;
import com.gs.geradorSenha.model.entity.Senha;
import com.gs.geradorSenha.model.repository.SenhaRepository;

@Service
public class SenhaService {

	@Autowired
	private SenhaRepository senhaRepository;

	public Senha criarSenha(Senha senha) throws GSException {
		return senhaRepository.save(senha);
	}

	public void excluirSenha(Long senhaId, Long usuarioId) throws GSException {

		Senha sugestao = senhaRepository.findById(senhaId)
				.orElseThrow(() -> new GSException("Senha não encontrada.", HttpStatus.NOT_FOUND));
	}

	public List<Senha> pesquisarSugestaoTodas() throws GSException {
		List<Senha> senhas = senhaRepository.findAll();

		return senhas;
	}

	public Senha procurarPorId(Long senhaId) throws GSException {
		Senha sugestao = senhaRepository.findById(senhaId)
				.orElseThrow(() -> new GSException("Esta senha não foi encontrada!", HttpStatus.NOT_FOUND));

		return sugestao;
	}

}
