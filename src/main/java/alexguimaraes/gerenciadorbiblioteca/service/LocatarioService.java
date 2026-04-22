package alexguimaraes.gerenciadorbiblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.model.Locatario;
import alexguimaraes.gerenciadorbiblioteca.repository.LocatarioRepository;

@Service
public class LocatarioService {
    
    @Autowired
    private LocatarioRepository locatarioRepository; 

    public List<Locatario> listarLocatarios() {
        return locatarioRepository.findAll();
    }

    public Locatario salvar(Locatario locatario) {
        return locatarioRepository.save(locatario);
    }

    public Locatario atualizar(Locatario locatario, long id) {
        Locatario locatarioExistente = locatarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Locatario nao encontrado"));
        locatario.setId(locatarioExistente.getId());
        return locatarioRepository.save(locatario);
    }

    public void deleteLocatario(Long id) {
        Locatario locatario = locatarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Locatario nao encontrado"));
        locatarioRepository.delete(locatario);
    }
}

