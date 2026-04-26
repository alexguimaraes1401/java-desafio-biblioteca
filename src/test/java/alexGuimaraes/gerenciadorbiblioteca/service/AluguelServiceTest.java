package alexguimaraes.gerenciadorbiblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import alexguimaraes.gerenciadorbiblioteca.model.Aluguel;
import alexguimaraes.gerenciadorbiblioteca.repository.AluguelRepository;

@ExtendWith(MockitoExtension.class)
class AluguelServiceTest {

    @Mock
    private AluguelRepository aluguelRepository;

    @InjectMocks
    private AluguelService aluguelService;

    @Test
    void deveListarAlugueisAtivos() {
        List<Aluguel> alugueis = List.of(new Aluguel());
        when(aluguelRepository.findAtivosEm(any())).thenReturn(alugueis);

        List<Aluguel> resultado = aluguelService.listarAlugueisAtivos();

        assertEquals(1, resultado.size());
        verify(aluguelRepository).findAtivosEm(any());
    }

    @Test
    void deveListarAlugueisPorLocatario() {
        List<Aluguel> alugueis = List.of(new Aluguel());
        when(aluguelRepository.findByLocatarioId(9L)).thenReturn(alugueis);

        List<Aluguel> resultado = aluguelService.listarAlugueisPorLocatario(9L);

        assertEquals(1, resultado.size());
        verify(aluguelRepository).findByLocatarioId(9L);
    }

    @Test
    void deveSalvarAluguel() {
        Aluguel aluguel = new Aluguel();
        when(aluguelRepository.save(aluguel)).thenReturn(aluguel);

        Aluguel resultado = aluguelService.salvar(aluguel);

        assertEquals(aluguel, resultado);
        verify(aluguelRepository).save(aluguel);
    }
}
