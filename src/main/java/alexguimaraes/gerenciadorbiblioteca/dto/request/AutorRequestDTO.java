package alexguimaraes.gerenciadorbiblioteca.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AutorRequestDTO(
        @NotBlank(message = "Nome do autor e obrigatorio")
        @Size(max = 255, message = "Nome do autor deve ter no maximo 255 caracteres")
        String nome,

        @Size(max = 50, message = "Sexo deve ter no maximo 50 caracteres")
        String sexo,

        @NotNull(message = "Ano de nascimento e obrigatorio")
        @Min(value = 1500, message = "Ano de nascimento invalido")
        @Max(value = 2100, message = "Ano de nascimento invalido")
        Integer anoNascimento,

        @NotBlank(message = "CPF e obrigatorio")
        @Pattern(
                regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$",
                message = "CPF deve estar no formato 00000000000 ou 000.000.000-00"
        )
        String cpf
) {}

