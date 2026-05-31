# 📝 reserva-salas-api
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#) [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F.svg?logo=springboot&logoColor=white)](#) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?logo=postgresql&logoColor=white)](#) [![JUnit](https://img.shields.io/badge/JUnit-25A162.svg?logo=junit5&logoColor=white)](#) [![Auth0](https://img.shields.io/badge/Auth0-EB5424.svg?logo=auth0&logoColor=white)](#) [![JSON Web Tokens](https://img.shields.io/badge/JSON_Web_Tokens-000000.svg?logo=json-web-tokens&logoColor=white)](#) [![Swagger](https://img.shields.io/badge/Swagger-85EA2D.svg?logo=swagger&logoColor=black)](#) API REST para a reserva de salas desenvolvida utilizando Framework **Spring Boot**, **PostgreSQL** para persistência de dados, **JUnit** para realização de testes, **JWT** para autenticação e **Swagger** para documentação.

## Entidades
| Entidade | Atributos                                      |
|-|------------------------------------------------|
| Usuário | nome, sobrenome, telefone, email, login, senha |
| Sala | nome, capacidade, andar, bloco, status         |
| Reserva | dataInicio, dataFim, salaId, usuarioId, status |


## Documentação
```bash
http://localhost:8080/swagger-ui/index.html
```