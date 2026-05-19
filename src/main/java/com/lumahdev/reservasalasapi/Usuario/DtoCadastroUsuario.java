package com.lumahdev.reservasalasapi.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DtoCadastroUsuario(
                        @NotBlank String nome,
                        @NotBlank String sobrenome,
                        @NotBlank @Email String email,
                        @NotBlank @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")  String telefone) {}

