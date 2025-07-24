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
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @Test
    void deveListarTodosProdutosComSucesso() {

        Produto produto1 = new Produto("Produto 1", "Descricao 1", "Categoria A", BigDecimal.valueOf(100));
        produto1.setId(1L);

        Produto produto2 = new Produto("Produto 2", "Descricao 2", "Categoria B", BigDecimal.valueOf(200));
        produto2.setId(2L);

        List<Produto> produtos = List.of(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(produtos);

        List<ProdutoResponseDTO> response = produtoService.listarTodosProdutos();

        assertEquals(2, response.size());
        assertEquals("Produto 1", response.get(0).getNome());
        assertEquals("Produto 2", response.get(1).getNome());

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarProdutoPorIdExistente() {

        Produto produto = new Produto("Produto 1", "Descricao 1", "Categoria A", BigDecimal.valueOf(100));
        produto.setId(1L);

        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        Optional<ProdutoResponseDTO> response = produtoService.buscarProdutoPorId(produto.getId());

        assertTrue(response.isPresent(), "ProdutoResponseDTO deveria estar presente");

        ProdutoResponseDTO dto = response.get();

        verify(produtoRepository, times(1)).findById(produto.getId());
        assertEquals(produto.getNome(), dto.getNome());
        assertEquals(produto.getDescricao(), dto.getDescricao());
        assertEquals(produto.getCategoria(), dto.getCategoria());
        assertEquals(produto.getPreco(), dto.getPreco());
        assertNotNull(dto.getImagemUrl());
    }

    @Test
    void deveRetornarVazioAoBuscarProdutoPorIdInexistente() {

        Long idInexistente = 10L;

        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Optional<ProdutoResponseDTO> response = produtoService.buscarProdutoPorId(idInexistente);

        assertTrue(response.isEmpty(), "Deveria retornar Optional.empty() ao busca um produto inexistente");
    }

    @Test
    void deveAtualizarProdutoComSucesso() {

        Produto produto = new Produto("Produto 1", "Descricao 1", "Categoria A", BigDecimal.valueOf(100));
        produto.setId(1L);

        ProdutoRequestDTO produtoAtualizado = new ProdutoRequestDTO("Produto 1", "Descricao 2", "Categoria B", BigDecimal.valueOf(200));

        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<ProdutoResponseDTO> response = produtoService.atualizarProduto(produto.getId(), produtoAtualizado);

        assertTrue(response.isPresent(), "ProdutoResponseDTO deveria estar presente");

        ProdutoResponseDTO dto = response.get();

        verify(produtoRepository, times(1)).findById(produto.getId());
        assertEquals(produtoAtualizado.getNome(), dto.getNome());
        assertEquals(produtoAtualizado.getDescricao(), dto.getDescricao());
        assertEquals(produtoAtualizado.getCategoria(), dto.getCategoria());
        assertEquals(produtoAtualizado.getPreco(), dto.getPreco());
        assertNotNull(dto.getImagemUrl());
    }

    @Test
    void deveRetornarVazioAoAtualizarProdutoComIdInexistente() {

        Long idInexistente = 10L;
        ProdutoRequestDTO produtoAtualizado = new ProdutoRequestDTO("Produto 1", "Descricao 2", "Categoria B", BigDecimal.valueOf(200));

        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Optional<ProdutoResponseDTO> response = produtoService.atualizarProduto(idInexistente, produtoAtualizado);

        assertTrue(response.isEmpty(), "Deveria retornar Optional.empty() ao atualizar um produto inexistente");
    }

    @Test
    void deveDeletarProdutoComSucesso() {

        Produto produto = new Produto("Produto 1", "Descricao 1", "Categoria A", BigDecimal.valueOf(100));
        produto.setId(1L);

        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        ResponseEntity<Object> response = produtoService.deletarProduto(produto.getId());

        verify(produtoRepository, times(1)).findById(produto.getId());
        verify(produtoRepository, times(1)).delete(produto);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void deveRetornarNotFoundAoDeletarProdutoComIdInexistente() {
        Long idInexistente = 10L;

        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = produtoService.deletarProduto(idInexistente);

        verify(produtoRepository, times(1)).findById(idInexistente);

        verify(produtoRepository, never()).delete(any());

        assertEquals(404, response.getStatusCode().value());
    }
}
