package alexguimaraes.gerenciadorbiblioteca.dto.response;

public record AutorResponseDTO(
        Long id,
        String nome,
        String sexo,
        Integer anoNascimento,
        String cpf
) {}

