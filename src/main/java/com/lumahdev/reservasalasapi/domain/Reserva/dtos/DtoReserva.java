package com.lumahdev.reservasalasapi.domain.Reserva.dtos;

import com.lumahdev.reservasalasapi.domain.Reserva.Reserva;
import com.lumahdev.reservasalasapi.domain.Reserva.ReservaStatusEnum;

import java.time.LocalDate;

public record DtoReserva (
        Long id,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long salaId,
        Long usuarioId,
        ReservaStatusEnum status
) {
    public DtoReserva (Reserva reserva) {
        this(
                reserva.getReservaId(),
                reserva.getDataInicio(),
                reserva.getDataFim(),
                reserva.getSalaId(),
                reserva.getUsuarioId(),
                reserva.getStatus()
        );
    }
}