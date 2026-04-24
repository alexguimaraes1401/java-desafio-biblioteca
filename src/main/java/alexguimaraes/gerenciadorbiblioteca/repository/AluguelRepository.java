package alexguimaraes.gerenciadorbiblioteca.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import alexguimaraes.gerenciadorbiblioteca.model.Aluguel;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

        @Query("""
                        SELECT a
                        FROM Aluguel a
                        WHERE a.dataRetirada <= :agora
                            AND a.dataDevolucao >= :agora
                        """)
        List<Aluguel> findAtivosEm(@Param("agora") LocalDateTime agora);

        List<Aluguel> findByLocatarioId(Long locatarioId);

                @Query("""
                                                SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
                                                FROM Aluguel a
                                                JOIN a.livros l
                                                WHERE l.id = :livroId
                                                """)
                boolean existeAluguelParaLivro(@Param("livroId") Long livroId);

                @Query("""
                                                SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
                                                FROM Aluguel a
                                                WHERE a.locatario.id = :locatarioId
                                                    AND a.dataRetirada <= :agora
                                                    AND a.dataDevolucao >= :agora
                                                """)
                boolean existePendenciaAtivaParaLocatario(
                                                @Param("locatarioId") Long locatarioId,
                                                @Param("agora") LocalDateTime agora
                );
}

