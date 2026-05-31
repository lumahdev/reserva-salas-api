package com.lumahdev.reservasalasapi.domain.Usuario.dtos;

import com.lumahdev.reservasalasapi.domain.Usuario.Usuario;

public record DtoUsuario (
        Long id,
        String nome,
        String sobrenome,
        String email,
        String telefone,
        String login
) {
    public DtoUsuario (Usuario usuario) {
        this(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getLogin()
        );
    }
}