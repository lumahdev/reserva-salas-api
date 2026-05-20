package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    public void cadastrarReserva(DtoCadastroReserva dto) {
        boolean jaCadastrada = repository.existsBySalaIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(dto.salaId(), dto.dataFim(), dto.dataInicio());

        if(jaCadastrada) {
            throw new Excecao("Já existe uma reserva para esta sala no período especificado.");
        }

        repository.save(new Reserva(dto));
    }
}