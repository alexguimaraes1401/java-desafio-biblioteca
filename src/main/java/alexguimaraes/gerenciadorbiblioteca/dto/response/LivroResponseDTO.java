package alexguimaraes.gerenciadorbiblioteca.dto.response;

import java.time.LocalDate;
import java.util.List;

public record LivroResponseDTO(
        Long id,
        String nome,
        String isbn,
        LocalDate dataPublicacao,
        List<Long> autoresIds
) {}

