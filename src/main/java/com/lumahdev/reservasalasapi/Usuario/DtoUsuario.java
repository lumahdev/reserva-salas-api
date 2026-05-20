package com.lumahdev.reservasalasapi.Usuario;

public record DtoUsuario (
        Long id,
        String nome,
        String sobrenome,
        String email,
        String telefone
) {
    public DtoUsuario (Usuario usuario) {
        this(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getTelefone()
        );
    }
}