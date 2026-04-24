package alexguimaraes.gerenciadorbiblioteca.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.repository.AluguelRepository;
import alexguimaraes.gerenciadorbiblioteca.repository.LivroRepository;

@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AluguelRepository aluguelRepository;

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado"));
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.buscarLivrosDisponiveisEm(LocalDateTime.now());
    }

    public List<Livro> listarLivrosAlugados() {
        return livroRepository.buscarLivrosAlugadosEm(LocalDateTime.now());
    }

    public List<Livro> listarLivrosPorAutorPesquisado(String nomeAutor) {
        return livroRepository.buscarPorNomeAutor(nomeAutor);
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

        if (aluguelRepository.existeAluguelParaLivro(id)) {
            throw new BusinessRuleException("Nao e permitido excluir livro que ja foi alugado");
        }

        livroRepository.delete(livro);
    }
}

