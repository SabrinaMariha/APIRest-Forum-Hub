package br.com.sabrinamariha.ForumHub.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN FALSE ELSE TRUE END FROM Usuario u 
            WHERE 
            u.email = :email
            OR 
            u.nome = :nome
            """)
    boolean validacaoDuplicidadeUsuario(@Param("email") String email, @Param("nome") String nome);

}
