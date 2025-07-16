package com.hansel.produto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidacaoExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // exceção lançada quando algum campo anotado com @Valid não passa na validação
    public ResponseEntity<ErroValidacaoResponse> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<ErroCampoDTO> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> new ErroCampoDTO(erro.getField(), erro.getDefaultMessage()))
                .toList();

        ErroValidacaoResponse response = new ErroValidacaoResponse(HttpStatus.BAD_REQUEST.value(), erros);
        return ResponseEntity.badRequest().body(response);
    }
}
