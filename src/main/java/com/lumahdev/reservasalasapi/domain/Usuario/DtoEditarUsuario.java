package com.lumahdev.reservasalasapi.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DtoEditarUsuario (
        @Email(message = "E-mail inválido.") String email,
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}", message = "Telefone inválido.")  String telefone
) { }
