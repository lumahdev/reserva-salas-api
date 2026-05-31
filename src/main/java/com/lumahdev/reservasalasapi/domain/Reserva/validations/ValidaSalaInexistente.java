package com.lumahdev.reservasalasapi.domain.Reserva.validations;

import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import com.lumahdev.reservasalasapi.domain.Reserva.dtos.DtoCadastroReserva;
import com.lumahdev.reservasalasapi.domain.Sala.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidaSalaInexistente implements ValidadorInterface {

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public void validar(DtoCadastroReserva dto) {
        if (!salaRepository.existsById(dto.salaId())) {
            throw new Excecao("Não existe uma sala com este ID.", HttpStatus.NOT_FOUND);
        }
    }
}
