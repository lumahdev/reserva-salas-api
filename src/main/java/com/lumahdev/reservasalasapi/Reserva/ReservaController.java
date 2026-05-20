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
    ResponseEntity<String> cadastrarReserva(@RequestBody @Valid DtoCadastroReserva dto) {
        try {
            service.cadastrarReserva(dto);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
