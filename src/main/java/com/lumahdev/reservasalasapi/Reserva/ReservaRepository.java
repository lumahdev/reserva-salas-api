package com.lumahdev.reservasalasapi.Reserva;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsBySalaIdAndStatusAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(Long salaId, ReservaStatusEnum status, LocalDate dataFim, LocalDate dataInicio);
}
