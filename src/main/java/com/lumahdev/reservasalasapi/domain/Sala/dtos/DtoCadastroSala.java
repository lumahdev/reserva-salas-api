package com.lumahdev.reservasalasapi.domain.Sala.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DtoCadastroSala (
        @NotBlank(message = "Nome é obrigatório.") String nome,
        @NotNull(message = "Capacidade é obrigatória.") @Positive(message = "Capacidade deve ser um número positivo.") @Min(value = 1, message = "Capacidade deve ser maior que 1.") Integer capacidade,
        @NotBlank(message = "Andar é obrigatório.") String andar,
        @NotBlank(message = "Bloco é obrigatório.") String bloco
) {}
