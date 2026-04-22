package alexguimaraes.gerenciadorbiblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alexguimaraes.gerenciadorbiblioteca.model.Aluguel;
import alexguimaraes.gerenciadorbiblioteca.repository.AluguelRepository;

@Service
public class AluguelService {
    
    @Autowired
    private AluguelRepository aluguelRepository;

    public List<Aluguel> listarAlugueis() {
        return aluguelRepository.findAll();
    }

    public List<Aluguel> listarAlugueisAtivos() {
        return aluguelRepository.findByDataDevolucaoIsNull();
    }

    public List<Aluguel> listarAlugueisPorLocatario(Long locatarioId) {
        return aluguelRepository.findByLocatarioId(locatarioId);
    }

    public Aluguel salvar(Aluguel aluguel) {
        return aluguelRepository.save(aluguel);
    }
}

