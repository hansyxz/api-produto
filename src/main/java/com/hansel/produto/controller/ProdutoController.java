package com.hansel.produto.controller;

import com.hansel.produto.dto.ProdutoRequestDTO;
import com.hansel.produto.dto.ProdutoResponseDTO;
import com.hansel.produto.repository.ProdutoRepository;
import com.hansel.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public class ProdutoController {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto")
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody @Valid ProdutoRequestDTO request) {
        ProdutoResponseDTO response = produtoService.criarProduto(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarTodosProdutos();

        return  ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto por ID")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO request) {
        return produtoService.atualizarProduto(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um produto")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return produtoService.deletarProduto(id);
    }
}
