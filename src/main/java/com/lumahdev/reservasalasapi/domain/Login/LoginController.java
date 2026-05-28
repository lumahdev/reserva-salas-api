package com.lumahdev.reservasalasapi.domain.Login;

import com.lumahdev.reservasalasapi.domain.Usuario.Usuario;
import com.lumahdev.reservasalasapi.infra.security.Token.DtoToken;
import com.lumahdev.reservasalasapi.infra.security.Token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DtoToken> efetuarLogin(@RequestBody @Valid DtoLogin dto, UriComponentsBuilder uriBuilder) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var authentication = manager.authenticate(authenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String tokenJwt = tokenService.gerarToken(usuario);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(tokenJwt).toUri();
        return ResponseEntity.created(uri).body(new DtoToken(tokenJwt));
    }
}
