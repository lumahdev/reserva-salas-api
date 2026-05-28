package com.lumahdev.reservasalasapi.domain.Excecao;

import org.springframework.http.HttpStatus;

public class Excecao extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public Excecao(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}