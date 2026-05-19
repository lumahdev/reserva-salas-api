package com.lumahdev.reservasalasapi.Usuario;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}