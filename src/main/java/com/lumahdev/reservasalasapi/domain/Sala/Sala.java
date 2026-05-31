package com.lumahdev.reservasalasapi.domain.Sala;

import com.lumahdev.reservasalasapi.domain.Sala.dtos.DtoCadastroSala;
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

    public Long getSalaId() {
        return salaId;
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

    public void setStatus(SalaStatusEnum status) {
        this.status = status;
    }
}