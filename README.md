# TodoList API

API REST desenvolvida com Java e Spring Boot para gerenciamento de tarefas, disponibilizando operações de CRUD e recursos complementares de cache, validação, tratamento de exceções, documentação e monitoramento.

## Funcionalidades

- Cadastro de tarefas
- Listagem de tarefas
- Consulta de tarefa por ID
- Atualização de tarefa
- Exclusão de tarefa
- Validação dos dados de entrada
- Tratamento global de exceções com `@RestControllerAdvice`
- Cache local
- Logging com `@Slf4j`
- Documentação interativa com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator
- Testes automatizados com JUnit 5 e Mockito
- Execução em container Docker

## Tecnologias

- Java 21+
- Spring Boot
- H2 Database
- Swagger/OpenAPI
- Spring Boot Actuator
- Lombok
- JUnit 5
- Mockito
- Docker
- Maven

## Requisitos

- Java 21+
- Maven
- Docker (opcional)

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/Spring-boot-todolist.git
cd Spring-boot-todolist
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

A interface permite consultar e testar os endpoints disponíveis na API.

## Actuator

Endpoint de saúde da aplicação:

```text
http://localhost:8080/actuator/health
```

## API Endpoints

### Listar tarefas

```http
GET /api/tarefas
```

Retorna todas as tarefas cadastradas.

### Buscar tarefa por ID

```http
GET /api/tarefas/{id}
```

Retorna os dados da tarefa correspondente ao ID informado.

### Criar tarefa

```http
POST /api/tarefas
Content-Type: application/json
```

Exemplo:

```json
{
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar desenvolvimento de APIs REST",
  "completo": false
}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `titulo` | `String` | Título da tarefa. |
| `descricao` | `String` | Descrição da tarefa. |
| `completo` | `Boolean` | Indica se a tarefa foi concluída. |

### Atualizar tarefa

```http
PUT /api/tarefas/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar testes e cache",
  "completo": true
}
```

### Excluir tarefa

```http
DELETE /api/tarefas/{id}
```

Exclui a tarefa correspondente ao ID informado.

## Cache

A aplicação utiliza cache local para reduzir consultas repetidas aos dados das tarefas, conforme a implementação atual do serviço.

## Testes

Execute os testes automatizados com:

```bash
mvn test
```

Os testes utilizam JUnit 5 e Mockito.

## Docker

Gere o pacote da aplicação:

```bash
mvn clean package
```

Gere a imagem:

```bash
docker build -t todolist .
```

Execute o container:

```bash
docker run -p 8080:8080 todolist
```

## Fluxo simplificado

```text
Cliente
   │
   ▼
API REST
   │
   ▼
Validação
   │
   ▼
Serviço
   │
   ├── Cache
   │
   ▼
Persistência
   │
   ▼
H2 Database
```

## Status

Projeto desenvolvido para praticar a construção de APIs REST com Spring Boot, operações CRUD, cache, validação, tratamento de exceções, testes automatizados, documentação e monitoramento.
