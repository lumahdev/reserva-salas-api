package com.lumahdev.reservasalasapi.Usuario;

import jakarta.persistence.*;

@Entity
@Table
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long usuarioId;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;

    public Usuario() {
    }

    public Usuario(DtoCadastroUsuario dto) {
        this.nome = dto.nome();
        this.sobrenome = dto.sobrenome();
        this.email = dto.email();
        this.telefone = dto.telefone();
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}