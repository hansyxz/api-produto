package com.hansel.produto.service;

import com.hansel.produto.dto.ProdutoRequestDTO;
import com.hansel.produto.dto.ProdutoResponseDTO;
import com.hansel.produto.model.Produto;
import com.hansel.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveCriarProdutoComDadosCorretos() {

        ProdutoRequestDTO request = new ProdutoRequestDTO(
                "Camiseta",
                "Camiseta de algodão",
                "Roupas",
                new BigDecimal("49.90")
        );

        Produto produtoSalvo = new Produto(
                request.getNome(),
                request.getDescricao(),
                request.getCategoria(),
                request.getPreco()
        );
        produtoSalvo.setId(1L);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        ProdutoResponseDTO response = produtoService.criarProduto(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getDescricao(), response.getDescricao());
        assertEquals(request.getCategoria(), response.getCategoria());
        assertEquals(request.getPreco(), response.getPreco());
        assertNotNull(response.getImagemUrl());

        verify(produtoRepository, times(1)).save(any(Produto.class));
    }
}
