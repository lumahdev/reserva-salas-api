package com.lumahdev.reservasalasapi.Sala;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SalaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SalaRepository repository;

    private Sala cadastraSala() {
        return repository.save(new Sala(new DtoCadastroSala("101", 50, "1", "Orquídeas")));
    }

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    @Test
    void cadastro200() throws Exception {
        mockMvc.perform(post("/salas")
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
        mockMvc.perform(post("/salas")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void cadastro400BrancosOuNulos() throws Exception {
        mockMvc.perform(post("/salas")
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
        mockMvc.perform(post("/salas")
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
        mockMvc.perform(post("/salas")
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
        cadastraSala();
        mockMvc.perform(post("/salas")
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
        Sala sala = cadastraSala();
        mockMvc.perform(get("/salas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(sala.getSalaId()))
                .andExpect(jsonPath("$.content[0].nome").value(sala.getNome()))
                .andExpect(jsonPath("$.content[0].capacidade").value(sala.getCapacidade()))
                .andExpect(jsonPath("$.content[0].andar").value(sala.getAndar()))
                .andExpect(jsonPath("$.content[0].bloco").value(sala.getBloco()))
                .andExpect(jsonPath("$.content[0].status").value(sala.getStatus().name()));
    }

    @Test
    void listar200PorId() throws Exception {
        Sala sala = cadastraSala();
        Long salaId = sala.getSalaId();
        mockMvc.perform(get("/salas/" + salaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salaId))
                .andExpect(jsonPath("$.nome").value(sala.getNome()))
                .andExpect(jsonPath("$.capacidade").value(sala.getCapacidade()))
                .andExpect(jsonPath("$.andar").value(sala.getAndar()))
                .andExpect(jsonPath("$.bloco").value(sala.getBloco()))
                .andExpect(jsonPath("$.status").value(sala.getStatus().name()));
    }

    @Test
    void listar404() throws Exception {
        mockMvc.perform(get("/salas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }

    @Test
    void editar200DisponivelParaIndisponivel() throws Exception {
        Sala sala = cadastraSala();
        Long salaId = sala.getSalaId();
        mockMvc.perform(put("/salas/" + salaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salaId))
                .andExpect(jsonPath("$.nome").value(sala.getNome()))
                .andExpect(jsonPath("$.capacidade").value(sala.getCapacidade()))
                .andExpect(jsonPath("$.andar").value(sala.getAndar()))
                .andExpect(jsonPath("$.bloco").value(sala.getBloco()))
                .andExpect(jsonPath("$.status").value("INDISPONIVEL"));
    }

    @Test
    void editar404() throws Exception {
        mockMvc.perform(put("/salas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }

    @Test
    void deletar200() throws Exception {
        Long id = cadastraSala().getSalaId();
        mockMvc.perform(delete("/salas/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Sala deletada com sucesso."));
        mockMvc.perform(get("/reservas/salas/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID de sala."));
    }

    @Test
    void deletar404() throws Exception {
        mockMvc.perform(delete("/salas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }
}