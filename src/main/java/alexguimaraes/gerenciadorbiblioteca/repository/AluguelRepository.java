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
}

