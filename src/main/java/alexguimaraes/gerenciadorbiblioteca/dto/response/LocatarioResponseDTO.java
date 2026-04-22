package alexguimaraes.gerenciadorbiblioteca.dto.response;

import java.time.LocalDate;

public record LocatarioResponseDTO(
        Long id,
        String nome,
        String sexo,
        String telefone,
        String email,
        LocalDate dataNascimento,
        String cpf
) {}

