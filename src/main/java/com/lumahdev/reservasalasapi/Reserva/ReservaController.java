package com.lumahdev.reservasalasapi.Reserva;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping("/reservas")
    public ResponseEntity<DtoReserva> cadastrarReserva(@RequestBody @Valid DtoCadastroReserva dto) {
        Reserva reserva = service.cadastrarReserva(dto);
        return ResponseEntity.ok(new DtoReserva(reserva));
    }
}
