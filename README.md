# TodoList API

Esta é uma API REST desenvolvida para gerenciamento de tarefas (Todo List), implementando as operações básicas de um CRUD. A aplicação foi construída com foco em simplicidade e boas práticas de desenvolvimento, utilizando uma stack moderna para documentação, monitoramento e geração de logs.


## Tecnologias Utilizadas

- **Spring Boot**: framework principal para construção da API REST.
- **H2 Database**: banco de dados em memória utilizado para armazenamento das tarefas.
- **Swagger**: ferramenta de documentação interativa da API.
- **Spring Boot Actuator**: fornece endpoints para monitoramento e métricas da aplicação.
- **Integração Swagger + Actuator**: permite a visualização de métricas diretamente via interface Swagger.
- **Lombok (@slf4j)**: utilizado para geração automática de logs com a anotação `@Slf4j`.

## Requisitos

- Java 21+
- Maven

## Executando o Projeto

1. Clone o repositório:

```bash
git https://github.com/bispobr/Spring-boot-todolist.git
```


## Como usar

1. Inicie a aplicação
2. A API está acessivel atraves do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8080/actuator/health


## API Endpoints

API contem os seguintes endpoints :

```http request
GET /api/tarefas - retorna todas as tarefas registradas
```

```http request
GET /api/tarefas/{id} - retorna os dados de uma tarefa específica
```

```http request
POST /api/tarefas - cadastra uma nova tarefa no banco de dados
Content-Type: application/json

{
 "titulo": "xxxxxx",
 "descricao" : "xxxxxx",
 "completo" : "False"
}
```

| Parâmetro   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `titulo` | `String`  | **Obrigatório**. O título da tarefa 
| `descricao` | `String`  | **Obrigatório**. A descrição da tarefa 
| `completo` | `Boolean` | **Obrigatório**. O status da tarefa 

```http request
PUT /api/tarefas/{id} - atualiza as informações de uma tarefa existente
Content-Type: application/json

{
 "id": x,
 "titulo": "xxxxxx",
 "descricao": "xxxxxx",
 "completo": true
}
```


| Parâmetro   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `id` | `Long`    | **Obrigatório**. O id da tarefa 
| `titulo` | `String`  | **Obrigatório**. O título da tarefa 
| `descricao` | `String`  | **Obrigatório**. A descrição da tarefa 
| `completo` | `Boolean` | **Obrigatório**. O status da tarefa 


```http request
DELETE /api/tarefas/{id} - exclui uma tarefa do banco de dados

```


