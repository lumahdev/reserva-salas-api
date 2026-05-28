package com.lumahdev.reservasalasapi;

import com.lumahdev.reservasalasapi.Reserva.DtoCadastroReserva;
import com.lumahdev.reservasalasapi.Reserva.Reserva;
import com.lumahdev.reservasalasapi.Reserva.ReservaRepository;
import com.lumahdev.reservasalasapi.Sala.DtoCadastroSala;
import com.lumahdev.reservasalasapi.Sala.Sala;
import com.lumahdev.reservasalasapi.Sala.SalaRepository;
import com.lumahdev.reservasalasapi.Usuario.DtoCadastroUsuario;
import com.lumahdev.reservasalasapi.Usuario.Usuario;
import com.lumahdev.reservasalasapi.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class Test {

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
