package com.lumahdev.reservasalasapi.Usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/usuarios")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid DtoCadastroUsuario dto) {
        try {
            service.cadastrarUsuario(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<DtoUsuario>> listarUsuarios() {
        List<DtoUsuario> usuarios = service.buscarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<String> editarUsuario(@PathVariable Long id, @RequestBody @Valid DtoEditarUsuario dto) {
        try {
            service.editarUsuario(id, dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}