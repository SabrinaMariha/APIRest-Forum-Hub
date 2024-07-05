package br.com.sabrinamariha.ForumHub.security;


import br.com.sabrinamariha.ForumHub.usuario.UsuarioRepository;
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
public class SecurityFilter extends OncePerRequestFilter {//valida uma vez por requisição
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT!=null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByEmail(subject);
            var autenticacao= new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }
        filterChain.doFilter(request, response);//chama o próximo filtro
    }

    private String recuperarToken(HttpServletRequest request) {
        var autorizacaoHeader = request.getHeader("Authorization");
        if (autorizacaoHeader!=null) {
            return autorizacaoHeader.replace("Bearer ", "");
        }
        return null;
    }
}
