package com.gs.geradorSenha.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gs.geradorSenha.model.entity.Senha;

@Repository
public interface SenhaRepository extends JpaRepository<Senha, Long>, JpaSpecificationExecutor<Senha>{

}
