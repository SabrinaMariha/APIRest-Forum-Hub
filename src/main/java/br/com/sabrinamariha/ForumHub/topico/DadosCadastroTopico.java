package br.com.sabrinamariha.ForumHub.topico;

import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroTopico(

        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,

        @NotBlank
        String curso,
        Usuario usuario,
        String status,
        boolean ativo,
        LocalDateTime dataCriacao
) {
}
