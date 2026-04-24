SELECT setval(pg_get_serial_sequence('autor', 'id'), COALESCE((SELECT MAX(id) FROM autor), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('livro', 'id'), COALESCE((SELECT MAX(id) FROM livro), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('locatario', 'id'), COALESCE((SELECT MAX(id) FROM locatario), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('aluguel', 'id'), COALESCE((SELECT MAX(id) FROM aluguel), 0) + 1, false);
