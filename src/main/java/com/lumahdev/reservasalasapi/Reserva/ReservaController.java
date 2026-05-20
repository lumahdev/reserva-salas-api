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

//    @GetMapping("/salas/{id}")
//    public ResponseEntity<DtoSala> listarSala(@PathVariable Long id) {
//        Sala sala = service.buscarSala(id);
//        if (sala == null) {
//            throw new Excecao("Não existe uma sala com este ID.");
//        } else {
//            return ResponseEntity.ok(new DtoSala(sala));
//        }
//    }
//
//    @PutMapping("/salas/{id}")
//    public ResponseEntity<DtoSala> mudarDisponibilidadeSala(@PathVariable Long id) {
//        Sala sala = service.mudarDisponibilidadeSala(id);
//        return ResponseEntity.ok(new DtoSala(sala));
//    }
}
