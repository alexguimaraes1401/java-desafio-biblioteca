package alexguimaraes.gerenciadorbiblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.model.Locatario;
import alexguimaraes.gerenciadorbiblioteca.repository.AluguelRepository;
import alexguimaraes.gerenciadorbiblioteca.repository.LocatarioRepository;

@ExtendWith(MockitoExtension.class)
class LocatarioServiceTest {

    @Mock
    private LocatarioRepository locatarioRepository;

    @Mock
    private AluguelRepository aluguelRepository;

    @InjectMocks
    private LocatarioService locatarioService;

    @Test
    void deveAtualizarLocatarioComIdPersistido() {
        Locatario existente = new Locatario();
        existente.setId(3L);

        Locatario atualizado = new Locatario();

        when(locatarioRepository.findById(3L)).thenReturn(Optional.of(existente));
        when(locatarioRepository.save(any(Locatario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Locatario resultado = locatarioService.atualizar(atualizado, 3L);

        assertEquals(3L, resultado.getId());
        verify(locatarioRepository).save(atualizado);
    }

    @Test
    void deveLancarExcecaoQuandoLocatarioNaoExisteNaExclusao() {
        when(locatarioRepository.findById(88L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> locatarioService.deleteLocatario(88L));
    }

    @Test
    void deveLancarExcecaoQuandoLocatarioTemPendenciaAtiva() {
        Locatario locatario = new Locatario();
        locatario.setId(4L);

        when(locatarioRepository.findById(4L)).thenReturn(Optional.of(locatario));
        when(aluguelRepository.existePendenciaAtivaParaLocatario(any(), any())).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> locatarioService.deleteLocatario(4L));
    }

    @Test
    void deveExcluirLocatarioSemPendenciaAtiva() {
        Locatario locatario = new Locatario();
        locatario.setId(5L);

        when(locatarioRepository.findById(5L)).thenReturn(Optional.of(locatario));
        when(aluguelRepository.existePendenciaAtivaParaLocatario(any(), any())).thenReturn(false);

        locatarioService.deleteLocatario(5L);

        verify(locatarioRepository).delete(locatario);
    }
}
