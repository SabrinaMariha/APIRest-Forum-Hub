package br.com.sabrinamariha.ForumHub.resposta;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoResposta(
        Long id,
        LocalDateTime dataCriacaoResposta,
        String tituloTopico,
        String autor,
        String mensagem

) {

    public DadosDetalhamentoResposta(Resposta resposta) {
        this(resposta.getId(),
                resposta.getDataCriacao(),
                resposta.getTopico().getTitulo(),
                resposta.getUsuario().getNome(),
                resposta.getMensagem());
    }

    public static List<DadosDetalhamentoResposta> converter(List<Resposta> respostas) {
        return respostas.stream().map(DadosDetalhamentoResposta::new).toList();
    }
}
