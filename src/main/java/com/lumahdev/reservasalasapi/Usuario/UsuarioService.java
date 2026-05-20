package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public void cadastrarUsuario(DtoCadastroUsuario dto) {
        boolean jaCadastrado = repository.existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if(jaCadastrado) {
            throw new Excecao("Já existe um usuário cadastrado com estes dados.");
        }

        repository.save(new Usuario(dto));
    }

    public List<DtoUsuario> buscarUsuarios() {
        return repository
                .findAll()
                .stream()
                .map(DtoUsuario::new)
                .toList();
    }
}
