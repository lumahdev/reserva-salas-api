package com.lumahdev.reservasalasapi.Sala;

import com.lumahdev.reservasalasapi.Usuario.DtoUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalaController {

    @Autowired
    private SalaService service;

    @PostMapping("/salas")
    public ResponseEntity<DtoSala> cadastrarSala(@RequestBody @Valid DtoCadastroSala dto) {
        Sala sala = service.cadastrarSala(dto);
        return ResponseEntity.ok(new DtoSala(sala));
    }

    @GetMapping("/salas")
    public ResponseEntity<List<DtoSala>> listarSalas() {
        List<DtoSala> salas = service.buscarSalas();
        return ResponseEntity.ok(salas);
    }
}
