package com.lumahdev.reservasalasapi.domain.Reserva.validations;

import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import com.lumahdev.reservasalasapi.domain.Reserva.dtos.DtoCadastroReserva;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidaDatas implements ValidadorInterface {

    @Override
    public void validar(DtoCadastroReserva dto) {
        if (dto.dataFim().isBefore(dto.dataInicio())) {
            throw new Excecao("Data final não pode ser anterior à data inicial.", HttpStatus.BAD_REQUEST);
        }
    }
}
