package com.lumahdev.reservasalasapi.Sala;

public record DtoSala (
        Long id,
        String nome,
        int capacidade,
        String andar,
        String bloco,
        SalaStatusEnum status
) {
    public DtoSala (Sala sala) {
        this(
                sala.getSalaId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getAndar(),
                sala.getBloco(),
                sala.getStatus()
        );
    }
}