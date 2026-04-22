-- Carga inicial de dados para ambiente local/desenvolvimento.
-- Esta migration roda uma única vez pelo Flyway.

INSERT INTO autor (id, nome, sexo, ano_nascimento, cpf) VALUES
(1, 'J.R.R. Tolkien', 'Masculino', 1892, '101.202.303-44'),
(2, 'George R.R. Martin', 'Masculino', 1948, '202.303.404-55'),
(3, 'J.K. Rowling', 'Feminino', 1965, '303.404.505-66'),
(4, 'Isaac Asimov', 'Masculino', 1920, '404.505.606-77'),
(5, 'Agatha Christie', 'Feminino', 1890, '505.606.707-88'),
(6, 'H.P. Lovecraft', 'Masculino', 1890, '606.707.808-99'),
(7, 'Stephen King', 'Masculino', 1947, '707.808.909-00'),
(8, 'Mary Shelley', 'Feminino', 1797, '808.909.001-11'),
(9, 'Neil Gaiman', 'Masculino', 1960, '909.010.111-22'),
(10, 'Ursula K. Le Guin', 'Feminino', 1929, '010.111.212-33')
ON CONFLICT DO NOTHING;

INSERT INTO livro (id, nome, isbn, data_publicacao) VALUES
(1, 'O Senhor dos Aneis', '978-8533613379', '1954-07-29'),
(2, 'A Guerra dos Tronos', '978-8580442625', '1996-08-01'),
(3, 'Harry Potter e a Pedra Filosofal', '978-8532530783', '1997-06-26'),
(4, 'Fundacao', '978-8576571551', '1951-06-01'),
(5, 'O Assassinato no Expresso do Oriente', '978-8525414540', '1934-01-01'),
(6, 'O Chamado de Cthulhu', '978-8580444568', '1928-02-01'),
(7, 'O Iluminado', '978-8539004034', '1977-01-28'),
(8, 'Frankenstein', '978-8580444735', '1818-01-01'),
(9, 'Coraline', '978-8580442779', '2002-02-24'),
(10, 'O Feiticeiro de Terramar', '978-8576573128', '1968-01-01')
ON CONFLICT DO NOTHING;

INSERT INTO livro_autor (livro_id, autor_id) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10)
ON CONFLICT DO NOTHING;

INSERT INTO locatario (id, nome, sexo, telefone, email, data_nascimento, cpf) VALUES
(1, 'Carlos Andrade', 'Masculino', '11988887777', 'carlos@email.com', '1990-05-15', '123.456.789-01'),
(2, 'Mariana Silva', 'Feminino', '21977776666', 'mariana@email.com', '1985-08-22', '234.567.890-12'),
(3, 'Roberto Souza', 'Masculino', '31966665555', 'roberto@email.com', '1992-12-01', '345.678.901-23'),
(4, 'Ana Paula Oliveira', 'Feminino', '41955554444', 'ana@email.com', '1998-03-10', '456.789.012-34'),
(5, 'Fernando Gomes', 'Masculino', '51944443333', 'fernando@email.com', '1988-07-30', '567.890.123-45'),
(6, 'Julia Costa', 'Feminino', '61933332222', 'julia@email.com', '1995-11-05', '678.901.234-56'),
(7, 'Marcos Rocha', 'Masculino', '71922221111', 'marcos@email.com', '1982-01-20', '789.012.345-67'),
(8, 'Beatriz Lima', 'Feminino', '81911110000', 'beatriz@email.com', '2000-09-14', '890.123.456-78'),
(9, 'Ricardo Alves', 'Masculino', '91900009999', 'ricardo@email.com', '1993-06-25', '901.234.567-89'),
(10, 'Patricia Mendes', 'Feminino', '11912345678', 'patricia@email.com', '1987-04-12', '012.345.678-90')
ON CONFLICT DO NOTHING;

INSERT INTO aluguel (id, locatario_id, data_retirada, data_devolucao) VALUES
(1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2 days'),
(2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2 days'),
(3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2 days'),
(4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2 days'),
(5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '2 days')
ON CONFLICT DO NOTHING;

INSERT INTO aluguel_livros (aluguel_id, livro_id) VALUES
(1, 1), (1, 2), (2, 3), (3, 4), (4, 5), (5, 6)
ON CONFLICT DO NOTHING;