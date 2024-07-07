# Fórum Hub


ForumHub é uma aplicação backend construída como parte do desafio da ALURA + ONE (Oracle Next Education). Este projeto é uma API RESTful que gerencia cursos, tópicos, e usuários.

## Descrição
ForumHub é um sistema de fórum onde usuários podem se registrar, criar tópicos, responder a tópicos e gerenciar seus perfis. Ele é construído utilizando Spring Boot e segue boas práticas de desenvolvimento, incluindo autenticação e autorização.

## Instalação


## Configure o banco de dados no arquivo application.properties:

```properties
spring.datasource.url=jdbc:postgresql://seu.db:5432/forumhub
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```

## Instale as dependências e compile o projeto:

```bash
mvn clean install
```

## Execução
Para executar a aplicação, use o comando:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em http://localhost:8080.



### Endpoints
A documentação da API fornece informações detalhadas sobre todos os endpoints disponíveis, incluindo métodos, parâmetros e exemplos de respostas. Você pode explorar e testar os endpoints diretamente pela interface Swagger.

_Aqui estão alguns dos principais endpoints disponíveis na API:_

#### Autenticação
- **POST /login:** Autenticar um usuário.


#### Tópicos
- **POST /topicos:** Criar um novo tópico.
- **GET /topicos:** Listar 10 tópicos por página em ordem crescente.
- **GET /topicos/{id}:** Detalhar um tópico específico. ⚠️ 
- **PUT /topicos/{id}:** Atualizar um tópico. ⚠️
- **DELETE /topicos/{id}:** Excluir um tópico. ⚠️
  
⚠️ Autorização: somente usuários criadores dos tópicos podem executar essas ações destacadas.

# Implementações futuras :rocket:

  #### Usuários 
- **POST /usuarios:** Cadastrar um novo usuário.
- **GET /usuarios/{id}:** Detalhar um usuário específico.
- **PUT /usuarios/{id}:** Atualizar um usuário.
- **DELETE /usuarios/{id}:** Excluir um usuário.

   #### Respostas
- **POST /repostas:** Cadastrar uma nova resposta.
- **GET /respostas/{id}:** Detalhar uma resposta específica.
- **PUT /respostas/{id}:** Atualizar uma resposta.
- **DELETE /respostas/{id}:** Excluir uma resposta.

## Documentação da API
ForumHub utiliza SpringDoc para gerar a documentação da API. Após iniciar a aplicação, você pode acessar a documentação interativa em:

```bash
http://localhost:8080/swagger-ui.html
```
