package alexguimaraes.gerenciadorbiblioteca.dto.response;

import java.util.List;

public record LivroResponseDTO(
        Long id,
        String nome,
        String isbn,
        List<Long> autoresIds
) {}

