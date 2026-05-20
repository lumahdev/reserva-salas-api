package com.lumahdev.reservasalasapi.Reserva;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DtoCadastroReserva (
        @NotNull @FutureOrPresent LocalDate dataInicio,
        @NotNull @Future LocalDate dataFim,
        @NotNull Long salaId,
        @NotNull Long usuarioId
) { }
