package com.lumahdev.reservasalasapi.tests;

import com.lumahdev.reservasalasapi.domain.TestInterface;
import com.lumahdev.reservasalasapi.domain.TestPai;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginTest extends TestPai implements TestInterface {

    @BeforeEach
    public void limparBanco() {
        usuarioRepository.deleteAll();
    }

    @Test
    void login400SenhaIncorreta() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/auth/login")
                        .content("""
                            {
                                "login": "jose_be",
                                "senha": "naosei"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Credenciais inválidas."));
    }

    @Test
    void login400UsuarioInexistente() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content("""
                            {
                                "login": "naosei",
                                "senha": "naosei"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com as credenciais informadas."));
    }

    @Test
    void login200() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/auth/login")
                        .content("""
                            {
                                "login": "jose_be",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void naoAutenticado400() throws Exception {
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("É necessário estar autenticado para acessar esta funcionalidade."));
    }

    @Test
    void tokenInvalido400() throws Exception {
        mockMvc.perform(get("/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer a"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O token informado tem um formato inválido."));
    }

    @Test
    void tokenExpirado401() throws Exception {
        mockMvc.perform(get("/usuarios")
                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyZXNlcnZhLXNhbGFzLWFwaSIsInN1YiI6Impvc2VfYmUiLCJleHAiOjE3ODAxNTE3NzZ9.JBCX92bf5AnAMjuNN4J5a1dGmWygXxntegkmRGAPt1c"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("O token informado está expirado."));
    }
}