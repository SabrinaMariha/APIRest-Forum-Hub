package br.com.sabrinamariha.ForumHub.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(EntityNotFoundException ex){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }
    @ExceptionHandler(DuplicidadeTopico.class)
    public ResponseEntity tratarErro400(DuplicidadeTopico ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(NaoAutorizadoTopico.class)
    public ResponseEntity tratarErro401(NaoAutorizadoTopico ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }
    @ExceptionHandler(DuplicidadeUsuario.class)
    public ResponseEntity tratarErro400(DuplicidadeUsuario ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(NaoAutorizadoUsuario.class)
    public ResponseEntity tratarErro401(NaoAutorizadoUsuario ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }
    @ExceptionHandler(NaoAutorizadoResposta.class)
    public ResponseEntity tratarErro401(NaoAutorizadoResposta ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }



    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }


}
