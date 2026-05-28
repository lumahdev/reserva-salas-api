package com.lumahdev.reservasalasapi.domain;

import com.jayway.jsonpath.JsonPath;
import com.lumahdev.reservasalasapi.domain.Reserva.DtoCadastroReserva;
import com.lumahdev.reservasalasapi.domain.Reserva.Reserva;
import com.lumahdev.reservasalasapi.domain.Reserva.ReservaRepository;
import com.lumahdev.reservasalasapi.domain.Sala.DtoCadastroSala;
import com.lumahdev.reservasalasapi.domain.Sala.Sala;
import com.lumahdev.reservasalasapi.domain.Sala.SalaRepository;
import com.lumahdev.reservasalasapi.domain.Usuario.DtoCadastroUsuario;
import com.lumahdev.reservasalasapi.domain.Usuario.Usuario;
import com.lumahdev.reservasalasapi.domain.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPai {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected SalaRepository salaRepository;

    @Autowired
    protected ReservaRepository reservaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected Long criaUsuario() {
        return usuarioRepository
                .save(new Usuario(new DtoCadastroUsuario("José", "Bezerra", "jose@email.com", "11987590982", "jose_be", "Senha@123"), passwordEncoder.encode("Senha@123")))
                .getUsuarioId();
    }

    private String realizaLogin() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .content("""
                        {
                            "login": "jose_be",
                            "senha": "Senha@123"
                        }
                    """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        return JsonPath.read(response, "$.token");
    }

    protected String bearerToken() throws Exception {
        return "Bearer " + realizaLogin();
    }

    protected Long criaSala() {
        return salaRepository
                .save(new Sala(new DtoCadastroSala("101", 50, "1", "Orquídeas")))
                .getSalaId();
    }

    protected Long criaReserva(Long salaId, Long usuarioId) {
        LocalDate dataInicio = LocalDate.parse("2026-05-30");
        LocalDate dataFim = LocalDate.parse("2026-06-30");
        return reservaRepository
                .save(new Reserva(new DtoCadastroReserva(dataInicio, dataFim, salaId, usuarioId)))
                .getReservaId();
    }

    @Autowired
    protected MockMvc mockMvc;
}
