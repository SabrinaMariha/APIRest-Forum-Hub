package br.com.sabrinamariha.ForumHub.controller;

import br.com.sabrinamariha.ForumHub.exceptions.DuplicidadeTopico;
import br.com.sabrinamariha.ForumHub.exceptions.NaoAutorizadoTopico;
import br.com.sabrinamariha.ForumHub.resposta.Resposta;
import br.com.sabrinamariha.ForumHub.resposta.RespostaRepository;
import br.com.sabrinamariha.ForumHub.topico.*;
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
    @Autowired
    private RespostaRepository respostaRepository;
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> registrarTopico(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder, Authentication authentication) {

        Usuario usuario= usuarioRepository.findByEmail(authentication.getName());


        if(!topicoRepository.validacaoDuplicidadeTopico(dados.titulo(),dados.mensagem()))
            throw new DuplicidadeTopico("Já existem tópicos com esses dados. Veja se ele atende a sua necessidade ou altere o conteúdo.");


        var topico = new Topico(dados,usuario);

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


        if(!usuario.verificarAutorizacao(topico))
            throw new NaoAutorizadoTopico("Solicitação não autorizada. Só o criador do tópico pode alterá-lo.");


        if(!topicoRepository.validacaoDuplicidadeTopico(dados.titulo(),dados.mensagem()))
            throw new DuplicidadeTopico("Já existem tópicos com esses dados. Veja se ele atende a sua necessidade ou altere o conteúdo.");


        topico.atualizarTopico(dados, usuario);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarTopico(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName());
        var topico = topicoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado."));

        if(!usuario.verificarAutorizacao(topico))
            throw new NaoAutorizadoTopico("Solicitação não autorizada. Só o criador do tópico pode deletá-lo.");

        //deletar tópico definitivamente
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long id){
        var topico = topicoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado."));

        List<Resposta> respostas = respostaRepository.findByTopicoId(id);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico, respostas));


    }

    @PutMapping("/{id}/alterarStatus")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> alterarStatus(@PathVariable Long id, Authentication authentication){
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName());
        var topico = topicoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado."));

        if(!usuario.verificarAutorizacao(topico))
            throw new NaoAutorizadoTopico("Solicitação não autorizada. Só o criador do tópico pode alterar o status.");

        topico.alterarStatus();
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

}
