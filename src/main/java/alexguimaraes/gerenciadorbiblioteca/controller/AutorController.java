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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alexguimaraes.gerenciadorbiblioteca.dto.request.AutorRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.AutorResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.mapper.AutorMapper;
import alexguimaraes.gerenciadorbiblioteca.model.Autor;
import alexguimaraes.gerenciadorbiblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@OpenAPIDefinition(
    info = @Info(
        title = "Library Manager API",
        version = "1.0.0",
        description = "API para gerenciamento de uma biblioteca com controle de autores, livros e locatarios"
    )
)
@RestController
@RequestMapping("/autores")
@Tag(name = "Autores", description = "Endpoints para gerenciar autores")
@Validated
public class AutorController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;
    
    public AutorController(AutorService autorService, AutorMapper autorMapper) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os autores", description = "Retorna uma lista de todos os autores cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AutorResponseDTO>> listarAutores() {
        List<AutorResponseDTO> response = autorService.listarAutores()
                .stream()
            .map(autorMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar autor por nome", description = "Retorna autores filtrados por nome (parcial, sem case sensitive)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    public ResponseEntity<List<AutorResponseDTO>> buscarAutoresPorNome(@RequestParam String nome) {
        List<AutorResponseDTO> response = autorService.buscarPorNome(nome)
                .stream()
                .map(autorMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo autor", description = "Cria um novo registro de autor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Autor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AutorResponseDTO> adicionarAutor(@Valid @RequestBody AutorRequestDTO autorRequestDTO) {
        Autor autor = autorMapper.toEntity(autorRequestDTO);
        Autor salvo = autorService.salvar(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorMapper.toResponseDTO(salvo));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um autor", description = "Atualiza os dados de um autor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor nao encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@Valid @RequestBody AutorRequestDTO autorRequestDTO, @PathVariable Long id) {
        Autor autor = autorMapper.toEntity(id, autorRequestDTO);
        Autor atualizado = autorService.atualizar(autor, id);
        return ResponseEntity.ok(autorMapper.toResponseDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um autor", description = "Remove um autor do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor nao encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }
}

