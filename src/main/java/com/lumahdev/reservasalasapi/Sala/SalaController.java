package com.lumahdev.reservasalasapi.Sala;

import com.lumahdev.reservasalasapi.Usuario.DtoUsuario;
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
    public ResponseEntity<DtoSala> cadastrarSala(@RequestBody @Valid DtoCadastroSala dto) {
        Sala sala = service.cadastrarSala(dto);
        return ResponseEntity.ok(new DtoSala(sala));
    }
}
