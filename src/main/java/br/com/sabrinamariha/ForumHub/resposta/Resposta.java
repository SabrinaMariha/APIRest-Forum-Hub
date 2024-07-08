package br.com.sabrinamariha.ForumHub.resposta;


import br.com.sabrinamariha.ForumHub.topico.Topico;
import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Resposta{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private boolean ativo = true;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Resposta(DadosCadastroResposta dados, Usuario usuario, Topico topico) {
        this.mensagem = dados.mensagem();
        this.usuario = usuario;
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();

    }
}
