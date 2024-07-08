package br.com.sabrinamariha.ForumHub.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface TopicoRepository extends JpaRepository<Topico,Long> {
    @Query("""
            SELECT CASE WHEN COUNT(t) > 0 THEN FALSE ELSE TRUE END FROM Topico t 
            WHERE 
            t.titulo = :titulo 
            OR 
            t.mensagem = :mensagem
            """)
    boolean validacaoDuplicidadeTopico(@Param("titulo") String titulo, @Param("mensagem") String mensagem);
}

