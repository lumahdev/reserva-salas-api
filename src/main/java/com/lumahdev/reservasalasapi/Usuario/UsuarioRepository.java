package com.lumahdev.reservasalasapi.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existePorTelefoneOuEmail(String telefone, String email);
}
