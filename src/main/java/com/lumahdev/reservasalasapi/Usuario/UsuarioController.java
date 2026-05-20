package com.lumahdev.reservasalasapi.Usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/usuarios")
    ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid DtoCadastroUsuario dto) {
        try {
            service.cadastrarUsuario(dto);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    ResponseEntity<List<DtoUsuario>> listarUsuarios() {
        List<DtoUsuario> usuarios = service.buscarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}