package com.lumahdev.reservasalasapi.infra.security;

import com.lumahdev.reservasalasapi.domain.Excecao.DtoExcecao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class HandlerAuth implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    private String getMensagemDeErro(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "Credenciais inválidas.";
        }
        if (exception instanceof InternalAuthenticationServiceException) {
            return "Não existe um usuário com as credenciais informadas.";
        }
        if (exception instanceof InsufficientAuthenticationException) {
            return "É necessário estar autenticado para acessar esta funcionalidade.";
        }
        return exception.getMessage();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String mensagem = getMensagemDeErro(authException);
        response.getWriter().write(objectMapper.writeValueAsString(new DtoExcecao(mensagem)));
//        response.getWriter().write(objectMapper.writeValueAsString(authException.getClass().getName()));
    }
}