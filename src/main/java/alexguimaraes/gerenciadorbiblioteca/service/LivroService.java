package alexguimaraes.gerenciadorbiblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.repository.LivroRepository;

@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro atualizar(Livro livro, long id) {
        Livro livroExistente = livroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado"));
        livro.setId(livroExistente.getId());
        return livroRepository.save(livro);
    }

    public void deleteLivro(Long id) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado"));
        livroRepository.delete(livro);
    }
}

