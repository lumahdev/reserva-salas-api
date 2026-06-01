# 📝 reserva-salas-api
API REST para a reserva de salas, com implementação de banco de dados, testes, autenticação de rotas e documentação.

## Tecnologias
- Framework **Spring Boot**
- Banco de Dados **PostgreSQL**
- Testes com **JUnit** e **MockMVC**
- Autenticação e segurança com **JWT** + **Auth0**
- **SpringDocs** para documentação

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
