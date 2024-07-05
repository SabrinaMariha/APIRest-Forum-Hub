package br.com.sabrinamariha.ForumHub.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico (
        Long id,
        String titulo,
        String mensagem,
        String curso,
        String autor,
        String status,
        LocalDateTime dataCriacao
){
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso(), topico.getUsuario().getNome(), topico.getStatus(), topico.getDataCriacao());

    }
}
