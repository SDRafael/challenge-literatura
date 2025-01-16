# Literatura API

## Descrição
O **Literatura API** é um software que consome a API Gutendex para obter informações sobre livros e seus autores, armazenando os dados em um banco de dados PostgreSQL. O objetivo principal é permitir a busca, visualização e organização de livros e autores.

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação do projeto.
- **Spring Boot 3.4.1**: Framework para simplificar o desenvolvimento de aplicações Java.
- **Hibernate**: Framework ORM para gerenciar a persistência de dados.
- **PostgreSQL**: Banco de dados relacional para armazenar os livros e autores.
- **Jakarta Persistence API**: Para mapeamento objeto-relacional.
- **Gson**: Biblioteca para desserialização de JSON.
- **Maven**: Gerenciador de dependências e build.

## Funcionalidades

### Menu Principal
O menu principal oferece as seguintes opções:

1. **Buscar Livro por Nome**:
   - Permite buscar um livro na API Gutendex informando seu título.
   - O livro encontrado é salvo no banco de dados junto com os dados do autor principal.

2. **Listar Livros Registrados**:
   - Lista todos os livros cadastrados no banco, incluindo o título e o autor de cada livro.

3. **Listar Autores Registrados**:
   - Exibe os autores cadastrados no banco, mostrando o nome, ano de nascimento e ano de morte (se aplicável).


### Persistência e Validações

- Antes de salvar um livro ou autor no banco de dados, o sistema verifica se o autor ou o livro já estão registrados para evitar duplicatas.


### Banco de Dados

O projeto utiliza **PostgreSQL** como banco de dados relacional. Certifique-se de configurar corretamente as credenciais no arquivo `application.properties` ou `application.yml` do Spring Boot.

Exemplo de configuração:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Estrutura do Projeto

- **models**: Contém as classes que representam as entidades do sistema (Livro, Autor).
- **repositorio**: Interfaces que estendem `JpaRepository` para gerenciar operações no banco de dados.
- **menu**: Contém a classe `Menu` responsável pela interação do usuário com o sistema.
- **service**: Serviços para comunicação com a API Gutendex.
