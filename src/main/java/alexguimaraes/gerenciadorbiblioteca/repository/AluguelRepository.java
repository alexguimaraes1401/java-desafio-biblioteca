package alexguimaraes.gerenciadorbiblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import alexguimaraes.gerenciadorbiblioteca.model.Aluguel;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByDataDevolucaoIsNull();

    List<Aluguel> findByLocatarioId(Long locatarioId);}

