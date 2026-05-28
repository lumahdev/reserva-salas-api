package com.lumahdev.reservasalasapi.domain.Reserva;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Reserva {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long reservaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long salaId;
    private Long usuarioId;
    @Enumerated(EnumType.STRING) private ReservaStatusEnum status;

    public Reserva() {
    }

    public Reserva(DtoCadastroReserva dto) {
        this.dataInicio = dto.dataInicio();
        this.dataFim = dto.dataFim();
        this.salaId = dto.salaId();
        this.usuarioId = dto.usuarioId();
        this.status = ReservaStatusEnum.ATIVA;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Long getSalaId() {
        return salaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public ReservaStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReservaStatusEnum status) {
        this.status = status;
    }
}