package com.lumahdev.reservasalasapi.Sala;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNome(String nome);
}
