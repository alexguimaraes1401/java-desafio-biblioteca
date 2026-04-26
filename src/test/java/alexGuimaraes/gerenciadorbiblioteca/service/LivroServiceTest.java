package alexguimaraes.gerenciadorbiblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.repository.AluguelRepository;
import alexguimaraes.gerenciadorbiblioteca.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AluguelRepository aluguelRepository;

    @InjectMocks
    private LivroService livroService;

    @Test
    void deveBuscarLivroPorIdQuandoEncontrado() {
        Livro livro = new Livro();
        livro.setId(10L);

        when(livroRepository.findById(10L)).thenReturn(Optional.of(livro));

        Livro resultado = livroService.buscarPorId(10L);

        assertEquals(10L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> livroService.buscarPorId(99L));
    }

    @Test
    void deveAtualizarLivroComIdPersistido() {
        Livro existente = new Livro();
        existente.setId(1L);

        Livro livroAtualizado = new Livro();

        when(livroRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(livroRepository.save(any(Livro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Livro resultado = livroService.atualizar(livroAtualizado, 1L);

        assertEquals(1L, resultado.getId());
        verify(livroRepository).save(livroAtualizado);
    }

    @Test
    void deveLancarExcecaoAoExcluirLivroJaAlugado() {
        Livro livro = new Livro();
        livro.setId(5L);

        when(livroRepository.findById(5L)).thenReturn(Optional.of(livro));
        when(aluguelRepository.existeAluguelParaLivro(5L)).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> livroService.deleteLivro(5L));
    }

    @Test
    void deveExcluirLivroQuandoNuncaAlugado() {
        Livro livro = new Livro();
        livro.setId(6L);

        when(livroRepository.findById(6L)).thenReturn(Optional.of(livro));
        when(aluguelRepository.existeAluguelParaLivro(6L)).thenReturn(false);

        livroService.deleteLivro(6L);

        verify(livroRepository).delete(livro);
    }

    @Test
    void deveListarLivrosDisponiveis() {
        List<Livro> livros = List.of(new Livro());
        when(livroRepository.buscarLivrosDisponiveisEm(any())).thenReturn(livros);

        List<Livro> resultado = livroService.listarLivrosDisponiveis();

        assertEquals(1, resultado.size());
        verify(livroRepository).buscarLivrosDisponiveisEm(any());
    }
}
