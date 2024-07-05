package br.com.sabrinamariha.ForumHub.topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String status,
        String autor,
        String curso
) {
    public DadosListagemTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getUsuario().getNome(), topico.getCurso());
    }
}
