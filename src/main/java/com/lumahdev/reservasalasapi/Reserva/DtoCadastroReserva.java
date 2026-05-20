package com.lumahdev.reservasalasapi.Reserva;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DtoCadastroReserva (
        @NotNull(message = "Data de início é obrigatória.") @FutureOrPresent(message = "Data de início não pode estar no passado.") LocalDate dataInicio,
        @NotNull(message = "Data de fim é obrigatória.") @Future(message = "Data de fim não pode estar no passado.") LocalDate dataFim,
        @NotNull(message = "Identificação da sala é obrigatória.") Long salaId,
        @NotNull(message = "Identificação do usuário é obrigatória.") Long usuarioId
) { }
