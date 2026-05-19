package com.lumahdev.reservasalasapi.Sala;

import jakarta.persistence.*;

@Entity
@Table
public class SalaModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long salaId;
    private String nome;
    private int capacidade;
    private String andar;
    private String bloco;
    private SalaStatusEnum status;

    public SalaModel() {
    }

    public SalaModel(String nome, int capacidade, String andar, String bloco) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.andar = andar;
        this.bloco = bloco;
        this.status = SalaStatusEnum.DISPONIVEL;
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

    public SalaStatusEnum getStatus() {
        return status;
    }

    public void setStatusIndisponivel() {
        this.status = SalaStatusEnum.INDISPONIVEL;
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