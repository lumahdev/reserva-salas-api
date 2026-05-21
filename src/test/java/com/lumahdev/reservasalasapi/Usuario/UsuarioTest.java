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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository repository;

    private Usuario cadastraUsuario() {
        return repository.save(new Usuario(new DtoCadastroUsuario("José", "Bezerra", "jose@email.com", "11987590982")));
    }

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    @Test
    void cadastro200() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "11987590982"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("jose@email.com"))
                .andExpect(jsonPath("$.telefone").value("11987590982"));
    }

    @Test
    void cadastro400CorpoVazio() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void cadastro400BrancosOuNulos() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "",
                                "sobrenome": "",
                                "email": "",
                                "telefone": ""
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório."))
                .andExpect(jsonPath("$.sobrenome").value("Sobrenome é obrigatório."))
                .andExpect(jsonPath("$.email").value("E-mail é obrigatório."))
                .andExpect(jsonPath("$.telefone").value("Telefone é obrigatório."));
    }

    @Test
    void cadastro400Invalidos() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "teste",
                                "sobrenome": "teste",
                                "email": "teste",
                                "telefone": "teste"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("E-mail inválido."))
                .andExpect(jsonPath("$.telefone").value("Telefone inválido."));
    }

    @Test
    void cadastro400TelefoneDuplicado() throws Exception {
        cadastraUsuario();
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
        cadastraUsuario();
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
    void cadastro400AmbosDuplicados() throws Exception {
        cadastraUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "11987590982"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void listar200() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].nome").value("José"))
                .andExpect(jsonPath("$[0].sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$[0].telefone").value("11987590982"))
                .andExpect(jsonPath("$[0].email").value("jose@email.com"));
    }

    @Test
    void listar200PorId() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(get("/usuarios/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("jose@email.com"))
                .andExpect(jsonPath("$.telefone").value("11987590982"));
    }

    @Test
    void listar404() throws Exception {
        mockMvc.perform(get("/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Não existe um usuário com este ID."));
    }

    @Test
    void editar200Email() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                            {"email": "joseemail2@email.com"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("joseemail2@email.com"))
                .andExpect(jsonPath("$.telefone").value("11987590982"));;
    }

    @Test
    void editar200Telefone() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                                {"telefone": "11999999999"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("jose@email.com"))
                .andExpect(jsonPath("$.telefone").value("11999999999"));;
    }

    @Test
    void editar200Ambos() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                            {
                                "email": "joseemail2@email.com",
                                "telefone": "11999999999"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("joseemail2@email.com"))
                .andExpect(jsonPath("$.telefone").value("11999999999"));;
    }

    @Test
    void editar400CorpoVazio() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Corpo da requisição é obrigatório."));
        ;
    }

    @Test
    void editar400Invalidos() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                            {
                                "email": "teste",
                                "telefone": "teste"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("E-mail inválido."))
                .andExpect(jsonPath("$.telefone").value("Telefone inválido."));
        ;
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