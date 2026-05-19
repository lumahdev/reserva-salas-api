package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public void cadastrarUsuario(DtoCadastroUsuario dto) {
        boolean jaCadastrado = repository.existePorTelefoneOuEmail(dto.telefone(), dto.email());

        if(jaCadastrado) {
            throw new Excecao("Já existe um usuário cadastrado com estes dados.");
        }

        repository.save(new Usuario(dto));
    }
}
