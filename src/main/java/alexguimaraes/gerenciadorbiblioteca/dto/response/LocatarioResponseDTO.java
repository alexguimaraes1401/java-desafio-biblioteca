package alexguimaraes.gerenciadorbiblioteca.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record LocatarioResponseDTO(
        Long id,
        String nome,
        String sexo,
        String telefone,
        String email,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataNascimento,
        String cpf
) {}

