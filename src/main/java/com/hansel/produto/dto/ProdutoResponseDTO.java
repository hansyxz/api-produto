package com.hansel.produto.dto;

import java.math.BigDecimal;

public class ProdutoResponseDTO {
    private final Long id;
    private final String nome;
    private final String descricao;
    private final String categoria;
    private final BigDecimal preco;
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
