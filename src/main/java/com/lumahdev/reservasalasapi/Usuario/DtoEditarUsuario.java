package com.lumahdev.reservasalasapi.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DtoEditarUsuario (
        @Email String email,
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")  String telefone
) { }
