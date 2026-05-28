package com.lumahdev.reservasalasapi.domain;

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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPai {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected SalaRepository salaRepository;

    @Autowired
    protected ReservaRepository reservaRepository;

    protected Usuario cadastraUsuario() {
        return usuarioRepository.save(new Usuario(new DtoCadastroUsuario("José", "Bezerra", "jose@email.com", "11987590982")));
    }

    protected Sala cadastraSala() {
        return salaRepository.save(new Sala(new DtoCadastroSala("101", 50, "1", "Orquídeas")));
    }

    protected Reserva cadastraReserva(Long salaId, Long usuarioId) {
        LocalDate dataInicio = LocalDate.parse("2026-05-30");
        LocalDate dataFim = LocalDate.parse("2026-06-30");
        return reservaRepository.save(new Reserva(new DtoCadastroReserva(dataInicio, dataFim, salaId, usuarioId)));
    }
}
