package br.com.sabrinamariha.ForumHub.topico;

import br.com.sabrinamariha.ForumHub.usuario.DadosAutenticacao;
import br.com.sabrinamariha.ForumHub.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="Topico")
@Table(name="topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Topico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    private String curso;
    private String status = "NAO_RESPONDIDO";
    private boolean ativo =true;
    @Column(name="data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Topico(DadosCadastroTopico dados, Usuario usuario) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.usuario = usuario;
        this.curso = dados.curso();
        this.ativo = true;
    }

    public void atualizarTopico(DadosAtualizarTopico dados, Usuario usuario) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
        this.dataCriacao = LocalDateTime.now();

    }

    public void alterarStatus() {
        this.status="RESPONDIDO";
    }
}

