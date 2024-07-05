package br.com.sabrinamariha.ForumHub.controller;

import br.com.sabrinamariha.ForumHub.topico.DadosCadastroTopico;
import br.com.sabrinamariha.ForumHub.topico.DadosDetalhamentoTopico;
import br.com.sabrinamariha.ForumHub.topico.Topico;
import br.com.sabrinamariha.ForumHub.topico.TopicoRepository;
import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import br.com.sabrinamariha.ForumHub.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> registrarTopico(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder, Authentication authentication) {

        Usuario usuario= usuarioRepository.findByEmail(authentication.getName());
        var topico = new Topico(dados, usuario);

        topicoRepository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        var dto = new DadosDetalhamentoTopico(topico);
        return ResponseEntity.created(uri).body(dto);
    }
}
