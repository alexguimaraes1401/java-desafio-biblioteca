package alexguimaraes.gerenciadorbiblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.model.Autor;
import alexguimaraes.gerenciadorbiblioteca.repository.AutorRepository;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Autor autor, long id) {
        Autor autorExistente = autorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Autor nao encontrado"));
        autor.setId(autorExistente.getId());
        return autorRepository.save(autor);
    }
    
    public void deleteAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor nao encontrado"));
        
        if (!autor.getLivros().isEmpty()) {
            throw new BusinessRuleException("Nao e permitido excluir um autor que possui livros cadastrados");
        }
        
        autorRepository.delete(autor);
    }

    public List<Autor> buscarPorNome(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }
    
}

