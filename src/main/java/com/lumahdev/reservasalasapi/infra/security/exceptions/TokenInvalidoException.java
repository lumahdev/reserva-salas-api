package com.lumahdev.reservasalasapi.infra.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenInvalidoException extends AuthenticationException {

    public TokenInvalidoException(String msg) {
        super(msg);
    }
}
