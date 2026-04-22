package alexguimaraes.gerenciadorbiblioteca.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "aluguel")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "locatario_id", nullable = false)
    private Locatario locatario;

    @Column(nullable = false)
    private LocalDateTime dataRetirada;
    
    @Column(nullable = false)
    private LocalDateTime dataDevolucao;

    @ManyToMany
    @JoinTable(
            name = "aluguel_livros",
            joinColumns = @JoinColumn(name = "aluguel_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livros;

    public Aluguel(Locatario locatario, LocalDateTime dataRetirada, LocalDateTime dataDevolucao, List<Livro> livros) {
        this(null, locatario, dataRetirada, dataDevolucao, livros);
    }

    public Aluguel(Long id, Locatario locatario, LocalDateTime dataRetirada, LocalDateTime dataDevolucao, List<Livro> livros) {
        this.id = id;
        this.locatario = locatario;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.livros = livros;
    }

    @PrePersist
    protected void onCreate() {
        if (this.dataRetirada == null) {
            this.dataRetirada = LocalDateTime.now();
        }
        if (this.dataDevolucao == null) {
            this.dataDevolucao = this.dataRetirada.plusDays(2);
        }
    }
}

