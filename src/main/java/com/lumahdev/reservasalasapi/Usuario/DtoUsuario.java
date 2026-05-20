package com.lumahdev.reservasalasapi.Usuario;

public record DtoUsuario (
        String nome,
        String sobrenome,
        String email,
        String telefone
) {
    public DtoUsuario (Usuario usuario ){
        this(
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getTelefone()
        );
    }
}