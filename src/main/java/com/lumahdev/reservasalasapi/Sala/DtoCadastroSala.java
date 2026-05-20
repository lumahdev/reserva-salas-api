package com.lumahdev.reservasalasapi.Sala;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DtoCadastroSala (
        @NotBlank String nome,
        @NotNull @Positive @Min(1) int capacidade,
        @NotBlank String andar,
        @NotBlank String bloco
) {}
