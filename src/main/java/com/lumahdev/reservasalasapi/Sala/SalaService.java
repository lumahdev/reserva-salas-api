package com.lumahdev.reservasalasapi.Sala;

import com.lumahdev.reservasalasapi.Excecao.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    @Autowired
    private SalaRepository repository;

    public Sala cadastrarSala(DtoCadastroSala dto) {
        if(repository.existsByNome(dto.nome())) {
            throw new Excecao("Já existe uma sala cadastrada com estes dados.", HttpStatus.BAD_REQUEST);
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
                .orElseThrow(() -> new Excecao("Não existe uma sala com este ID.", HttpStatus.NOT_FOUND));
    }

    public Sala mudarDisponibilidadeSala(Long id) {
        Sala sala = buscarSala(id);
        if(sala.getStatus() == SalaStatusEnum.DISPONIVEL) {
            sala.setStatus(SalaStatusEnum.INDISPONIVEL);
        } else {
            sala.setStatus(SalaStatusEnum.DISPONIVEL);
        }
        return repository.save(sala);
    }
}
