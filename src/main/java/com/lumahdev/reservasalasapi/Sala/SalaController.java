package com.lumahdev.reservasalasapi.Sala;

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
@RequestMapping("salas")
public class SalaController {

    @Autowired
    private SalaService service;

    @PostMapping
    public ResponseEntity<DtoSala> cadastrarSala(@RequestBody @Valid DtoCadastroSala dto, UriComponentsBuilder uriBuilder) {
        Sala sala = service.cadastrarSala(dto);
        URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(sala.getSalaId()).toUri();
        return ResponseEntity.created(uri).body(new DtoSala(sala));
    }

    @GetMapping
    public ResponseEntity<Page<DtoSala>> listarSalas(@PageableDefault(size = 10) Pageable paginacao) {
        Page<DtoSala> salas = service.buscarSalas(paginacao);
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoSala> listarSala(@PathVariable Long id) {
        Sala sala = service.buscarSala(id);
        return ResponseEntity.ok(new DtoSala(sala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoSala> mudarDisponibilidadeSala(@PathVariable Long id) {
        Sala sala = service.mudarDisponibilidadeSala(id);
        return ResponseEntity.ok(new DtoSala(sala));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable Long id) {
        service.deletarSala(id);
        return ResponseEntity.ok("Sala deletada com sucesso.");
    }
}
