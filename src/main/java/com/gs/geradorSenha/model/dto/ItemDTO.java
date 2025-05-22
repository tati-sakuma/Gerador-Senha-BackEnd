package com.gs.geradorSenha.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemDTO {
    
    @NotBlank(message = "Nome do item é obrigatório")
    private String name;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
} 