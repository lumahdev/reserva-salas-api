package com.lumahdev.reservasalasapi.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository repository;

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    private Long cadastraUsuarioRetornaId() {
        return repository
                .save(new Usuario(new DtoCadastroUsuario("José", "Bezerra", "jose@email.com", "11987590982")))
                .getUsuarioId();
    }

    @Test
    void cadastro400TelefoneDuplicado() throws Exception {
        Long id = cadastraUsuarioRetornaId();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "joseemail2@email.com",
                                "telefone": "11987590982"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void cadastro400EmailDuplicado() throws Exception {
        Long id = cadastraUsuarioRetornaId();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "11999999999"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void listar404() throws Exception {
        mockMvc.perform(get("/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Não existe um usuário com este ID."));
    }

    @Test
    void editar404() throws Exception {
        mockMvc.perform(put("/usuarios/99999")
                        .content("""
                            {"email": "joseemail2@email.com"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Não existe um usuário com este ID."));
    }
}
