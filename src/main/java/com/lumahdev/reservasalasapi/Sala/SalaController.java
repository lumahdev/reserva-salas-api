package com.lumahdev.reservasalasapi.Sala;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaController {

    @Autowired
    private SalaService service;

    @PostMapping("/salas")
    ResponseEntity<String> cadastrarSala(@RequestBody @Valid DtoCadastroSala dto) {
        try {
            service.cadastrarSala(dto);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
