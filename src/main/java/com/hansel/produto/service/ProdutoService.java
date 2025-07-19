package com.hansel.produto.service;

import com.hansel.produto.dto.ProdutoRequestDTO;
import com.hansel.produto.dto.ProdutoResponseDTO;
import com.hansel.produto.model.Produto;
import com.hansel.produto.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO request) {
        Produto produto = new Produto(
            request.getNome(),
            request.getDescricao(),
            request.getCategoria(),
            request.getPreco()
        );

        return converterParaDTO(produtoRepository.save(produto));
    }

    public Optional<ProdutoResponseDTO> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .map(this::converterParaDTO);
    }

    public List<ProdutoResponseDTO> listarTodosProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Optional<ProdutoResponseDTO> atualizarProduto(Long id, ProdutoRequestDTO request) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(request.getNome());
                    produto.setDescricao(request.getDescricao());
                    produto.setCategoria(request.getCategoria());
                    produto.setPreco(request.getPreco());
                    produto.setImagemUrl(produto.getImagemUrl());

                    Produto atualizado = produtoRepository.save(produto);

                    return new ProdutoResponseDTO(
                            atualizado.getId(),
                            atualizado.getNome(),
                            produto.getDescricao(),
                            produto.getCategoria(),
                            produto.getPreco(),
                            produto.getImagemUrl()
                    );
                });
    }

    public ResponseEntity<Object> deletarProduto(Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getImagemUrl()
        );
    }
}
