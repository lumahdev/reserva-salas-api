package com.lumahdev.reservasalasapi.Usuario;

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
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/usuarios")
    public ResponseEntity<DtoUsuario> cadastrarUsuario(@RequestBody @Valid DtoCadastroUsuario dto, UriComponentsBuilder uriBuilder) {
        Usuario usuario = service.cadastrarUsuario(dto);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getUsuarioId()).toUri();
        return ResponseEntity.created(uri).body(new DtoUsuario(usuario));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Page<DtoUsuario>> listarUsuarios(Pageable paginacao) {
        Page<DtoUsuario> usuarios = service.buscarUsuarios(paginacao);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<DtoUsuario> listarUsuario(@PathVariable Long id) {
        Usuario usuario = service.buscarUsuario(id);
        return ResponseEntity.ok(new DtoUsuario(usuario));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<DtoUsuario> editarUsuario(@PathVariable Long id, @RequestBody @Valid DtoEditarUsuario dto) {
        Usuario usuario = service.editarUsuario(id, dto);
        return ResponseEntity.ok(new DtoUsuario(usuario));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso.");
    }
}