package br.com.sabrinamariha.ForumHub.resposta;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DadosCadastroResposta(
        Long id,
        @NotBlank
        String mensagem,
        Long idTopico,
        Long idUsuario,
        Boolean ativo,
        LocalDateTime dataCriacao

) {
}
