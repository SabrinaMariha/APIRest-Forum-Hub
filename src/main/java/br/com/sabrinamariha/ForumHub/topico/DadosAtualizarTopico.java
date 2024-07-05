package br.com.sabrinamariha.ForumHub.topico;

import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DadosAtualizarTopico(
        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,

        @NotBlank
        String curso
) {

}
