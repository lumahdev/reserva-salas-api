package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario cadastrarUsuario(DtoCadastroUsuario dto) {
        if (repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())) {
            throw new Excecao("Já existe um usuário cadastrado com estes dados.");
        }
        return repository.save(new Usuario(dto));
    }

    public List<DtoUsuario> buscarUsuarios() {
        return repository
                .findAll()
                .stream()
                .map(DtoUsuario::new)
                .toList();
    }

    public Usuario buscarUsuario(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new Excecao("Não existe um usuário com este ID."));
    }

    public Usuario editarUsuario(Long id, DtoEditarUsuario dto) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        return repository.save(usuario);
    }
}
