package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Usuario.DtoUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping("/reservas")
    public ResponseEntity<DtoReserva> cadastrarReserva(@RequestBody @Valid DtoCadastroReserva dto, UriComponentsBuilder uriBuilder) {
        Reserva reserva = service.cadastrarReserva(dto);
        URI uri = uriBuilder.path("/reservas/{id}").buildAndExpand(reserva.getReservaId()).toUri();
        return ResponseEntity.created(uri).body(new DtoReserva(reserva));
    }

    @GetMapping("/reservas")
    public ResponseEntity<Page<DtoReserva>> listarReservas(Pageable paginacao) {
        Page<DtoReserva> reservas = service.buscarReservas(paginacao);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<DtoReserva> listarReserva(@PathVariable Long id) {
        Reserva reserva = service.buscarReserva(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @GetMapping("/reservas/usuarios/{id}")
    public ResponseEntity<Page<DtoReserva>> listarReservaPorUsuarioId(@PathVariable Long id, Pageable paginacao) {
        Page<DtoReserva> reservas = service.buscarReservaPorUsuarioId(id, paginacao);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/reservas/salas/{id}")
    public ResponseEntity<Page<DtoReserva>> listarReservaPorSalaId(@PathVariable Long id, Pageable paginacao) {
        Page<DtoReserva> reservas = service.buscarReservaPorSalaId(id, paginacao);
        return ResponseEntity.ok(reservas);
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
