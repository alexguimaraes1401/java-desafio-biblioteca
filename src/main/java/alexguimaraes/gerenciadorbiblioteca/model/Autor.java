package alexguimaraes.gerenciadorbiblioteca.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String sexo;

    @Column(nullable = false)
    private Integer anoNascimento;

    @Column(nullable = false, unique = true)
    private String cpf;

    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros;

    public Autor(String nome, String sexo, Integer anoNascimento, String cpf, Set<Livro> livros) {
        this(null, nome, sexo, anoNascimento, cpf, livros);
    }

    public Autor(Long id, String nome, String sexo, Integer anoNascimento, String cpf, Set<Livro> livros) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.anoNascimento = anoNascimento;
        this.cpf = cpf;
        this.livros = livros;
    }
}

