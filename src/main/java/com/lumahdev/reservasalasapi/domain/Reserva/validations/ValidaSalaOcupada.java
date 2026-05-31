package com.lumahdev.reservasalasapi.domain.Reserva.validations;

import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import com.lumahdev.reservasalasapi.domain.Reserva.ReservaRepository;
import com.lumahdev.reservasalasapi.domain.Reserva.ReservaStatusEnum;
import com.lumahdev.reservasalasapi.domain.Reserva.dtos.DtoCadastroReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidaSalaOcupada implements ValidadorInterface {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void validar(DtoCadastroReserva dto) {
        boolean salaOcupada = reservaRepository
                .existsBySalaIdAndStatusAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(
                        dto.salaId(),
                        ReservaStatusEnum.ATIVA,
                        dto.dataFim(),
                        dto.dataInicio()
                );
        if (salaOcupada) {
            throw new Excecao("Já existe uma reserva para esta sala no período especificado.", HttpStatus.BAD_REQUEST);
        }
    }
}
