package com.lumahdev.reservasalasapi.infra.security;

import com.lumahdev.reservasalasapi.domain.Excecao.DtoExcecao;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class HandleAuthExceptions {

    @Autowired
    private ObjectMapper objectMapper;

    public HandleAuthExceptions(String mensagem, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(new DtoExcecao(mensagem)));
    }
}
