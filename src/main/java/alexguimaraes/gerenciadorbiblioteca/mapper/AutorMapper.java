package alexguimaraes.gerenciadorbiblioteca.mapper;

import java.util.Collections;

import org.springframework.stereotype.Component;

import alexguimaraes.gerenciadorbiblioteca.dto.request.AutorRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.AutorResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.model.Autor;

@Component
public class AutorMapper {

    public Autor toEntity(AutorRequestDTO requestDTO) {
        return new Autor(
                requestDTO.nome(),
                requestDTO.sexo(),
                requestDTO.anoNascimento(),
                requestDTO.cpf(),
                Collections.emptySet()
        );
    }

    public Autor toEntity(Long id, AutorRequestDTO requestDTO) {
        return new Autor(
                id,
                requestDTO.nome(),
                requestDTO.sexo(),
                requestDTO.anoNascimento(),
                requestDTO.cpf(),
                Collections.emptySet()
        );
    }

    public AutorResponseDTO toResponseDTO(Autor autor) {
        return new AutorResponseDTO(
                autor.getId(),
                autor.getNome(),
                autor.getSexo(),
                autor.getAnoNascimento(),
                autor.getCpf()
        );
    }
}
