package com.lumahdev.reservasalasapi.Usuario;

import com.lumahdev.reservasalasapi.Excecao.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private String normalizarTelefone(String telefone) {
        return telefone.replaceAll("\\D", "");
    }

    public Usuario cadastrarUsuario(DtoCadastroUsuario dto) {
        String telefone = normalizarTelefone(dto.telefone());
        if (repository.existsByTelefoneOrEmail(telefone, dto.email())) {
            throw new Excecao("Já existe um usuário cadastrado com estes dados.", HttpStatus.BAD_REQUEST);
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
                .orElseThrow(() -> new Excecao("Não existe um usuário com este ID.", HttpStatus.NOT_FOUND));
    }

    public Usuario editarUsuario(Long id, DtoEditarUsuario dto) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        return repository.save(usuario);
    }
}
