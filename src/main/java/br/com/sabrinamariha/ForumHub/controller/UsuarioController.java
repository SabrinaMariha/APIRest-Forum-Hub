package br.com.sabrinamariha.ForumHub.controller;

import br.com.sabrinamariha.ForumHub.exceptions.DuplicidadeUsuario;
import br.com.sabrinamariha.ForumHub.exceptions.NaoAutorizadoUsuario;
import br.com.sabrinamariha.ForumHub.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
   @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados){
       if(!usuarioRepository.validacaoDuplicidadeUsuario(dados.email(),dados.nome()))
           throw new DuplicidadeUsuario("Já existem Usuários com esse nome ou email cadastrados.");

       var usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }
    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity atualizarUsuario(@PathVariable Long id, @RequestBody @Valid DadosAtualizarUsuario dados, Authentication authentication){

       var usuario = usuarioRepository.findById(id).get();
        if(!authentication.getName().equals(usuario.getEmail()))
            throw new NaoAutorizadoUsuario("Solicitação não autorizada. Só o usuário desse perfil pode alter-a-lo.");

        usuario.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity deletarUsuario(@PathVariable Long id, Authentication authentication){
        var usuario = usuarioRepository.findById(id).get();
        if(!authentication.getName().equals(usuario.getEmail()))
            throw new NaoAutorizadoUsuario("Solicitação não autorizada. Só o usuário desse perfil pode deletá-lo.");
        usuarioRepository.delete(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detalhes/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalharUsuario(@PathVariable Long id){
        var usuario = usuarioRepository.findById(id).get();
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }


}
