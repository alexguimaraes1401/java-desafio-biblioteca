package alexguimaraes.gerenciadorbiblioteca.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alexguimaraes.gerenciadorbiblioteca.dto.request.AluguelRequestDTO;
import alexguimaraes.gerenciadorbiblioteca.dto.response.AluguelResponseDTO;
import alexguimaraes.gerenciadorbiblioteca.exception.BusinessRuleException;
import alexguimaraes.gerenciadorbiblioteca.exception.ResourceNotFoundException;
import alexguimaraes.gerenciadorbiblioteca.mapper.AluguelMapper;
import alexguimaraes.gerenciadorbiblioteca.model.Aluguel;
import alexguimaraes.gerenciadorbiblioteca.model.Livro;
import alexguimaraes.gerenciadorbiblioteca.model.Locatario;
import alexguimaraes.gerenciadorbiblioteca.repository.LivroRepository;
import alexguimaraes.gerenciadorbiblioteca.repository.LocatarioRepository;
import alexguimaraes.gerenciadorbiblioteca.service.AluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alugueis")
@Tag(name = "Alugueis", description = "Endpoints para gerenciar alugueis")
@Validated
public class AluguelController {

    private final AluguelService aluguelService;
    private final LocatarioRepository locatarioRepository;
    private final LivroRepository livroRepository;
    private final AluguelMapper aluguelMapper;

    public AluguelController(
            AluguelService aluguelService,
            LocatarioRepository locatarioRepository,
            LivroRepository livroRepository,
            AluguelMapper aluguelMapper
    ) {
        this.aluguelService = aluguelService;
        this.locatarioRepository = locatarioRepository;
        this.livroRepository = livroRepository;
        this.aluguelMapper = aluguelMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alugueis", description = "Retorna todos os alugueis cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alugueis retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AluguelResponseDTO>> listarAlugueis() {
        List<AluguelResponseDTO> response = aluguelService.listarAlugueis()
                .stream()
                .map(aluguelMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar alugueis ativos", description = "Retorna alugueis sem data de devolucao")
    public ResponseEntity<List<AluguelResponseDTO>> listarAlugueisAtivos() {
        List<AluguelResponseDTO> response = aluguelService.listarAlugueisAtivos()
                .stream()
                .map(aluguelMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locatario/{locatarioId}")
    @Operation(summary = "Listar alugueis por locatario", description = "Retorna alugueis por id do locatario")
    public ResponseEntity<List<AluguelResponseDTO>> listarPorLocatario(@PathVariable Long locatarioId) {
        List<AluguelResponseDTO> response = aluguelService.listarAlugueisPorLocatario(locatarioId)
                .stream()
                .map(aluguelMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar aluguel", description = "Cria um novo aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluguel criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "404", description = "Locatario nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negocio violada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AluguelResponseDTO> criarAluguel(@Valid @RequestBody AluguelRequestDTO requestDTO) {
        Locatario locatario = locatarioRepository.findById(requestDTO.locatarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Locatario nao encontrado"));

        List<Livro> livros = livroRepository.findAllById(requestDTO.livrosIds());
        if (livros.size() != requestDTO.livrosIds().size()) {
            throw new BusinessRuleException("Um ou mais livros informados nao existem");
        }

        Aluguel aluguel = aluguelMapper.toEntity(requestDTO, locatario, livros);
        Aluguel salvo = aluguelService.salvar(aluguel);
        return ResponseEntity.status(HttpStatus.CREATED).body(aluguelMapper.toResponseDTO(salvo));
    }
}
