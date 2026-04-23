package alexguimaraes.gerenciadorbiblioteca.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import alexguimaraes.gerenciadorbiblioteca.dto.request.LivroRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.LivroResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.model.Autor;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;

@Component
public class LivroMapper {

    public Livro toEntity(LivroRequestDTO requestDTO, List<Autor> autores) {
        return new Livro(
                requestDTO.nome(),
                requestDTO.isbn(),
                requestDTO.dataPublicacao(),
                autores
        );
    }

    public Livro toEntity(Long id, LivroRequestDTO requestDTO, List<Autor> autores) {
        return new Livro(
                id,
                requestDTO.nome(),
                requestDTO.isbn(),
                requestDTO.dataPublicacao(),
                autores
        );
    }

    public LivroResponseDTO toResponseDTO(Livro livro) {
        List<Long> autoresIds = livro.getAutores() == null
                ? List.of()
                : livro.getAutores().stream().map(Autor::getId).toList();

        return new LivroResponseDTO(
                livro.getId(),
                livro.getNome(),
                livro.getIsbn(),
            livro.getDataPublicacao(),
                autoresIds
        );
    }
}
