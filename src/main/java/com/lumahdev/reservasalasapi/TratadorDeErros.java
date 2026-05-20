package com.lumahdev.reservasalasapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErrosDeValidacao(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> erros = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(erro -> {
                    erros.put(
                            erro.getField(),
                            erro.getDefaultMessage()
                    );
                });
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(Excecao.class)
    public ResponseEntity<String> tratarExcecao(
            Excecao e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}