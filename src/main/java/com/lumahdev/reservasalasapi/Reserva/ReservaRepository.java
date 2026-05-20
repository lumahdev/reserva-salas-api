package com.lumahdev.reservasalasapi.Reserva;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsBySalaIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(Long salaId, LocalDate dataFim, LocalDate dataInicio);
}
