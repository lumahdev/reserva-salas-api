package com.lumahdev.reservasalasapi.infra.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiradoException extends AuthenticationException {

    public TokenExpiradoException(String msg) {
        super(msg);
    }
}
