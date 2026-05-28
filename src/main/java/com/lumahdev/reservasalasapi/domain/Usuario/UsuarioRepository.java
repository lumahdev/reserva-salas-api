package com.lumahdev.reservasalasapi.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByTelefoneOrEmailOrLogin(String telefone, String email, String login);
    UserDetails findByLogin(String login);
}