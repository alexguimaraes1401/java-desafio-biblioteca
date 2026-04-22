package alexguimaraes.gerenciadorbiblioteca.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AluguelRequestDTO(
        @NotNull(message = "Locatario e obrigatorio")
        Long locatarioId,

        @NotEmpty(message = "Informe ao menos um livro")
        List<Long> livrosIds,

        LocalDateTime dataRetirada,

        LocalDateTime dataDevolucao
) {}
