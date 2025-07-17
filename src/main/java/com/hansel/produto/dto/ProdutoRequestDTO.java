package com.hansel.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(min = 3, message = "A descrição deve ter no mínimo 3 caracteres")
    private String descricao;

    @NotBlank(message = "A categoria é obrigatória!")
    @Size(min = 3, max = 100, message = "A categoria deve ter entre 3 e 100 caracteres")
    private String categoria;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
