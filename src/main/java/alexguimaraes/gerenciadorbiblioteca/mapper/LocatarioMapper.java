package alexguimaraes.gerenciadorbiblioteca.mapper;

import org.springframework.stereotype.Component;

import alexguimaraes.gerenciadorbiblioteca.dto.request.LocatarioRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.LocatarioResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.model.Locatario;

@Component
public class LocatarioMapper {

    public Locatario toEntity(LocatarioRequestDTO requestDTO) {
        return new Locatario(
                requestDTO.nome(),
                requestDTO.sexo(),
                requestDTO.telefone(),
                requestDTO.email(),
                requestDTO.dataNascimento(),
                requestDTO.cpf()
        );
    }

    public Locatario toEntity(Long id, LocatarioRequestDTO requestDTO) {
        return new Locatario(
                id,
                requestDTO.nome(),
                requestDTO.sexo(),
                requestDTO.telefone(),
                requestDTO.email(),
                requestDTO.dataNascimento(),
                requestDTO.cpf()
        );
    }

    public LocatarioResponseDTO toResponseDTO(Locatario locatario) {
        return new LocatarioResponseDTO(
                locatario.getId(),
                locatario.getNome(),
                locatario.getSexo(),
                locatario.getTelefone(),
                locatario.getEmail(),
                locatario.getDataNascimento(),
                locatario.getCpf()
        );
    }
}
