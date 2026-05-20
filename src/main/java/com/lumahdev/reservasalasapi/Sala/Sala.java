package com.lumahdev.reservasalasapi.Sala;

import jakarta.persistence.*;

@Entity
@Table
public class Sala {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long salaId;
    private String nome;
    private int capacidade;
    private String andar;
    private String bloco;
    @Enumerated(EnumType.STRING) private SalaStatusEnum status;

    public Sala() {
    }

    public Sala(DtoCadastroSala dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
        this.andar = dto.andar();
        this.bloco = dto.bloco();
        this.status = SalaStatusEnum.DISPONIVEL;
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