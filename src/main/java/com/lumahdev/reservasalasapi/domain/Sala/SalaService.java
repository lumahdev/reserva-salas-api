package com.lumahdev.reservasalasapi.domain.Sala;

import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import com.lumahdev.reservasalasapi.domain.Reserva.ReservaRepository;
import com.lumahdev.reservasalasapi.domain.Sala.dtos.DtoCadastroSala;
import com.lumahdev.reservasalasapi.domain.Sala.dtos.DtoSala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Sala cadastrarSala(DtoCadastroSala dto) {
        if(repository.existsByNome(dto.nome())) {
            throw new Excecao("Já existe uma sala cadastrada com estes dados.", HttpStatus.BAD_REQUEST);
        }
        return repository.save(new Sala(dto));
    }

    public Page<DtoSala> buscarSalas(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(DtoSala::new);
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

    public void deletarSala(Long id) {
        Sala sala = buscarSala(id);
        if(sala != null) {
            repository.deleteById(id);
            reservaRepository.deleteAllBySalaId(id);
        }
    }
}
