package com.lumahdev.reservasalasapi.domain.Usuario;

import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import com.lumahdev.reservasalasapi.domain.Reserva.ReservaRepository;
import com.lumahdev.reservasalasapi.domain.Usuario.dtos.DtoCadastroUsuario;
import com.lumahdev.reservasalasapi.domain.Usuario.dtos.DtoEditarUsuario;
import com.lumahdev.reservasalasapi.domain.Usuario.dtos.DtoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrarUsuario(DtoCadastroUsuario dto) {
        if (repository.existsByTelefoneOrEmailOrLogin(dto.telefone(), dto.email(), dto.login())) {
            throw new Excecao("Já existe um usuário cadastrado com estes dados.", HttpStatus.BAD_REQUEST);
        }
        String hash = passwordEncoder.encode(dto.senha());
        return repository.save(new Usuario(dto, hash));
    }

    public Page<DtoUsuario> buscarUsuarios(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(DtoUsuario::new);
    }

    public Usuario buscarUsuario(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new Excecao("Não existe um usuário com este ID.", HttpStatus.NOT_FOUND));
    }

    public Usuario editarUsuario(Long id, DtoEditarUsuario dto) {
        Usuario usuario = buscarUsuario(id);
        if(dto.email() != null){
            usuario.setEmail(dto.email());
        }
        if(dto.telefone() != null ){
            usuario.setTelefone(dto.telefone());
        }
        return repository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuario(id);
        if(usuario != null) {
            repository.deleteById(id);
            reservaRepository.deleteAllByUsuarioId(id);
        }
    }
}
