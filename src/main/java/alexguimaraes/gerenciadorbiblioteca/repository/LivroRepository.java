package alexguimaraes.gerenciadorbiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alexguimaraes.gerenciadorbiblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {}

