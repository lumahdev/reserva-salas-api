package com.lumahdev.reservasalasapi.Reserva;

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

    @Override
    public String toString() {
        return "Reserva{" +
                "dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", sala=" + salaId +
                ", usuario=" + usuarioId +
                '}';
    }
}