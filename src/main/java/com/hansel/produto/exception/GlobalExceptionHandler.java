package com.hansel.produto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(field -> {
                    String campo = field.getField();
                    String mensagem = field.getDefaultMessage();
                    erros.put(campo, mensagem);
                });

        ErroValidacaoResponse response = new ErroValidacaoResponse(HttpStatus.BAD_REQUEST.value(), erros);

        return ResponseEntity.badRequest().body(response);
    }
}
