# Project-Funeral

## Descrição
O Project-Funeral é uma API REST desenvolvida com Spring Boot para gerenciar serviços funerários. O sistema permite o cadastro de funerárias, usuários e avaliações de serviços prestados, facilitando a busca e avaliação de estabelecimentos funerários.

## Objetivos do Projeto
- Digitalizar o processo de busca e avaliação de serviços funerários
- Centralizar informações sobre funerárias disponíveis
- Permitir aos usuários compartilhar experiências e avaliações
- Proporcionar transparência na qualidade dos serviços funerários

## Membros da Equipe
- Marcelo Henrique
- Josysllan Wislly
- Aquiles Arruda
- José Renato
- Luiz Orlando
- Luanderson Arlindo

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.4.5
- Maven
- H2 Database (banco de dados em memória)

### Dependências Spring Boot
- Spring Data JPA
- Spring Web
- Spring DevTools
- Spring Validation
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Estrutura do Projeto
O projeto segue a arquitetura MVC (Model-View-Controller) com as seguintes camadas:

### Entidades
- **UserModel**: Cadastro e autenticação de usuários
- **FuneralHomeModel**: Gerenciamento de funerárias 
- **FeedbackModel**: Avaliações e comentários sobre os serviços das funerárias

### API Endpoints
O projeto disponibiliza endpoints REST para as seguintes operações:

#### Usuários
- `POST /users` - Cadastrar um novo usuário
- `GET /users` - Listar todos os usuários
- `GET /users/{id}` - Buscar usuário por ID
- `PUT /users/{id}` - Atualizar dados de um usuário
- `DELETE /users/{id}` - Remover um usuário

#### Funerárias
- `POST /funeral-homes` - Cadastrar uma nova funerária
- `GET /funeral-homes` - Listar todas as funerárias
- `GET /funeral-homes/{id}` - Buscar funerária por ID
- `PUT /funeral-homes/{id}` - Atualizar dados de uma funerária
- `DELETE /funeral-homes/{id}` - Remover uma funerária

#### Feedbacks/Avaliações
- `POST /feedbacks` - Registrar uma nova avaliação
- `GET /feedbacks` - Listar todas as avaliações
- `GET /feedbacks/{id}` - Buscar avaliação por ID
- `GET /funeral-homes/{id}/feedbacks` - Listar avaliações de uma funerária
- `PUT /feedbacks/{id}` - Atualizar uma avaliação
- `DELETE /feedbacks/{id}` - Remover uma avaliação

### Tratamento de Exceções
O sistema possui tratamento global de exceções com respostas HTTP apropriadas:
- 404 (Not Found): Quando um recurso não é encontrado
- 400 (Bad Request): Validações de entrada falham
- 409 (Conflict): Conflitos de dados (ex: duplicação de email, CNPJ, etc.)

## Como Executar o Projeto

### Pré-requisitos
- JDK 21 ou superior
- Maven instalado (opcional, pode usar o wrapper ./mvnw)
- IDE de sua preferência (Spring Tool Suite, IntelliJ IDEA, VSCode, etc.)

### Passos para Execução
1. Clone o repositório
   ```
   git clone https://github.com/luandersonarlindo/Project-Funeral.git
   ```

2. Navegue até a pasta do projeto
   ```
   cd Project-Funeral
   ```

3. Execute o projeto usando Maven
   ```
   ./mvnw spring-boot:run
   ```
   
   Ou no Windows:
   ```
   mvnw.cmd spring-boot:run
   ```

4. Acesse a aplicação em: http://localhost:8080

### Documentação da API
A documentação interativa da API está disponível através do Swagger UI:
- URL: http://localhost:8080/swagger-ui/index.html

### Testes
Para executar os testes:
```
./mvnw test
```

## Configuração do Banco de Dados
O projeto utiliza o H2 Database (banco em memória) com as seguintes configurações:
- URL: jdbc:h2:mem:Project-Funeral
- Console H2: http://localhost:8080/h2-console
- Configurações de acesso definidas no application.properties

## Modelos de Dados

### UserModel
```
{
  "id": "UUID",
  "username": "string",
  "email": "string",
  "password": "string"
}
```

