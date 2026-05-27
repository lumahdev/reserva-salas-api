package com.lumahdev.reservasalasapi.Reserva;

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
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @GetMapping("/reservas/usuarios/{id}")
    public ResponseEntity<DtoReserva> listarReservaPorUsuarioId(@PathVariable Long id) {
        Reserva reserva = service.buscarReservaPorUsuarioId(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @GetMapping("/reservas/salas/{id}")
    public ResponseEntity<DtoReserva> listarReservaPorSalaId(@PathVariable Long id) {
        Reserva reserva = service.buscarReservaPorSalaId(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<DtoReserva> mudarDisponibilidadeReserva(@PathVariable Long id) {
        Reserva reserva = service.mudarDisponibilidadeReserva(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable Long id) {
        service.deletarReserva(id);
        return ResponseEntity.ok("Reserva deletada com sucesso.");
    }
}
