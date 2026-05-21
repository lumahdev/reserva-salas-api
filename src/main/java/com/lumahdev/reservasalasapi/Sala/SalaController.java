package com.lumahdev.reservasalasapi.Sala;

import com.lumahdev.reservasalasapi.Excecao;
import com.lumahdev.reservasalasapi.Usuario.DtoEditarUsuario;
import com.lumahdev.reservasalasapi.Usuario.DtoUsuario;
import com.lumahdev.reservasalasapi.Usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/salas/{id}")
    public ResponseEntity<DtoSala> listarSala(@PathVariable Long id) {
        Sala sala = service.buscarSala(id);
        return ResponseEntity.ok(new DtoSala(sala));
    }

    @PutMapping("/salas/{id}")
    public ResponseEntity<DtoSala> mudarDisponibilidadeSala(@PathVariable Long id) {
        Sala sala = service.mudarDisponibilidadeSala(id);
        return ResponseEntity.ok(new DtoSala(sala));
    }
}
