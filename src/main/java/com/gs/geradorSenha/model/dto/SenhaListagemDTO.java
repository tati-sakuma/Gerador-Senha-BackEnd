package com.gs.geradorSenha.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SenhaListagemDTO {

    private String id;
    private String nome;
    private String senha;
}