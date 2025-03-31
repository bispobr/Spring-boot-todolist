# TodoList
Este repositório contém um projeto CRUD simples construído usando Java Spring. O objetivo deste repositório é praticar e construir todos os métodos CRUD usando o Java Spring atraves de uma aplicação de Todolist.

## Instalação

1. Clone o repositório:

```bash
git https://github.com/bispobr/Spring-boot-todolist.git
```

2. Instale as dependências com Maven

## Como usar

1. Inicie a aplicação com o Maven
2. API está acessivem atraves do Link http://localhost:8080


## API Endpoints

API contem os seguintes endpoints :

```http request
GET /api/tarefas - Retorna uma Lista com todos os objetos.
```

```http request
GET /api/tarefas/1 - Retorna o objeto com o id especificado.
```

```http request
POST /api/tarefas - Registra uma novo tarefa.
Content-Type: application/json

{
 "titulo": "xxxxxx",
 "descricao" : "xxxxxx",
 "completo" : "False"
}
```

```http request
PUT /api/tarefas/1 - Atualiza a tarefa de id especificado.
Content-Type: application/json

{
 "id": x,
 "titulo": "xxxxxx",
 "descricao": "xxxxxx",
 "completo": true
}
```

```http request
DELETE /api/tarefas/1 - Deleta a tarefa de id especificado.

```
## Banco-de-Dados
Esse projeto utiliza o H2 como Banco de Dados.

