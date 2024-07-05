package br.com.sabrinamariha.ForumHub.controller;

import br.com.sabrinamariha.ForumHub.topico.*;
import br.com.sabrinamariha.ForumHub.usuario.DadosAutenticacao;
import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import br.com.sabrinamariha.ForumHub.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listarTopicos(@PageableDefault(size=10, sort={"dataCriacao"}) Pageable paginacao) {
        var page = topicoRepository.findAll(paginacao)
                .map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizarTopico(@PathVariable Long id, @RequestBody @Valid DadosAtualizarTopico dados, Authentication authentication){
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName());
        var topico = topicoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado."));
        var autorizado=usuario.verificarAutorizacao(topico);
        if(!autorizado){
            throw new IllegalArgumentException("Solicitação não autorizada. Só o criador do tópico pode alterá-lo.");
        }
        topico.atualizarTopico(dados, usuario);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }
}
