package br.com.sabrinamariha.ForumHub.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
