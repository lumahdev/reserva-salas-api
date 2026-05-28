package com.lumahdev.reservasalasapi.tests;

import com.lumahdev.reservasalasapi.domain.TestInterface;
import com.lumahdev.reservasalasapi.domain.TestPai;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
                .andExpect(jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    void login400InfosIncorretas() throws Exception {
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
                .andExpect(jsonPath("$.message").value("UserDetailsService returned null, which is an interface contract violation"));
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
}