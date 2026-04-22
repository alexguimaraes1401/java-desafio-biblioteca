package alexguimaraes.gerenciadorbiblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import alexguimaraes.gerenciadorbiblioteca.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
