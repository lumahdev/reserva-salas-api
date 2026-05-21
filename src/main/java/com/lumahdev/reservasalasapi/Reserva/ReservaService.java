package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Excecao.Excecao;
import com.lumahdev.reservasalasapi.Sala.SalaRepository;
import com.lumahdev.reservasalasapi.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<DtoReserva> buscarReservas() {
        return reservaRepository
                .findAll()
                .stream()
                .map(DtoReserva::new)
                .toList();
    }

    public Reserva buscarReserva(Long id) {
        return reservaRepository
                .findById(id)
                .orElseThrow(() -> new Excecao("Não existe uma reserva com este ID.", HttpStatus.NOT_FOUND));
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
}