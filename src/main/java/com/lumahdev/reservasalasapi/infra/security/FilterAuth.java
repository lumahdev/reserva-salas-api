package com.lumahdev.reservasalasapi.infra.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lumahdev.reservasalasapi.domain.Usuario.Usuario;
import com.lumahdev.reservasalasapi.domain.Usuario.UsuarioRepository;
import com.lumahdev.reservasalasapi.infra.security.Token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();
        return path.equals("/auth/login") || (path.equals("/usuarios") && request.getMethod().equals("POST"));
    }

    private String recuperarToken(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        if (token != null) {
            try {
                String subject = tokenService.getSubject(token);
                if (subject != null) {
                    Usuario usuario = (Usuario) usuarioRepository.findByLogin(subject);
                    if (usuario != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JWTDecodeException e) {
                new HandleAuthExceptions("O token informado tem um formato inválido.", response);
                return;
            } catch (TokenExpiredException e) {
                new HandleAuthExceptions("O token informado está expirado.", response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}