package com.lumahdev.reservasalasapi.Excecao;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(Excecao.class)
    public ResponseEntity<DtoErro> tratarExcecao(Excecao e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new DtoErro(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException e) {
        Map<String, String> erros = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(erro ->
                        erros.put(
                                erro.getField(),
                                erro.getDefaultMessage()
                        )
                );

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DtoErro> tratarBodyVazio(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .body(new DtoErro("Corpo da requisição é obrigatório."));
    }
}