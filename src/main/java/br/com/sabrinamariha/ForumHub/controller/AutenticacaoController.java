package br.com.sabrinamariha.ForumHub.controller;

import br.com.sabrinamariha.ForumHub.usuario.DadosAutenticacao;
import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import br.com.sabrinamariha.ForumHub.security.DadosToken;
import br.com.sabrinamariha.ForumHub.security.TokenService;
import br.com.sabrinamariha.ForumHub.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var autenticacaoToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        var autenticacao = manager.authenticate(autenticacaoToken);

        var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new DadosToken(tokenJWT));
    }
//    @PostMapping("/cadastro")
//    @Transactional
//    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosAutenticacao dados){
//        var usuario = new Usuario(2L,"Sabrina",dados.email(), dados.senha());
//        usuarioRepository.save(usuario);
//        return ResponseEntity.ok().build();
//    }

}