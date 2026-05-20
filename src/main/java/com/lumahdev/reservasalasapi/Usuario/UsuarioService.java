package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void editarUsuario(Long id, DtoEditarUsuario dto) {
        Usuario usuario = repository
                .findById(id)
                .orElseThrow(() -> new Excecao("Não existe um usuário com este ID."));

        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());

        repository.save(usuario);
    }
}
