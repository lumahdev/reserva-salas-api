package com.lumahdev.reservasalasapi.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository repository;

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    private String cadastro200 = """
        {
                "nome": "José",
                "sobrenome": "Bezerra",
                "email": "jose@email.com",
                "telefone": "11987590982"
        }
    """;

    private String cadastro4xxBrancosOuNulos = """
        {
                "nome": "",
                "sobrenome": "",
                "email": "",
                "telefone": ""
        }
    """;

    private String cadastro4xxInvalidos = """
        {
                "nome": "teste",
                "sobrenome": "teste",
                "email": "teste",
                "telefone": "teste"
        }
    """;

    @Test
    void cadastro200() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content(cadastro200)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("jose@email.com"))
                .andExpect(jsonPath("$.telefone").value("11987590982"));
    }

    @Test
    void cadastro4xxCorpoVazio() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void cadastro4xxBrancosOuNulos() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content(cadastro4xxBrancosOuNulos)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório."))
                .andExpect(jsonPath("$.sobrenome").value("Sobrenome é obrigatório."))
                .andExpect(jsonPath("$.email").value("E-mail é obrigatório."))
                .andExpect(jsonPath("$.telefone").value("Telefone é obrigatório."));
    }

    @Test
    void cadastro4xxInvalidos() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content(cadastro4xxInvalidos)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("E-mail inválido."))
                .andExpect(jsonPath("$.telefone").value("Telefone inválido."));
    }

    @Test
    void listar200() throws Exception {
        repository.save(
                new Usuario(
                        new DtoCadastroUsuario(
                                "José",
                                "Bezerra",
                                "jose@email.com",
                                "11987590982"
                        )));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nome").exists())
                .andExpect(jsonPath("$[0].sobrenome").exists())
                .andExpect(jsonPath("$[0].telefone").exists())
                .andExpect(jsonPath("$[0].email").exists());;
    }
}