### FuneralHomeModel
```
{
  "id": "UUID",
  "name": "string",
  "cnpj": "string",
  "address": "string",
  "phone": "string"
}
```

### FeedbackModel
```
{
  "id": "UUID",
  "comment": "string",
  "rating": "integer",
  "createdAt": "LocalDateTime",
  "user": "UserModel",
  "funeralHome": "FuneralHomeModel"
}
```

## Exemplos de Requisições

### Usuários

#### Cadastro de Usuário
```http
POST /users HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "username": "usuario_exemplo",
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

**Resposta de Sucesso:**
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "username": "usuario_exemplo",
  "email": "usuario@exemplo.com"
}
```

#### Consulta de Usuário por ID
```http
GET /users/a1b2c3d4-e5f6-7890-abcd-ef1234567890 HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Resposta de Sucesso:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "username": "usuario_exemplo",
  "email": "usuario@exemplo.com"
}
```

### Funerárias

#### Cadastro de Funerária
```http
POST /funeral-homes HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "name": "Funerária Serenidade",
  "cnpj": "12.345.678/0001-90",
  "address": "Rua das Flores, 123 - Centro",
  "phone": "(11) 98765-4321"
}
```

**Resposta de Sucesso:**
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": "b2c3d4e5-f6g7-8901-hijk-lm2345678901",
  "name": "Funerária Serenidade",
  "cnpj": "12.345.678/0001-90",
  "address": "Rua das Flores, 123 - Centro",
  "phone": "(11) 98765-4321"
}
```

#### Listagem de Funerárias
```http
GET /funeral-homes HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Resposta de Sucesso:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": "b2c3d4e5-f6g7-8901-hijk-lm2345678901",
    "name": "Funerária Serenidade",
    "cnpj": "12.345.678/0001-90",
    "address": "Rua das Flores, 123 - Centro",
    "phone": "(11) 98765-4321"
  },
  {
    "id": "c3d4e5f6-g7h8-9012-ijkl-mn3456789012",
    "name": "Funerária Paz Eterna",
    "cnpj": "98.765.432/0001-10",
    "address": "Av. Principal, 456 - Jardim",
    "phone": "(11) 91234-5678"
  }
]
```

### Feedbacks

#### Criação de Avaliação/Feedback
```http
POST /feedbacks HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "comment": "Serviço prestado com respeito e atenção em um momento difícil para nossa família.",
  "rating": 5,
  "userId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "funeralHomeId": "b2c3d4e5-f6g7-8901-hijk-lm2345678901"
}
```

**Resposta de Sucesso:**
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": "d4e5f6g7-h8i9-0123-jklm-no4567890123",
  "comment": "Serviço prestado com respeito e atenção em um momento difícil para nossa família.",
  "rating": 5,
  "createdAt": "2025-08-05T15:30:45",
  "user": {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "username": "usuario_exemplo"
  },
  "funeralHome": {
    "id": "b2c3d4e5-f6g7-8901-hijk-lm2345678901",
    "name": "Funerária Serenidade"
  }
}
```

#### Consulta de Avaliações de uma Funerária
```http
GET /funeral-homes/b2c3d4e5-f6g7-8901-hijk-lm2345678901/feedbacks HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Resposta de Sucesso:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": "d4e5f6g7-h8i9-0123-jklm-no4567890123",
    "comment": "Serviço prestado com respeito e atenção em um momento difícil para nossa família.",
    "rating": 5,
    "createdAt": "2025-08-05T15:30:45",
    "user": {
      "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "username": "usuario_exemplo"
    }
  },
  {
    "id": "e5f6g7h8-i9j0-1234-klmn-op5678901234",
    "comment": "Atendimento profissional e atencioso.",
    "rating": 4,
    "createdAt": "2025-08-03T10:15:22",
    "user": {
      "id": "f6g7h8i9-j0k1-2345-lmno-pq6789012345",
      "username": "outro_usuario"
    }
  }
]
```

## Segurança e Boas Práticas
- Validação de entradas para evitar injeção de dados maliciosos
- Tratamento adequado de exceções com mensagens claras
- Proteção contra duplicação de dados sensíveis (email, CNPJ, etc.)
- Uso de DTOs para transferência segura de dados
