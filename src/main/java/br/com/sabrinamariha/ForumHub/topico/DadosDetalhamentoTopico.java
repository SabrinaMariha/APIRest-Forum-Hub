package br.com.sabrinamariha.ForumHub.topico;

import br.com.sabrinamariha.ForumHub.resposta.DadosDetalhamentoResposta;
import br.com.sabrinamariha.ForumHub.resposta.Resposta;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTopico (
        Long id,
        String titulo,
        String mensagem,
        String curso,
        String autor,
        String status,
        LocalDateTime dataCriacao,

        List<DadosDetalhamentoResposta> respostas
){


    public DadosDetalhamentoTopico(Topico topico, List<Resposta> respostas) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso(), topico.getUsuario().getNome(), topico.getStatus(), topico.getDataCriacao(), DadosDetalhamentoResposta.converter(respostas));

    }

    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso(), topico.getUsuario().getNome(), topico.getStatus(), topico.getDataCriacao(), null);
    }
}
