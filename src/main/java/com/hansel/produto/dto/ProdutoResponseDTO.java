package com.hansel.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO de resposta com os dados do produto")
public class ProdutoResponseDTO {

    @Schema(description = "ID único do produto", example = "1")
    private final Long id;

    @Schema(description = "Nome do produto", example = "Camiseta Estampada")
    private final String nome;

    @Schema(description = "Descrição do produto", example = "Camiseta de algodão")
    private final String descricao;

    @Schema(description = "Categoria do produto", example = "Roupas")
    private final String categoria;

    @Schema(description = "Preço do produto", example = "59.90")
    private final BigDecimal preco;

    @Schema(description = "URL da imagem do produto", example = "https://produto-image.jpg")
    private final String imagemUrl;

    public  ProdutoResponseDTO(Long id, String nome, String descricao, String categoria, BigDecimal preco, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }

    public Long getId() {
        return id;
    }

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

    public String getImagemUrl() {
        return imagemUrl;
    }
}
