package com.lumahdev.reservasalasapi.Sala;

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
public class SalaController {

    @Autowired
    private SalaService service;

    @PostMapping("/salas")
    public ResponseEntity<DtoSala> cadastrarSala(@RequestBody @Valid DtoCadastroSala dto, UriComponentsBuilder uriBuilder) {
        Sala sala = service.cadastrarSala(dto);
        URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(sala.getSalaId()).toUri();
        return ResponseEntity.created(uri).body(new DtoSala(sala));
    }

    @GetMapping("/salas")
    public ResponseEntity<Page<DtoSala>> listarSalas(Pageable paginacao) {
        Page<DtoSala> salas = service.buscarSalas(paginacao);
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

    @DeleteMapping("/salas/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable Long id) {
        service.deletarSala(id);
        return ResponseEntity.ok("Sala deletada com sucesso.");
    }
}
