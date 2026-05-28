package com.lumahdev.reservasalasapi.tests;

import com.lumahdev.reservasalasapi.domain.Usuario.Usuario;
import com.lumahdev.reservasalasapi.domain.TestInterface;
import com.lumahdev.reservasalasapi.domain.TestPai;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioTest extends TestPai implements TestInterface {

    @BeforeEach
    public void limparBanco() {
        usuarioRepository.deleteAll();
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
    void cadastro400TelefoneDuplicado() throws Exception {
        cadastraUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "joseemail2@email.com",
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
    void cadastro400EmailDuplicado() throws Exception {
        cadastraUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "11999999999",
                                "login": "jose_be",
                                "senha": "Senha@123"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe um usuário cadastrado com estes dados."));
    }

    @Test
    void cadastro400LoginDuplicado() throws Exception {
        cadastraUsuario();
        mockMvc.perform(post("/usuarios")
                        .content("""
                            {
                                "nome": "José",
                                "sobrenome": "Bezerra",
                                "email": "jose@email.com",
                                "telefone": "1199999499",
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
        cadastraUsuario();
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
        Usuario usuario = cadastraUsuario();
        mockMvc.perform(get("/usuarios"))
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
        Usuario usuario = cadastraUsuario();
        Long id = usuario.getUsuarioId();
        mockMvc.perform(get("/usuarios/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(usuario.getNome()))
                .andExpect(jsonPath("$.sobrenome").value(usuario.getSobrenome()))
                .andExpect(jsonPath("$.telefone").value(usuario.getTelefone()))
                .andExpect(jsonPath("$.email").value(usuario.getEmail()))
                .andExpect(jsonPath("$.login").value(usuario.getLogin()));
    }

    @Test
    void listar404() throws Exception {
        mockMvc.perform(get("/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }

    @Test
    void editar200Email() throws Exception {
        Usuario usuario = cadastraUsuario();
        Long id = usuario.getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                            {"email": "joseemail2@email.com"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(usuario.getNome()))
                .andExpect(jsonPath("$.sobrenome").value(usuario.getSobrenome()))
                .andExpect(jsonPath("$.telefone").value(usuario.getTelefone()))
                .andExpect(jsonPath("$.email").value("joseemail2@email.com"));
    }

    @Test
    void editar200Telefone() throws Exception {
        Usuario usuario = cadastraUsuario();
        Long id = usuario.getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                            {"telefone": "11999999999"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(usuario.getNome()))
                .andExpect(jsonPath("$.sobrenome").value(usuario.getSobrenome()))
                .andExpect(jsonPath("$.telefone").value("11999999999"))
                .andExpect(jsonPath("$.email").value(usuario.getEmail()));
    }

    @Test
    void editar200Ambos() throws Exception {
        Usuario usuario = cadastraUsuario();
        Long id = usuario.getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("""
                            {
                                "email": "joseemail2@email.com",
                                "telefone": "11999999999"
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(usuario.getNome()))
                .andExpect(jsonPath("$.sobrenome").value(usuario.getSobrenome()))
                .andExpect(jsonPath("$.telefone").value("11999999999"))
                .andExpect(jsonPath("$.email").value("joseemail2@email.com"));
    }

    @Test
    void editar400CorpoVazio() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(put("/usuarios/" + id)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Corpo da requisição é obrigatório."));
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
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }

    @Test
    void deletar200() throws Exception {
        Long id = cadastraUsuario().getUsuarioId();
        mockMvc.perform(delete("/usuarios/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário deletado com sucesso."));
        mockMvc.perform(get("/reservas/usuarios/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID de usuário."));
    }

    @Test
    void deletar404() throws Exception {
        mockMvc.perform(delete("/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }
}