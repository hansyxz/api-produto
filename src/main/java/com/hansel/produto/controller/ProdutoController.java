package com.hansel.produto.controller;

import com.hansel.produto.dto.ProdutoRequestDTO;
import com.hansel.produto.dto.ProdutoResponseDTO;
import com.hansel.produto.model.Produto;
import com.hansel.produto.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody @Valid ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoria(dto.getCategoria());
        produto.setPreco(dto.getPreco());
        produto.setImagemUrl("https://i.ibb.co/M5j3F7vQ/produto-image.jpg");

        Produto salvo = produtoRepository.save(produto);

        ProdutoResponseDTO response = new ProdutoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getDescricao(),
                salvo.getCategoria(),
                salvo.getPreco(),
                salvo.getImagemUrl()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoResponseDTO> dtos = produtos.stream()
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getCategoria(),
                        produto.getPreco(),
                        produto.getImagemUrl()))
                .toList();

        return  ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getCategoria(),
                        produto.getPreco(),
                        produto.getImagemUrl())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(dto.getNome());
                    produto.setDescricao(dto.getDescricao());
                    produto.setCategoria(dto.getCategoria());
                    produto.setPreco(dto.getPreco());

                    Produto atualizado = produtoRepository.save(produto);

                    ProdutoResponseDTO response = new ProdutoResponseDTO(
                            atualizado.getId(),
                            atualizado.getNome(),
                            atualizado.getDescricao(),
                            atualizado.getCategoria(),
                            atualizado.getPreco(),
                            atualizado.getImagemUrl()
                    );

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return ResponseEntity.noContent().build(); // 204
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
