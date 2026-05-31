package com.lumahdev.reservasalasapi.domain.Reserva.validations;

import com.lumahdev.reservasalasapi.domain.Excecao.Excecao;
import com.lumahdev.reservasalasapi.domain.Reserva.dtos.DtoCadastroReserva;
import com.lumahdev.reservasalasapi.domain.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidaUsuarioInexistente implements ValidadorInterface {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DtoCadastroReserva dto) {
        if (!usuarioRepository.existsById(dto.usuarioId())) {
            throw new Excecao("Não existe um usuário com este ID.", HttpStatus.NOT_FOUND);
        }
    }
}
