package com.lumahdev.reservasalasapi.domain.Reserva.validations;

import com.lumahdev.reservasalasapi.domain.Reserva.dtos.DtoCadastroReserva;

public interface ValidadorInterface {

    default void validar(DtoCadastroReserva dto) {}
}
