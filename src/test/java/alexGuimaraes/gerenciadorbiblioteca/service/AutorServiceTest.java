package alexguimaraes.gerenciadorbiblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.model.Autor;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @Test
    void deveAtualizarAutorComIdPersistido() {
        Autor existente = new Autor();
        existente.setId(2L);

        Autor atualizado = new Autor();

        when(autorRepository.findById(2L)).thenReturn(Optional.of(existente));
        when(autorRepository.save(any(Autor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Autor resultado = autorService.atualizar(atualizado, 2L);

        assertEquals(2L, resultado.getId());
        verify(autorRepository).save(atualizado);
    }

    @Test
    void deveLancarExcecaoAoExcluirAutorComLivros() {
        Autor autor = new Autor();
        Livro livro = new Livro();
        livro.setId(1L);
        autor.setLivros(Set.of(livro));

        when(autorRepository.findById(7L)).thenReturn(Optional.of(autor));

        assertThrows(BusinessRuleException.class, () -> autorService.deleteAutor(7L));
    }

    @Test
    void deveExcluirAutorSemLivros() {
        Autor autor = new Autor();
        autor.setLivros(Set.of());

        when(autorRepository.findById(8L)).thenReturn(Optional.of(autor));

        autorService.deleteAutor(8L);

        verify(autorRepository).delete(autor);
    }

    @Test
    void deveBuscarAutoresPorNome() {
        List<Autor> autores = List.of(new Autor());
        when(autorRepository.findByNomeContainingIgnoreCase("Machado")).thenReturn(autores);

        List<Autor> resultado = autorService.buscarPorNome("Machado");

        assertEquals(1, resultado.size());
    }
}
