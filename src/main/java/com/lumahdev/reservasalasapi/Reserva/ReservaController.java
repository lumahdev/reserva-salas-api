package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Excecao;
import com.lumahdev.reservasalasapi.Sala.DtoSala;
import com.lumahdev.reservasalasapi.Sala.Sala;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping("/reservas")
    public ResponseEntity<DtoReserva> cadastrarReserva(@RequestBody @Valid DtoCadastroReserva dto) {
        Reserva reserva = service.cadastrarReserva(dto);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<DtoReserva>> listarReservas() {
        List<DtoReserva> reservas = service.buscarReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<DtoReserva> listarReserva(@PathVariable Long id) {
        Reserva reserva = service.buscarReserva(id);
        if (reserva == null) {
            throw new Excecao("Não existe uma reserva com este ID.");
        } else {
            return ResponseEntity.ok(new DtoReserva(reserva));
        }
    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<DtoReserva> mudarDisponibilidadeReserva(@PathVariable Long id) {
        Reserva reserva = service.mudarDisponibilidadeReserva(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }
}
