package alexguimaraes.gerenciadorbiblioteca.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO(
        @NotBlank(message = "Nome do livro e obrigatorio")
        @Size(max = 255, message = "Nome do livro deve ter no maximo 255 caracteres")
        String nome,

        @NotBlank(message = "ISBN e obrigatorio")
        @Pattern(
                regexp = "^(97(8|9))?\\d{9}(\\d|X)$|^(97(8|9))-?\\d{1,5}-?\\d{1,7}-?\\d{1,7}-?[\\dX]$",
                message = "ISBN invalido"
        )
        String isbn,

        @NotNull(message = "Data de publicacao e obrigatoria")
        @PastOrPresent(message = "Data de publicacao invalida")
        LocalDate dataPublicacao,

        List<Long> autoresIds
) {}

