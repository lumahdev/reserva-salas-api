package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Sala.DtoCadastroSala;
import com.lumahdev.reservasalasapi.Sala.Sala;
import com.lumahdev.reservasalasapi.Sala.SalaRepository;
import com.lumahdev.reservasalasapi.Usuario.DtoCadastroUsuario;
import com.lumahdev.reservasalasapi.Usuario.Usuario;
import com.lumahdev.reservasalasapi.Usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private Usuario cadastraUsuario() {
        return usuarioRepository.save(new Usuario(new DtoCadastroUsuario("José", "Bezerra", "jose@email.com", "11987590982")));
    }

    private Sala cadastraSala() {
        return salaRepository.save(new Sala(new DtoCadastroSala("101", 50, "1", "Orquídeas")));
    }

    private Reserva cadastraReserva(Long salaId, Long usuarioId) {
        LocalDate dataInicio = LocalDate.parse("2026-05-30");
        LocalDate dataFim = LocalDate.parse("2026-06-30");
        return reservaRepository.save(new Reserva(new DtoCadastroReserva(dataInicio,dataFim,salaId,usuarioId)));
    }

    @BeforeEach
    void limparBanco() {
        reservaRepository.deleteAll();
    }

    @Test
    void cadastro200() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "2026-05-30",
                                "dataFim": "2026-06-30",
                                "salaId": %d,
                                "usuarioId": %d
                            }
                        """.formatted(
                                salaId,
                                usuarioId
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.dataInicio").value("2026-05-30"))
                .andExpect(jsonPath("$.dataFim").value("2026-06-30"))
                .andExpect(jsonPath("$.salaId").value(salaId))
                .andExpect(jsonPath("$.usuarioId").value(usuarioId))
                .andExpect(jsonPath("$.status").value("ATIVA"));
    }

    @Test
    void cadastro400CorpoVazio() throws Exception {
        mockMvc.perform(post("/reservas")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Corpo da requisição é obrigatório."));
    }

    @Test
    void cadastro400BrancosOuNulos() throws Exception {
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "",
                                "dataFim": "",
                                "salaId": null,
                                "usuarioId": null
                            }
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dataInicio").value("Data de início é obrigatória."))
                .andExpect(jsonPath("$.dataFim").value("Data de fim é obrigatória."))
                .andExpect(jsonPath("$.salaId").value("Identificação da sala é obrigatória."))
                .andExpect(jsonPath("$.usuarioId").value("Identificação do usuário é obrigatória."));
    }

    @Test
    void cadastro400Invalidos() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "2000-01-01",
                                "dataFim": "2000-01-02",
                                "salaId": %d,
                                "usuarioId": %d
                            }
                        """.formatted(
                                usuarioId,
                                salaId
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dataInicio").value("Data de início não pode estar no passado. AAAA-MM-DD"))
                .andExpect(jsonPath("$.dataFim").value("Data de fim não pode estar no passado. AAAA-MM-DD"));
    }

    @Test
    void cadastro400DataFimAnterior() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "2026-05-30",
                                "dataFim": "2026-05-29",
                                "salaId": %d,
                                "usuarioId": %d
                            }
                        """.formatted(
                                salaId,
                                usuarioId
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Data final não pode ser anterior à data inicial."));
    }

    @Test
    void cadastro400SalaJaOcupada() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        cadastraReserva(salaId, usuarioId);
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "2026-05-30",
                                "dataFim": "2026-06-30",
                                "salaId": %d,
                                "usuarioId": %d
                            }
                        """.formatted(
                                salaId,
                                usuarioId
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Já existe uma reserva para esta sala no período especificado."));
    }

    @Test
    void cadastro404Sala() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "2026-05-30",
                                "dataFim": "2026-06-30",
                                "salaId": 99999,
                                "usuarioId": %d
                            }
                        """.formatted(
                                usuarioId
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }

    @Test
    void cadastro404Usuario() throws Exception {
        Long salaId = cadastraSala().getSalaId();
        mockMvc.perform(post("/reservas")
                        .content("""
                            {
                                "dataInicio": "2026-05-30",
                                "dataFim": "2026-06-30",
                                "salaId": %d,
                                "usuarioId": 9999
                            }
                        """.formatted(
                                salaId
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe um usuário com este ID."));
    }

    @Test
    void listar200() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        Reserva reserva = cadastraReserva(salaId, usuarioId);
        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].dataInicio").exists())
                .andExpect(jsonPath("$.content[0].dataFim").exists())
                .andExpect(jsonPath("$.content[0].salaId").exists())
                .andExpect(jsonPath("$.content[0].usuarioId").exists())
                .andExpect(jsonPath("$.content[0].status").exists());
    }

    @Test
    void listar200PorId() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        Reserva reserva = cadastraReserva(salaId, usuarioId);
        Long reservaId = reserva.getReservaId();
        mockMvc.perform(get("/reservas/" + reservaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.dataInicio").exists())
                .andExpect(jsonPath("$.dataFim").exists())
                .andExpect(jsonPath("$.salaId").exists())
                .andExpect(jsonPath("$.usuarioId").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    void listar404PorId() throws Exception {
        mockMvc.perform(get("/reservas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID."));
    }

    @Test
    void listar200PorUsuarioId() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        Reserva reserva = cadastraReserva(salaId, usuarioId);
        Long reservaId = reserva.getReservaId();
        mockMvc.perform(get("/reservas/usuarios/" + usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].dataInicio").exists())
                .andExpect(jsonPath("$.content[0].dataFim").exists())
                .andExpect(jsonPath("$.content[0].salaId").exists())
                .andExpect(jsonPath("$.content[0].usuarioId").exists())
                .andExpect(jsonPath("$.content[0].status").exists());
    }

    @Test
    void listar404PorUsuarioId() throws Exception {
        mockMvc.perform(get("/reservas/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID de usuário."));
    }

    @Test
    void listar200PorSalaId() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        Reserva reserva = cadastraReserva(salaId, usuarioId);
        Long reservaId = reserva.getReservaId();
        mockMvc.perform(get("/reservas/salas/" + salaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].dataInicio").exists())
                .andExpect(jsonPath("$.content[0].dataFim").exists())
                .andExpect(jsonPath("$.content[0].salaId").exists())
                .andExpect(jsonPath("$.content[0].usuarioId").exists())
                .andExpect(jsonPath("$.content[0].status").exists());
    }

    @Test
    void listar404PorSalaId() throws Exception {
        mockMvc.perform(get("/reservas/salas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID de sala."));
    }

    @Test
    void editar200AtivaParaCancelada() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        Reserva reserva = cadastraReserva(salaId, usuarioId);
        Long reservaId = reserva.getReservaId();
        mockMvc.perform(put("/reservas/" + reservaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.dataInicio").exists())
                .andExpect(jsonPath("$.dataFim").exists())
                .andExpect(jsonPath("$.salaId").exists())
                .andExpect(jsonPath("$.usuarioId").exists())
                .andExpect(jsonPath("$.status").value("CANCELADA"));
    }

    @Test
    void editar404() throws Exception {
        mockMvc.perform(put("/salas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma sala com este ID."));
    }

    @Test
    void deletar200() throws Exception {
        Long usuarioId = cadastraUsuario().getUsuarioId();
        Long salaId = cadastraSala().getSalaId();
        Reserva reserva = cadastraReserva(salaId, usuarioId);
        Long reservaId = reserva.getReservaId();
        mockMvc.perform(delete("/reservas/" + reservaId))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva deletada com sucesso."));
    }

    @Test
    void deletar404() throws Exception {
        mockMvc.perform(delete("/reservas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não existe uma reserva com este ID."));
    }
}
