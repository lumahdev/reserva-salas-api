package com.lumahdev.reservasalasapi.domain.Usuario;

import jakarta.persistence.*;

@Entity
@Table
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long usuarioId;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String login;
    private String senha;

    public Usuario() { }

    public Usuario(DtoCadastroUsuario dto, String senha) {
        this.nome = dto.nome();
        this.sobrenome = dto.sobrenome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.login = dto.login();
        this.senha = senha;
    }

    public Usuario(DtoCadastroUsuario dto) {
        this.nome = dto.nome();
        this.sobrenome = dto.sobrenome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.login = dto.login();
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

    public String getLogin() {
        return login;
    }
}