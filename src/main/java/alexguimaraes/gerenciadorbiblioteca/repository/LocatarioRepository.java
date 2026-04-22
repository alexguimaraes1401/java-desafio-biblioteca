package alexguimaraes.gerenciadorbiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alexguimaraes.gerenciadorbiblioteca.model.Locatario;

public interface LocatarioRepository extends JpaRepository<Locatario, Long> {
    
    
}

