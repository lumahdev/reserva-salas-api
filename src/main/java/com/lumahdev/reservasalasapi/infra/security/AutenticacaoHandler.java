package com.lumahdev.reservasalasapi.infra.security;

import com.lumahdev.reservasalasapi.domain.Excecao.DtoExcecao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class AutenticacaoHandler implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    private String trataException(AuthenticationException exception, HttpServletResponse response) {
        if (exception instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Credenciais inválidas.";
        }
        if (exception instanceof InternalAuthenticationServiceException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "Não existe um usuário com as credenciais informadas.";
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return exception.getMessage();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        String mensagem = trataException(authException, response);
        response.getWriter().write(objectMapper.writeValueAsString(new DtoExcecao(mensagem)));
//        response.getWriter().write(objectMapper.writeValueAsString(authException.getClass().getName()));
    }
}