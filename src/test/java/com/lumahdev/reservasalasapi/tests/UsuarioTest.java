package com.lumahdev.reservasalasapi.tests;

import com.lumahdev.reservasalasapi.domain.TestInterface;
import com.lumahdev.reservasalasapi.domain.TestPai;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuarioTest extends TestPai implements TestInterface {

    @BeforeEach
    public void limparBanco() {
        usuarioRepository.deleteAll();
    }

    @Test
    void cadastro400CorpoVazio() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void cadastro400BrancosOuNulos() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "",
                                "sobrenome": "",
                                "email": "",
                                "telefone": "",
                                "login": "",
                                "senha": ""
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório."))
                .andExpect(jsonPath("$.sobrenome").value("Sobrenome é obrigatório."))
                .andExpect(jsonPath("$.email").value("E-mail é obrigatório."))
                .andExpect(jsonPath("$.telefone").value("Telefone é obrigatório."))
                .andExpect(jsonPath("$.login").value("Login é obrigatório."))
                .andExpect(jsonPath("$.senha").value("Senha é obrigatória."));
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
    void cadastro200() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "11987590982",
                                "login": "jose_be",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.email").value("jose@email.com"))
                .andExpect(jsonPath("$.telefone").value("11987590982"))
                .andExpect(jsonPath("$.login").value("jose_be"));
    }

    @Test
    void cadastro400TelefoneDuplicado() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose2@email.com",
                                "telefone": "11987590982",
                                "login": "jose_be2",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void cadastro400EmailDuplicado() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "99999999999",
                                "login": "jose_be2",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void cadastro400LoginDuplicado() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose2@email.com",
                                "telefone": "9999999999",
                                "login": "jose_be",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void cadastro400AmbosDuplicados() throws Exception {
        criaUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "11987590982",
                                "login": "jose_be",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void listar200() throws Exception {
        criaUsuario();
        mockMvc.perform(get("/usuarios")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].nome").exists())
                .andExpect(jsonPath("$.content[0].sobrenome").exists())
                .andExpect(jsonPath("$.content[0].telefone").exists())
                .andExpect(jsonPath("$.content[0].email").exists())
                .andExpect(jsonPath("$.content[0].login").exists());
    }

    @Test
    void listar200PorId() throws Exception {
        Long usuarioId = criaUsuario();
                mockMvc.perform(get("/usuarios/" + usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.sobrenome").exists())
                .andExpect(jsonPath("$.telefone").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.login").exists());
    }

    @Test
    void listar404() throws Exception {
        criaUsuario();
        mockMvc.perform(get("/usuarios/99999")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }

    @Test
    void editar400CorpoVazio() throws Exception {
        Long usuarioId = criaUsuario();
        mockMvc.perform(put("/usuarios/" + usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void editar400Invalidos() throws Exception {
        Long usuarioId = criaUsuario();
        mockMvc.perform(put("/usuarios/" + usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
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
    }

    @Test
    void editar404() throws Exception {
        criaUsuario();
        mockMvc.perform(put("/usuarios/99999")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {"email": "joseemail2@email.com"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }

    @Test
    void editar200Email() throws Exception {
        Long usuarioId = criaUsuario();
        mockMvc.perform(put("/usuarios/" + usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {"email": "jose2@email.com"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId))
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.telefone").value("11987590982"))
                .andExpect(jsonPath("$.email").value("jose2@email.com"))
                .andExpect(jsonPath(("$.login")).value("jose_be"));
    }

    @Test
    void editar200Telefone() throws Exception {
        Long usuarioId = criaUsuario();
        mockMvc.perform(put("/usuarios/" + usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {"telefone": "11999999999"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId))
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.telefone").value("11999999999"))
                .andExpect(jsonPath("$.email").value("jose@email.com"))
                .andExpect(jsonPath(("$.login")).value("jose_be"));
    }

    @Test
    void editar200Ambos() throws Exception {
        Long usuarioId = criaUsuario();
        mockMvc.perform(put("/usuarios/" + usuarioId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken())
                        .content("""
                            {
                                "email": "jose2@email.com",
                                "telefone": "11999999999"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId))
                .andExpect(jsonPath("$.nome").value("José"))
                .andExpect(jsonPath("$.sobrenome").value("Bezerra"))
                .andExpect(jsonPath("$.telefone").value("11999999999"))
                .andExpect(jsonPath("$.email").value("jose2@email.com"))
                .andExpect(jsonPath(("$.login")).value("jose_be"));
    }

    @Test
    void deletar404() throws Exception {
        criaUsuario();
        mockMvc.perform(delete("/usuarios/99999")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }

//    @Test
//    void deletar200() throws Exception {
//        Long usuarioId = criaUsuario();
//        mockMvc.perform(delete("/usuarios/" + usuarioId)
//                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Usuário deletado com sucesso."));
//        mockMvc.perform(get("/reservas/usuarios/" + usuarioId)
//                        .header(HttpHeaders.AUTHORIZATION, bearerToken()))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID de usuário."));
//    }
}
