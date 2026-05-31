package com.lumahdev.reservasalasapi.tests;

import com.lumahdev.reservasalasapi.domain.TestInterface;
import com.lumahdev.reservasalasapi.domain.TestPai;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SalaTest extends TestPai implements TestInterface {

    @BeforeEach
    public void limparBanco() {
        usuarioRepository.deleteAll();
        salaRepository.deleteAll();
    }

    @Test
    void cadastro200() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {
                                "nome": "101",
                                "capacidade": 50,
                                "andar": "1",
                                "bloco": "Orquídeas"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("101"))
                .andExpect(jsonPath("$.capacidade").value(50))
                .andExpect(jsonPath("$.andar").value("1"))
                .andExpect(jsonPath("$.bloco").value("Orquídeas"))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));
    }

    @Test
    void cadastro400CorpoVazio() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void cadastro400BrancosOuNulos() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {
                                "nome": "",
                                "capacidade": null,
                                "andar": "",
                                "bloco": ""
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório."))
                .andExpect(jsonPath("$.capacidade").value("Capacidade é obrigatória."))
                .andExpect(jsonPath("$.andar").value("Andar é obrigatório."))
                .andExpect(jsonPath("$.bloco").value("Bloco é obrigatório."));
    }

    @Test
    void cadastro400CapacidadeNegativa() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {
                                "nome": "101",
                                "capacidade": -50,
                                "andar": "1",
                                "bloco": "Orquídeas"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.capacidade").value("Capacidade deve ser um número positivo."));
    }

    @Test
    void cadastro400CapacidadeZero() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {
                                "nome": "101",
                                "capacidade": 0,
                                "andar": "1",
                                "bloco": "Orquídeas"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.capacidade").value("Capacidade deve ser um número positivo."));
    }

    @Test
    void cadastro400NomeDuplicado() throws Exception {
        criaUsuario();
        criaSala();
        mockMvc.perform(post("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {
                                "nome": "101",
                                "capacidade": 50,
                                "andar": "1",
                                "bloco": "Orquídeas"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe uma sala cadastrada com estes dados."));
    }

    @Test
    void listar200() throws Exception {
        criaUsuario();
        criaSala();
        mockMvc.perform(get("/salas")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].nome").exists())
                .andExpect(jsonPath("$.content[0].capacidade").exists())
                .andExpect(jsonPath("$.content[0].andar").exists())
                .andExpect(jsonPath("$.content[0].bloco").exists())
                .andExpect(jsonPath("$.content[0].status").exists());
    }

    @Test
    void listar200PorId() throws Exception {
        criaUsuario();
        Long salaId = criaSala();
        mockMvc.perform(get("/salas/" + salaId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salaId))
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.capacidade").exists())
                .andExpect(jsonPath("$.andar").exists())
                .andExpect(jsonPath("$.bloco").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    void listar404() throws Exception {
        criaUsuario();
        mockMvc.perform(get("/salas/99999")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }

    @Test
    void editar200DisponivelParaIndisponivel() throws Exception {
        criaUsuario();
        Long salaId = criaSala();
        mockMvc.perform(put("/salas/" + salaId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salaId))
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.capacidade").exists())
                .andExpect(jsonPath("$.andar").exists())
                .andExpect(jsonPath("$.bloco").exists())
                .andExpect(jsonPath("$.status").value("INDISPONIVEL"));
    }

    @Test
    void editar404() throws Exception {
        criaUsuario();
        mockMvc.perform(put("/salas/99999")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }

    @Test
    void deletar200() throws Exception {
        criaUsuario();
        Long salaId = criaSala();
        mockMvc.perform(delete("/salas/" + salaId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isOk())
                .andExpect(content().string("Sala deletada com sucesso."));
        mockMvc.perform(get("/reservas/salas/" + salaId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID de sala."));
    }

    @Test
    void deletar404() throws Exception {
        criaUsuario();
        mockMvc.perform(delete("/salas/99999")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }
}