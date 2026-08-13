# 📝 API para Reserva de Salas
API REST para a reserva de salas, com implementação de banco de dados, testes, autenticação de rotas e documentação.
<img width="1397" height="871" alt="image" src="https://github.com/user-attachments/assets/8cfda589-8ca7-4f9e-a5b4-0a2a2fdb0dc1" />


## Tecnologias
- **Java** com framework **Spring**
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

## Funcionalidades
- Usuário: cadastrar, listar todos, listar específico, editar, deletar.
- Sala: cadastrar, listar todos, listar específico, editar, deletar.
- Reserva: cadastrar, listar todos, listar específico, listar por usuário, listar por sala, editar, deletar.
- Login.

## Testes
Cada entidade possui testes.
<img width="1397" height="871" alt="image" src="https://github.com/user-attachments/assets/4628471d-ff7c-40f5-9e23-49095d041dbe" />



## Documentação
```bash
http://localhost:8080/swagger-ui/index.html
```
<img width="1920" height="963" alt="image" src="https://github.com/user-attachments/assets/1e20da62-ec76-4092-b09c-25b05bf1a550" />
<img width="1920" height="963" alt="image" src="https://github.com/user-attachments/assets/460fcbbb-edf2-42e6-bdae-b3ea0b5905f6" />
