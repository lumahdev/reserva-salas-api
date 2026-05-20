package com.lumahdev.reservasalasapi.Sala;

import com.lumahdev.reservasalasapi.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaService {
    @Autowired
    private SalaRepository repository;

    public void cadastrarSala(DtoCadastroSala dto) {
        boolean jaCadastrada = repository.existsByNome(dto.nome());

        if(jaCadastrada) {
            throw new Excecao("Já existe uma sala cadastrada com estes dados.");
        }

        repository.save(new Sala(dto));
    }
}
