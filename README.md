# Desafio Biblioteca API

API REST em Spring Boot para gerenciamento de biblioteca.

## Stack

- Java 17
- Spring Boot 4.0.3
- Maven Wrapper (mvnw / mvnw.cmd)
- PostgreSQL
- Flyway
- SpringDoc OpenAPI (Swagger UI)

## Pré-requisitos

- JDK 17+
- Docker e Docker Compose (opcional, recomendado)

## Como rodar

### Opção 1 - Com Docker Compose

Na raiz do projeto:

```bash
docker compose up --build
```

Serviços esperados:

- API: http://localhost:8080
- Adminer: http://localhost:8081
- PostgreSQL: localhost:5432

Credenciais do banco (padrão):

- database: library_db
- user: admin
- password: admin

### Opção 2 - Rodando local com Maven Wrapper

1. Garanta que o PostgreSQL esteja disponível em `localhost:5432`.
2. Crie o banco `library_db`.
3. Execute:

Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

## Testes

Windows:

```bash
.\mvnw.cmd test
```

Linux/macOS:

```bash
./mvnw test
```

## Build

Compilar (sem gerar jar):

Windows:

```bash
.\mvnw.cmd clean compile
```

Linux/macOS:

```bash
./mvnw clean compile
```

Empacotar (gera jar):

Windows:

```bash
.\mvnw.cmd clean package
```

Linux/macOS:

```bash
./mvnw clean package
```

Executar jar:

```bash
java -jar target/libraryManager-0.0.1-SNAPSHOT.jar
```

## Documentação da API

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Endpoints disponíveis hoje

Base URLs:

- /autores
- /livros
- /locatarios
- /alugueis

### Autores

- GET /autores
- POST /autores
- PUT /autores/{id}
- DELETE /autores/{id}

Exemplo de payload para POST /autores:

```json
{
  "nome": "Machado de Assis",
  "sexo": "Masculino",
  "anoNascimento": 1839,
  "cpf": "123.456.789-10"
}
```

### Livros

- GET /livros
- POST /livros
- PUT /livros/{id}
- DELETE /livros/{id}

Exemplo de payload para POST /livros:

```json
{
  "nome": "Clean Code",
  "isbn": "9780132350884",
  "autoresIds": [1]
}
```

### Locatários

- GET /locatarios
- POST /locatarios
- PUT /locatarios/{id}
- DELETE /locatarios/{id}

Exemplo de payload para POST /locatarios:

```json
{
  "nome": "Ana Souza",
  "sexo": "Feminino",
  "telefone": "11988887777",
  "email": "ana@email.com",
  "dataNascimento": 1996,
  "cpf": "12345678901"
}
```

### Aluguéis

- GET /alugueis
- GET /alugueis/ativos
- GET /alugueis/locatario/{locatarioId}
- POST /alugueis

Exemplo de payload para POST /alugueis:

```json
{
  "locatarioId": 1,
  "livrosIds": [1, 2],
  "dataRetirada": "2026-04-22T10:00:00",
  "dataDevolucao": "2026-04-24T10:00:00"
}
```

Observação: se `dataRetirada` e `dataDevolucao` não forem enviadas, o sistema preenche automaticamente.

## Validações e padrão de resposta de erro

- Validações de entrada são aplicadas com Bean Validation nos DTOs de request.
- Conversões entre request/response e entidades são feitas por mappers dedicados.
- Exceções são tratadas globalmente com resposta JSON padronizada.

Formato de erro:

```json
{
  "timestamp": "2026-04-22T11:30:00-03:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Dados de entrada invalidos",
  "path": "/alugueis",
  "validationErrors": {
    "locatarioId": "Locatario e obrigatorio"
  }
}
```

## Migração de banco

As tabelas são criadas via Flyway em:

- src/main/resources/db/migration/V1_0_0_001__criar_estrutura_inicial.sql

## Dados de exemplo

A carga inicial de dados roda automaticamente via Flyway em:

- src/main/resources/db/migration/V1_0_0_002__inserir_dados_iniciais.sql

## Troubleshooting de compilação

- Garanta que os arquivos `.java` estejam em UTF-8 sem BOM.
- O código-fonte ativo da aplicação está no pacote `alexguimaraes.gerenciadorbiblioteca`.

## Status atual (resumo)

- Estrutura em camadas aplicada: controller, service, repository, model, dto, mapper e exception handler global.
- Endpoints principais de autores, livros, locatarios e alugueis implementados.
- Projeto compilando com sucesso.
- Testes automatizados ainda em evolução (próxima etapa do desafio).
