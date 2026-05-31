package com.lumahdev.reservasalasapi.infra.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lumahdev.reservasalasapi.domain.Excecao.DtoExcecao;
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
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class FilterAutenticacao extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    private void criaErro(String mensagem, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(new DtoExcecao(mensagem)));
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
                criaErro("O token informado tem um formato inválido.", response);
                return;
            } catch (TokenExpiredException e) {
                criaErro("O token informado está expirado.", response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}