package alexguimaraes.gerenciadorbiblioteca.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alexguimaraes.gerenciadorbiblioteca.dto.request.LivroRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.LivroResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.mapper.LivroMapper;
import alexguimaraes.gerenciadorbiblioteca.model.Autor;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.repository.AutorRepository;
import alexguimaraes.gerenciadorbiblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Endpoints para gerenciar livros")
@Validated
public class LivroController {

    private final LivroService livroService;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;
    
    public LivroController(LivroService livroService, AutorRepository autorRepository, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.autorRepository = autorRepository;
        this.livroMapper = livroMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista de todos os livros cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<LivroResponseDTO>> listarLivros() {
        List<LivroResponseDTO> response = livroService.listarLivros()
                .stream()
            .map(livroMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo livro", description = "Cria um novo registro de livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<LivroResponseDTO> adicionarLivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        Livro livro = livroMapper.toEntity(livroRequestDTO, resolveAutores(livroRequestDTO.autoresIds()));
        Livro salvo = livroService.salvar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroMapper.toResponseDTO(salvo));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro", description = "Atualiza os dados de um livro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro nÃ£o encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<LivroResponseDTO> atualizarLivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO, @PathVariable Long id) {
        Livro livro = livroMapper.toEntity(id, livroRequestDTO, resolveAutores(livroRequestDTO.autoresIds()));
        Livro atualizado = livroService.atualizar(livro, id);
        return ResponseEntity.ok(livroMapper.toResponseDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um livro", description = "Remove um livro do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro nÃ£o encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deleteLivro(id);
        return ResponseEntity.noContent().build();
    }

    private List<Autor> resolveAutores(List<Long> autoresIds) {
        if (autoresIds == null || autoresIds.isEmpty()) {
            return List.of();
        }

        List<Autor> autores = autorRepository.findAllById(autoresIds);
        if (autores.size() != autoresIds.size()) {
            throw new BusinessRuleException("Um ou mais autores informados nao existem");
        }
        return autores;
    }
}

