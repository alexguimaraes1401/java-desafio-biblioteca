package alexguimaraes.gerenciadorbiblioteca.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import alexguimaraes.gerenciadorbiblioteca.dto.request.AluguelRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.AluguelResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.model.Aluguel;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.model.Locatario;

@Component
public class AluguelMapper {

    public Aluguel toEntity(AluguelRequestDTO requestDTO, Locatario locatario, List<Livro> livros) {
        return new Aluguel(
                locatario,
                requestDTO.dataRetirada(),
                requestDTO.dataDevolucao(),
                livros
        );
    }

    public AluguelResponseDTO toResponseDTO(Aluguel aluguel) {
        List<Long> livrosIds = aluguel.getLivros() == null
                ? List.of()
                : aluguel.getLivros().stream().map(Livro::getId).toList();

        Long locatarioId = aluguel.getLocatario() == null
                ? null
                : aluguel.getLocatario().getId();

        return new AluguelResponseDTO(
                aluguel.getId(),
                locatarioId,
                livrosIds,
                aluguel.getDataRetirada(),
                aluguel.getDataDevolucao()
        );
    }
}
