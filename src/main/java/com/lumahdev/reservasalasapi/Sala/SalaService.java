package com.lumahdev.reservasalasapi.Sala;

import com.lumahdev.reservasalasapi.Excecao;
import com.lumahdev.reservasalasapi.Usuario.DtoUsuario;
import com.lumahdev.reservasalasapi.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    @Autowired
    private SalaRepository repository;

    private boolean checarSalaUnica(String nome) {
        return repository.existsByNome(nome);
    }

    public Sala cadastrarSala(DtoCadastroSala dto) {
        if(checarSalaUnica(dto.nome())) {
            throw new Excecao("Já existe uma sala cadastrada com estes dados.");
        }
        return repository.save(new Sala(dto));
    }

    public List<DtoSala> buscarSalas() {
        return repository
                .findAll()
                .stream()
                .map(DtoSala::new)
                .toList();
    }

    public Sala buscarSala(Long id) {
        return repository
                .findById(id)
                .orElse(null);
    }
}
