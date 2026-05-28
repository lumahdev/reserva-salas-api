# 📝 reserva-salas-api
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#) [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F.svg?logo=springboot&logoColor=white)](#) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?logo=postgresql&logoColor=white)](#) [![JUnit](https://img.shields.io/badge/JUnit-25A162.svg?logo=junit5&logoColor=white)](#) [![Auth0](https://img.shields.io/badge/Auth0-EB5424.svg?logo=auth0&logoColor=white)](#) [![JSON Web Tokens](https://img.shields.io/badge/JSON_Web_Tokens-000000.svg?logo=json-web-tokens&logoColor=white)](#)API REST para a reserva de salas desenvolvida utilizando PostgreSQL, Spring Boot, JUnit para testes, Auth0 + JWT para autenticação.

## Entidades
| Entidade | Atributos                                      |
|-|------------------------------------------------|
| Usuário | nome, sobrenome, telefone, email, login, senha |
| Sala | nome, capacidade, andar, bloco, status         |
| Reserva | dataInicio, dataFim, salaId, usuarioId, status |
| Excecao (RuntimeException) | message, status                                |

## Rotas
### 1. Cadastrar usuário
Dados nome, sobrenome, email, telefone, login e senha, cadastra um usuário. Os dados não podem ser brancos ou nulos, email e telefone devem estar corretamente formatados. Não é possível cadastrar um usuário com email ou telefone já existentes.
- **POST** `/usuarios/`
- **Corpo da requisição:**
```bash
{
  "nome": "José",
  "sobrenome": "Bezerra",
  "email": "jose@email.com",
  "telefone": "11987590982",
  "login": "jose_be",
  "senha": "Senha@123"
}
```

### 2. Listar usuários
Retorna todos os usuários cadastrados, com paginação.
- **GET** `/usuarios/`

### 3. Listar usuário
Dado id, retorna as informações do usuário, caso ele exista.
- **GET** `/usuarios/{id}`

### 4. Editar usuário
Dados id e email e/ou telefone, atualiza a informação do usuário, caso ele exista.
- **PUT** `/usuarios/{id}`
- **Corpo da requisição:**
```bash
{
  "email": "jose@email.com",
  "telefone": "11987590982"
}
```

### 5. Deletar usuário
Dados id do usuário, o deleta do banco de dados, junto com suas reservas feitas.
- **DELETE** `/usuarios/{id}`

### 6. Cadastrar sala
Dados nome, capacidade, andar e bloco, cadastra uma sala. Os dados não podem ser brancos ou nulos. Não é possível cadastrar uma sala com nome já existentes.
- **POST** `/salas/`
- **Corpo da requisição:**
```bash
{
  "nome": "101",
  "capacidade": 50,
  "andar": "1",
  "bloco": "Orquídeas"
}
```

### 7. Listar salas
Retorna todas as salas cadastradas, com paginação.
- **GET** `/salas/`

### 8. Listar sala
Dado id, retorna as informações da sala, caso ela exista.
- **GET** `/salas/{id}`

### 9. Mudar disponibilidade da sala
Dados id, atualiza a disponibilidade da sala (disponível <-> indisponível), caso ele exista.
- **PUT** `/salas/{id}`

### 10. Deletar sala
Dado id, deleta a sala do banco de dados, junto com as reservas feitas nela.
- **DELETE** `/salas/{id}`

### 11. Cadastrar reserva
Dados data inicial, data final, id do usuário e id do cliente, cadastra uma reserva. Os dados não podem ser brancos ou nulos, as datas devem ser futuras e não é possível com que a data final seja anterior à inicial. Não é possível realizar uma reserva quando usuário e salas são inexistentes ou já exista uma reserva para a data especificada.
- **POST** `/reservas/`
- **Corpo da requisição:**
```bash
{
  "dataInicio": "2026-05-30",
  "dataFim": "2026-06-30",
  "salaId": 1,
  "usuarioId": 1
}
```

### 12. Listar reservas
Retorna todas as reservas cadastradas, com paginação.
- **GET** `/reservas/`

### 13. Listar reserva
Dado id, retorna as informações da reserva, caso ela exista.
- **GET** `/reservas/{id}`

### 14. Listar reservas de um usuário
Dado id, retorna as reservas feitas por um usuário, com paginação.
- **GET** `/reservas/usuarios/{id}`

### 15. Listar reservas em uma sala
Dado id, retorna as reservas feitas em uma sala, com paginação.
- **GET** `/reservas/salas/{id}`

### 16. Mudar disponibilidade da reserva
Dados id, atualiza a disponibilidade da reserva (ativa <-> cancelada), caso ele exista.
- **PUT** `/reservas/{id}`

### 17. Deletar reserva
Dado id, deleta a reserva.
- **DELETE** `/reservas/{id}`