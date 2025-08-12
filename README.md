### **API de Finanças**

---

# **API de Finanças**

Este projeto é uma **API de Finanças Pessoais** que permite ao usuário gerenciar carteiras, registrar receitas e despesas, acompanhar o histórico de transações e organizar categorias de movimentações financeiras.
A API utiliza autenticação via **JWT**, garantindo segurança na maior parte dos endpoints.

---

## **Arquitetura do Projeto**

A arquitetura segue o padrão **em camadas**, garantindo separação de responsabilidades e facilidade de manutenção.

### **Estrutura Geral**

```
Finance-API/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/github/guilhermecustodionieto/finance_api/
│   │   │       ├── config/
│   │   │       ├── controllers/
│   │   │       ├── dtos/
│   │   │       ├── entities/
│   │   │       ├── repositories/
│   │   │       ├── services/
│   │   │       ├── security/
│   │   │       └── FinanceApiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-docker.yml
│   │       └── application-dev.yml
│   └── test/
├── compose.yml
├── dockerfile.yml
├── pom.xml
├── Docs/
│   └── Docs.asta
└── README.md
```

### **Backend: Detalhes**

* **`FinanceApiApplication.java`**: Arquivo principal do projeto.
* **`application.properties`**: Configurações da aplicação e conexão com banco de dados.
* **`config/`**: Configurações de segurança, Swagger e outras dependências globais.
* **`controllers/`**: Controladores que expõem os endpoints.
* **`dtos/`**: Objetos de transferência de dados entre as camadas.
* **`entities/`**: Entidades que representam as tabelas no banco de dados.
* **`repositories/`**: Interfaces JPA para manipulação dos dados.
* **`services/`**: Camada de regras de negócio.

---

## **Funcionalidades**

### **Backend**

* **Autenticação e registro de usuários** com geração de token JWT.
* **Gerenciamento de usuários**: criação e visualização de informações básicas.
* **Gestão de carteiras (wallets)**: cadastro e acompanhamento de saldos.
* **Registro de receitas e despesas** com categorias.
* **Histórico de transações** detalhado.
* **Organização por categorias** de transações.

## **Diagramas**

- Diagrama de Classes 
<img src="Class Diagram0.png" alt="Diagrama de Classes">

- UML
<img src="UseCase Diagram0.png" alt="UML">

---

## **Instalação**

**Pré-requisitos**
- Docker instalado
- Docker Compose (já vem junto com Docker Desktop)
- Arquivo .env configurado na raiz do projeto com os dados seguindo o exemplo abaixo:
```
SPRING_DATASOURCE_URL=url-do-seu-banco
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=12345678
SPRING_JPA_HIBERNATE_DDL_AUTO=update

POSTGRES_DB=database
POSTGRES_USER=usuario
POSTGRES_PASSWORD=12345678

PGADMIN_DEFAULT_EMAIL=email@gmail.com
PGADMIN_DEFAULT_PASSWORD=2345678
```


### **Clonar o Repositório**

```bash
git clone https://github.com/usuario/finance-api.git
cd finance-api
```

### **Configuração do Backend**

1. Edite o arquivo `application-dev.yml` com os dados do seu banco de dados.
2. Execute o projeto:

```bash
docker compose up --build
```

---

## **Rotas da API**
Beleza — com base no Swagger que você mostrou, dá para organizar todas as rotas da sua API Finance em um formato bem estruturado.
Vou separar por recurso e método HTTP, exatamente como está no Swagger:

---

## **Rotas da Finance API**

### **User – Gerenciamento de usuários**

* **GET** `/user/{id}` → Busca um usuário pelo ID
* **DELETE** `/user/{id}` → Exclui um usuário pelo ID
* **PATCH** `/user/{id}` → Atualiza dados de um usuário
* **GET** `/user` → Lista todos os usuários

### **Transaction History – Consulta ao histórico de transações**

* **GET** `/transaction-history/{id}` → Busca um histórico de transação pelo ID

### **Recipe – Operações relacionadas a receitas**

* **GET** `/recipe` → Lista todas as receitas
* **POST** `/recipe` → Cria uma nova receita
* **GET** `/recipe/{id}` → Busca uma receita pelo ID

### **Wallet – Gerenciamento de carteiras**

* **GET** `/wallet/{id}` → Busca uma carteira pelo ID
* **DELETE** `/wallet/{id}` → Exclui uma carteira pelo ID

### **Waste – Operações relacionadas a gastos**

* **GET** `/waste` → Lista todos os gastos
* **POST** `/waste` → Cria um novo gasto
* **GET** `/waste/{id}` → Busca um gasto pelo ID
* **DELETE** `/waste/{id}` → Deleta um gasto pelo ID e carteira
* **PATCH** `/waste/{id}` → Atualiza um gasto existente
* **GET** `/waste/payment-format` → Busca gastos pelo formato de pagamento
* **GET** `/waste/description` → Busca gastos pela descrição


### **Transaction Category – Categorias de transações**

* **POST** `/transaction-category` → Cria uma nova categoria de transação
* **GET** `/transaction-category/{id}` → Busca uma categoria pelo ID
* **DELETE** `/transaction-category/{id}` → Deleta uma categoria pelo ID
* **PATCH** `/transaction-category/{id}` → Atualiza uma categoria existente
* **GET** `/transaction-category/` → Lista todas as categorias de transação

### **Authentication – Autenticação e registro**

* **POST** `/auth/register` → Registra um novo usuário
* **POST** `/auth/login` → Realiza login de usuário

> **Observação:** Exceto pelos endpoints de autenticação (`/auth`), todos os demais requerem **token JWT** válido.

---

## **Tecnologias Utilizadas**

### **Backend**

* Java
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* PostgreSQL
* Swagger / OpenAPI

## **Infraestrutura**
* Asta
* Docker
* Postman
  
---

## **Contribuição**

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto.
2. Crie uma branch para a feature:

   ```bash
   git checkout -b minha-feature
   ```
3. Faça suas alterações e envie um pull request.

---

## **Autor**

Desenvolvido por [Guilherme Custódio Nieto](https://www.linkedin.com/in/guilherme-cust%C3%B3dio-nieto/). 🚀

---

Se quiser, posso complementar esse README com **prints do Swagger e um diagrama de arquitetura** para deixar mais atrativo no GitHub e no LinkedIn.
Quer que eu já adicione essa parte visual?
