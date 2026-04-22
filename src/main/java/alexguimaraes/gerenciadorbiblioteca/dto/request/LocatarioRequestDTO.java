package alexguimaraes.gerenciadorbiblioteca.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LocatarioRequestDTO(
        @NotBlank(message = "Nome do locatario e obrigatorio")
        @Size(max = 255, message = "Nome do locatario deve ter no maximo 255 caracteres")
        String nome,

        @Size(max = 50, message = "Sexo deve ter no maximo 50 caracteres")
        String sexo,

        @NotBlank(message = "Telefone e obrigatorio")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "Telefone deve conter 10 ou 11 digitos numericos"
        )
        String telefone,

        @NotBlank(message = "Email e obrigatorio")
        @Email(message = "Email invalido")
        String email,

        @NotNull(message = "Data de nascimento e obrigatoria")
        @Past(message = "Data de nascimento invalida")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF e obrigatorio")
        @Pattern(
                regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$",
                message = "CPF deve estar no formato 00000000000 ou 000.000.000-00"
        )
        String cpf
) {}

