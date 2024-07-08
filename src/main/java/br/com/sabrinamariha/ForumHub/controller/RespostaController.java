package br.com.sabrinamariha.ForumHub.controller;

import br.com.sabrinamariha.ForumHub.exceptions.NaoAutorizadoResposta;
import br.com.sabrinamariha.ForumHub.resposta.DadosCadastroResposta;
import br.com.sabrinamariha.ForumHub.resposta.DadosDetalhamentoResposta;
import br.com.sabrinamariha.ForumHub.resposta.Resposta;
import br.com.sabrinamariha.ForumHub.resposta.RespostaRepository;
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

@RestController
@RequestMapping("/{idTopico}/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoResposta> cadastrarResposta(@PathVariable Long idTopico, @RequestBody @Valid DadosCadastroResposta dados, Authentication authentication){
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName());
        Topico topico = topicoRepository.getReferenceById(idTopico);

        Resposta resposta = new Resposta(dados, usuario,topico);

        respostaRepository.save(resposta);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @DeleteMapping("/{idResposta}")
    @Transactional
    public ResponseEntity deletarResposta(@PathVariable Long idTopico, @PathVariable Long idResposta, Authentication authentication){
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName());
        Resposta resposta = respostaRepository.getReferenceById(idResposta);

        if(!resposta.getUsuario().getId().equals(usuario.getId())){
            throw new NaoAutorizadoResposta("Somente o autor da resposta pode deletá-la.");
        }
        respostaRepository.delete(resposta);
        return ResponseEntity.noContent().build();
    }
}
