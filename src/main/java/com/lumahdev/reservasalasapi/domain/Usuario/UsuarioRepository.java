package com.lumahdev.reservasalasapi.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByTelefoneOrEmail(String telefone, String email);
}
