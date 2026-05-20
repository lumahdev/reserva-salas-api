package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao;
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
    public ResponseEntity<DtoUsuario> cadastrarUsuario(@RequestBody @Valid DtoCadastroUsuario dto) {
        Usuario usuario = service.cadastrarUsuario(dto);
        return ResponseEntity.ok(new DtoUsuario(usuario));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<DtoUsuario>> listarUsuarios() {
        List<DtoUsuario> usuarios = service.buscarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<DtoUsuario> listarUsuario(@PathVariable Long id) {
        Usuario usuario = service.buscarUsuario(id);
        if (usuario == null) {
            throw new Excecao("Não existe um usuário com este ID.");
        } else {
            return ResponseEntity.ok(new DtoUsuario(usuario));
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<DtoUsuario> editarUsuario(@PathVariable Long id, @RequestBody @Valid DtoEditarUsuario dto) {
        Usuario usuario = service.editarUsuario(id, dto);
        return ResponseEntity.ok(new DtoUsuario(usuario));

    }
}