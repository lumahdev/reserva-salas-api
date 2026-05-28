package com.lumahdev.reservasalasapi.Usuario;

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
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<DtoUsuario> cadastrarUsuario(@RequestBody @Valid DtoCadastroUsuario dto, UriComponentsBuilder uriBuilder) {
        Usuario usuario = service.cadastrarUsuario(dto);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(usuario.getUsuarioId()).toUri();
        return ResponseEntity.created(uri).body(new DtoUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DtoUsuario>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacao) {
        Page<DtoUsuario> usuarios = service.buscarUsuarios(paginacao);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUsuario> listarUsuario(@PathVariable Long id) {
        Usuario usuario = service.buscarUsuario(id);
        return ResponseEntity.ok(new DtoUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoUsuario> editarUsuario(@PathVariable Long id, @RequestBody @Valid DtoEditarUsuario dto) {
        Usuario usuario = service.editarUsuario(id, dto);
        return ResponseEntity.ok(new DtoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso.");
    }
}