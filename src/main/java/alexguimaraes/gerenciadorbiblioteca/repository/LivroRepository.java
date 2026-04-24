package alexguimaraes.gerenciadorbiblioteca.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import alexguimaraes.gerenciadorbiblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("""
	    SELECT DISTINCT l
	    FROM Livro l
	    JOIN l.autores a
	    WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nomeAutor, '%'))
	    """)
    List<Livro> buscarPorNomeAutor(@Param("nomeAutor") String nomeAutor);

    @Query("""
	    SELECT l
	    FROM Livro l
	    WHERE NOT EXISTS (
		SELECT 1
		FROM Aluguel a
		JOIN a.livros al
		WHERE al.id = l.id
		  AND a.dataRetirada <= :agora
		  AND a.dataDevolucao >= :agora
	    )
	    """)
    List<Livro> buscarLivrosDisponiveisEm(@Param("agora") LocalDateTime agora);

    @Query("""
	    SELECT DISTINCT l
	    FROM Livro l
	    WHERE EXISTS (
		SELECT 1
		FROM Aluguel a
		JOIN a.livros al
		WHERE al.id = l.id
		  AND a.dataRetirada <= :agora
		  AND a.dataDevolucao >= :agora
	    )
	    """)
    List<Livro> buscarLivrosAlugadosEm(@Param("agora") LocalDateTime agora);
}

