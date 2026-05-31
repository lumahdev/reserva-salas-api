package com.lumahdev.reservasalasapi.infra;

import com.lumahdev.reservasalasapi.domain.Excecao.DtoExcecao;
import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerExcecao {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacaoDtos(MethodArgumentNotValidException e) {
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

    @ExceptionHandler(Excecao.class)
    public ResponseEntity<DtoExcecao> tratarExcecao(Excecao e) {
        return ResponseEntity.status(e.getStatus()).body(new DtoExcecao(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DtoExcecao> tratarBodyVazio() {
        return ResponseEntity.badRequest().body(new DtoExcecao("Corpo da requisição é obrigatório."));
    }
}