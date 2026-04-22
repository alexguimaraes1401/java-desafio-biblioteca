package alexguimaraes.gerenciadorbiblioteca.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AluguelResponseDTO(
        Long id,
        Long locatarioId,
        List<Long> livrosIds,
        LocalDateTime dataRetirada,
        LocalDateTime dataDevolucao
) {}
