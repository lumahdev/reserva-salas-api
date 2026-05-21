package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao;
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

    private Usuario usuarioValido = new Usuario(new DtoCadastroUsuario("João", "Silva", "joao@email.com", "11999991111"));
    private Usuario usuarioComBrancosOuNulos = new Usuario(new DtoCadastroUsuario("", "", "", ""));
    private Usuario usuarioComInformacoesInvalidas = new Usuario(new DtoCadastroUsuario("João", "Silva", "teste", "abc"));

    @Test
    @DisplayName("200 quando dados válidos")
    void cadastrarComSucesso() throws Exception {
        Usuario usuario = usuarioValido;
        Mockito.when(service.cadastrarUsuario(Mockito.any(DtoCadastroUsuario.class)))
                .thenReturn(usuario);
        mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.sobrenome").value("Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.telefone").value("11999991111"));
    }

    @Test
    @DisplayName("400 quando dados brancos/nulos")
    void cadastrarComBrancosOuNulos() throws Exception {
        Usuario usuario = usuarioComBrancosOuNulos;
        Mockito.when(service.cadastrarUsuario(Mockito.any(DtoCadastroUsuario.class)))
                .thenReturn(usuario);
        mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório."))
                .andExpect(jsonPath("$.sobrenome").value("Sobrenome é obrigatório."))
                .andExpect(jsonPath("$.email").value("E-mail é obrigatório."))
                .andExpect(jsonPath("$.telefone").value("Telefone é obrigatório."));
    }

    @Test
    @DisplayName("400 quando email OU telefone inválidos")
    void cadastrarComInformacoesInvalidas() throws Exception {
        Usuario usuario = usuarioComInformacoesInvalidas;
        Mockito.when(service.cadastrarUsuario(Mockito.any(DtoCadastroUsuario.class)))
                .thenReturn(usuario);
        mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.email").value("E-mail inválido."))
                .andExpect(jsonPath("$.telefone").value("Telefone inválido."));
    }

    @Test
    @DisplayName("400 quando telefone OU email ja cadastrados")
    void cadastrarComInformacoesJaExistentes() throws Exception {
        Usuario usuario = usuarioValido;
        Mockito.when(service.cadastrarUsuario(Mockito.any(DtoCadastroUsuario.class)))
                .thenThrow(new Excecao("Já existe um usuário cadastrado com estes dados."));
        mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().is4xxClientError());
    }
}