package com.lumahdev.reservasalasapi.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DtoCadastroUsuario (
        @NotBlank(message = "Nome é obrigatório.") String nome,
        @NotBlank(message = "Sobrenome é obrigatório.") String sobrenome,
        @NotBlank(message = "E-mail é obrigatório.") @Email(message = "E-mail inválido.") String email,
        @NotBlank(message = "Telefone é obrigatório.") @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}", message = "Telefone inválido.")  String telefone
) { }

