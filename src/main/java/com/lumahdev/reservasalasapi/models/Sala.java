package com.lumahdev.reservasalasapi.models;

import jakarta.persistence.*;

@Entity
@Table
public class Sala {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long salaId;
    private String nome;
    private int capacidade;
    private String andar;
    private String bloco;
    private StatusSala status;

    public Sala(String nome, int capacidade, String andar, String bloco) {
        if (capacidade < 1) {
            throw new IllegalArgumentException("A capacidade deve ser maior que 0.");
        }

        this.nome = nome;
        this.capacidade = capacidade;
        this.andar = andar;
        this.bloco = bloco;
        this.status = StatusSala.DISPONIVEL;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getAndar() {
        return andar;
    }

    public String getBloco() {
        return bloco;
    }

    public StatusSala getStatus() {
        return status;
    }

    public void setStatusIndisponivel() {
        this.status = StatusSala.INDISPONIVEL;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "nome='" + nome + '\'' +
                ", capacidade=" + capacidade +
                ", andar='" + andar + '\'' +
                ", bloco='" + bloco + '\'' +
                ", ativa=" + status +
                '}';
    }
}