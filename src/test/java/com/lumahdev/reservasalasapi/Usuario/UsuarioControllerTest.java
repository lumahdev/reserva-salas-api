package com.lumahdev.reservasalasapi.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService service;

    @Test
    @DisplayName("200 quando dados válidos")
    void cadastrarUsuarioComSucesso() throws Exception {
        DtoCadastroUsuario dtoCadastro = new DtoCadastroUsuario("João", "Silva", "joao@email.com", "11999991111");
        Usuario usuarioSalvo = new Usuario(dtoCadastro);

        Mockito.when(service.cadastrarUsuario(Mockito.any(DtoCadastroUsuario.class)))
                .thenReturn(usuarioSalvo);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoCadastro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.sobrenome").value("Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.telefone").value("11999991111"));
    }

    @Test
    @DisplayName("400 quando dados brancos/nulos")
    void cadastrarUsuarioComErros() throws Exception {
        DtoCadastroUsuario dtoCadastro = new DtoCadastroUsuario("", "", "", "");
        Usuario usuarioSalvo = new Usuario(dtoCadastro);

        Mockito.when(service.cadastrarUsuario(Mockito.any(DtoCadastroUsuario.class)))
                .thenReturn(usuarioSalvo);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoCadastro)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório."))
                .andExpect(jsonPath("$.sobrenome").value("Sobrenome é obrigatório."))
                .andExpect(jsonPath("$.email").value("E-mail é obrigatório."))
                .andExpect(jsonPath("$.telefone").value("Telefone é obrigatório."));
    }
}