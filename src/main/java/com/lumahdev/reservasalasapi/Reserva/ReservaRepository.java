package com.lumahdev.reservasalasapi.Reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsBySalaIdAndStatusAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(Long salaId, ReservaStatusEnum status, LocalDate dataFim, LocalDate dataInicio);

    void deleteAllByUsuarioId(Long id);

    void deleteAllBySalaId(Long id);

    Page<Reserva> findAllByUsuarioId(Long id, Pageable paginacao);

    Page<Reserva> findAllBySalaId(Long id, Pageable paginacao);
}
