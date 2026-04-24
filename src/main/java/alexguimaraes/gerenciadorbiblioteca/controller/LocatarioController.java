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

import alexguimaraes.gerenciadorbiblioteca.dto.request.LocatarioRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.LocatarioResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.mapper.LocatarioMapper;
import alexguimaraes.gerenciadorbiblioteca.model.Locatario;
import alexguimaraes.gerenciadorbiblioteca.service.LocatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/locatarios")
@Tag(name = "Locatarios", description = "Endpoints para gerenciar locatarios")
@Validated
public class LocatarioController {

    private final LocatarioService locatarioService;
    private final LocatarioMapper locatarioMapper;

    public LocatarioController(LocatarioService locatarioService, LocatarioMapper locatarioMapper) {
        this.locatarioService = locatarioService;
        this.locatarioMapper = locatarioMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os locatarios", description = "Retorna uma lista de todos os locatarios cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de locatarios retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<LocatarioResponseDTO>> listarLocatarios() {
        List<LocatarioResponseDTO> response = locatarioService.listarLocatarios()
                .stream()
            .map(locatarioMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo locatario", description = "Cria um novo registro de locatario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Locatario criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<LocatarioResponseDTO> adicionarLocatario(@Valid @RequestBody LocatarioRequestDTO locatarioRequestDTO) {
        Locatario locatario = locatarioMapper.toEntity(locatarioRequestDTO);
        Locatario salvo = locatarioService.salvar(locatario);
        return ResponseEntity.status(HttpStatus.CREATED).body(locatarioMapper.toResponseDTO(salvo));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um locatario", description = "Atualiza os dados de um locatario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locatario atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Locatario nao encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados invalidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<LocatarioResponseDTO> atualizarLocatario(@Valid @RequestBody LocatarioRequestDTO locatarioRequestDTO, @PathVariable Long id) {
        Locatario locatario = locatarioMapper.toEntity(id, locatarioRequestDTO);
        Locatario atualizado = locatarioService.atualizar(locatario, id);
        return ResponseEntity.ok(locatarioMapper.toResponseDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um locatario", description = "Remove um locatario do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Locatario deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Locatario nao encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarLocatario(@PathVariable Long id) {
        locatarioService.deleteLocatario(id);
        return ResponseEntity.noContent().build();
    }
}

