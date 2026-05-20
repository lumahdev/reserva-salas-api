package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Excecao;
import com.lumahdev.reservasalasapi.Sala.DtoSala;
import com.lumahdev.reservasalasapi.Sala.Sala;
import com.lumahdev.reservasalasapi.Sala.SalaRepository;
import com.lumahdev.reservasalasapi.Sala.SalaStatusEnum;
import com.lumahdev.reservasalasapi.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private boolean salaExiste(Long id){
        return salaRepository.existsById(id);
    }

    private boolean usuarioExiste(Long id){
        return usuarioRepository.existsById(id);
    }

    public Reserva cadastrarReserva(DtoCadastroReserva dto) {
        if (!salaExiste(dto.salaId())) {
            throw new Excecao("Não existe uma sala com este ID.");
        }
        if (!usuarioExiste(dto.usuarioId())) {
            throw new Excecao("Não existe um usuário com este ID.");
        }
        if (dto.dataFim().isBefore(dto.dataInicio())) {
            throw new Excecao("Data final não pode ser anterior à data inicial.");
        }
        boolean salaOcupada = reservaRepository
                .existsBySalaIdAndStatusAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(
                    dto.salaId(),
                    ReservaStatusEnum.ATIVA,
                    dto.dataFim(),
                    dto.dataInicio()
                );
        if (salaOcupada) {
            throw new Excecao("Já existe uma reserva para esta sala no período especificado.");
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
                .orElse(null);
    }

    public Reserva mudarDisponibilidadeReserva(Long id) {
        Reserva reserva = buscarReserva(id);
        if(reserva == null){
            throw new Excecao("Não existe uma reserva com este ID");
        }
        if(reserva.getStatus() == ReservaStatusEnum.ATIVA) {
            reserva.setStatus(ReservaStatusEnum.CANCELADA);
        } else {
            reserva.setStatus(ReservaStatusEnum.ATIVA);
        }
        return reservaRepository.save(reserva);
    }
}