package com.lumahdev.reservasalasapi.domain.Reserva;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<DtoReserva> cadastrarReserva(@RequestBody @Valid DtoCadastroReserva dto, UriComponentsBuilder uriBuilder) {
        Reserva reserva = service.cadastrarReserva(dto);
        URI uri = uriBuilder.path("/reservas/{id}").buildAndExpand(reserva.getReservaId()).toUri();
        return ResponseEntity.created(uri).body(new DtoReserva(reserva));
    }

    @GetMapping
    public ResponseEntity<Page<DtoReserva>> listarReservas(@PageableDefault(size = 10) Pageable paginacao) {
        Page<DtoReserva> reservas = service.buscarReservas(paginacao);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoReserva> listarReserva(@PathVariable Long id) {
        Reserva reserva = service.buscarReserva(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Page<DtoReserva>> listarReservaPorUsuarioId(@PathVariable Long id, @PageableDefault(size = 10) Pageable paginacao) {
        Page<DtoReserva> reservas = service.buscarReservaPorUsuarioId(id, paginacao);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/salas/{id}")
    public ResponseEntity<Page<DtoReserva>> listarReservaPorSalaId(@PathVariable Long id, @PageableDefault(size = 10) Pageable paginacao) {
        Page<DtoReserva> reservas = service.buscarReservaPorSalaId(id, paginacao);
        return ResponseEntity.ok(reservas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoReserva> mudarDisponibilidadeReserva(@PathVariable Long id) {
        Reserva reserva = service.mudarDisponibilidadeReserva(id);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable Long id) {
        service.deletarReserva(id);
        return ResponseEntity.ok("Reserva deletada com sucesso.");
    }
}
