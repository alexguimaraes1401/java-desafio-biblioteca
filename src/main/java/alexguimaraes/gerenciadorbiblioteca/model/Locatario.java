package alexguimaraes.gerenciadorbiblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "locatario")
public class Locatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String sexo;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    private String cpf;

    public Locatario(String nome, String sexo, String telefone, String email, LocalDate dataNascimento, String cpf) {
        this(null, nome, sexo, telefone, email, dataNascimento, cpf);
    }

    public Locatario(Long id, String nome, String sexo, String telefone, String email, LocalDate dataNascimento, String cpf) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }
}

