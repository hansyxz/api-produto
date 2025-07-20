package com.hansel.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(
        description = "Descrição geral do DTO, ex: dados para criar um produto",
        requiredProperties = { "nome", "categoria", "preco" })
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome do produto", example = "Tênis")
    private String nome;

    @Size(min = 3, message = "A descrição deve ter no mínimo 3 caracteres")
    @Schema(description = "Descrição do produto", example = "Tênis esportivo")
    private String descricao;

    @NotBlank(message = "A categoria é obrigatória!")
    @Size(min = 3, max = 100, message = "A categoria deve ter entre 3 e 100 caracteres")
    @Schema(description = "Categoria do produto", example = "Calçados")
    private String categoria;

    @Schema(description = "Preço unitário", example = "350.00")
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

