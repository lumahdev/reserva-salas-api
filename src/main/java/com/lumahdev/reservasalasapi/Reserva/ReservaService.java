package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Excecao.Excecao;
import com.lumahdev.reservasalasapi.Sala.Sala;
import com.lumahdev.reservasalasapi.Sala.SalaRepository;
import com.lumahdev.reservasalasapi.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Reserva cadastrarReserva(DtoCadastroReserva dto) {
        if (!salaRepository.existsById(dto.salaId())) {
            throw new Excecao("Não existe uma sala com este ID.", HttpStatus.NOT_FOUND);
        }
        if (!usuarioRepository.existsById(dto.usuarioId())) {
            throw new Excecao("Não existe um usuário com este ID.", HttpStatus.NOT_FOUND);
        }
        if (dto.dataFim().isBefore(dto.dataInicio())) {
            throw new Excecao("Data final não pode ser anterior à data inicial.", HttpStatus.BAD_REQUEST);
        }
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
        Reserva reserva = new Reserva(dto);
        return reservaRepository.save(reserva);
    }

    public Page<DtoReserva> buscarReservas(Pageable paginacao) {
        return reservaRepository
                .findAll(paginacao)
                .map(DtoReserva::new);
    }

    public Reserva buscarReserva(Long id) {
        return reservaRepository
                .findById(id)
                .orElseThrow(() -> new Excecao("Não existe uma reserva com este ID.", HttpStatus.NOT_FOUND));
    }

    public Page<DtoReserva> buscarReservaPorUsuarioId(Long id, Pageable paginacao) {
        Page<DtoReserva> reservas =  reservaRepository
                .findAllByUsuarioId(id, paginacao)
                .map(DtoReserva::new);
        if(reservas.isEmpty()) {
            throw new Excecao("Não existe uma reserva com este ID de usuário.", HttpStatus.NOT_FOUND);
        }
        return reservas;
    }

    public Page<DtoReserva> buscarReservaPorSalaId(Long id, Pageable paginacao) {
        Page<DtoReserva> reservas =  reservaRepository
                .findAllBySalaId(id, paginacao)
                .map(DtoReserva::new);
        if(reservas.isEmpty()) {
            throw new Excecao("Não existe uma reserva com este ID de sala.", HttpStatus.NOT_FOUND);
        }
        return reservas;
    }

    public Reserva mudarDisponibilidadeReserva(Long id) {
        Reserva reserva = buscarReserva(id);
        if(reserva.getStatus() == ReservaStatusEnum.ATIVA) {
            reserva.setStatus(ReservaStatusEnum.CANCELADA);
        } else {
            reserva.setStatus(ReservaStatusEnum.ATIVA);
        }
        return reservaRepository.save(reserva);
    }

    public void deletarReserva(Long id) {
        Reserva reserva = buscarReserva(id);
        if(reserva != null) {
            reservaRepository.deleteById(id);
        }
    }
}