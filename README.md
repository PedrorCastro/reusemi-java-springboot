🔄 Reusemi - Plataforma de Trocas Sustentáveis
https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk
https://img.shields.io/badge/Spring_Boot-3.0-green?style=for-the-badge&logo=springboot
https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql
https://img.shields.io/badge/REST_API-%E2%9C%93-lightgrey?style=for-the-badge

📖 Sobre o Projeto
O Reusemi é uma API REST desenvolvida em Java com Spring Boot para uma plataforma de trocas colaborativas de itens usados ou ecológicos. A proposta visa combater o descarte inadequado e promover o consumo consciente através de uma comunidade de usuários que realizam trocas sem envolvimento financeiro.

🎯 Objetivos
♻️ Reduzir o desperdício de produtos ainda úteis

🤝 Fomentar uma comunidade colaborativa de trocas

🌱 Promover a educação ambiental e consumo sustentável

💻 Oferecer uma plataforma acessível para trocas locais

🛠 Stack Tecnológica
Back-End
Java 17+

Spring Boot 3.x

Spring Security + JWT

Spring Data JPA

Maven

Banco de Dados
MySQL 8.0

Ferramentas
Git & GitHub

Postman (testes de API)

Swagger/OpenAPI (documentação)

📋 Funcionalidades
✅ Implementadas
Autenticação JWT - Sistema seguro de login

CRUD de Usuários - Gerenciamento completo de perfis

CRUD de Produtos - Cadastro e gestão de itens para troca

Sistema de Trocas - Propostas e negociações

API RESTful - Endpoints documentados e padronizados

Validações - Tratamento de erros e exceções

🚧 Em Desenvolvimento
Sistema de Mensagens - Chat entre usuários

Filtros por Localização - Busca por proximidade geográfica

Dashboard de Métricas - Estatísticas de uso e impacto

🚀 Como Executar
Pré-requisitos
Java 17 ou superior

MySQL 8.0+

Maven 3.6+

Configuração
Clone o repositório

bash
git clone https://github.com/PedrorCastro/reusemi-java-springboot.git
cd reusemi-java-springboot
Configure o banco de dados MySQL

sql
CREATE DATABASE reusemi_db;
CREATE USER 'reusemi_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON reusemi_db.* TO 'reusemi_user'@'localhost';
FLUSH PRIVILEGES;
Configure as variáveis de ambiente
Crie um arquivo application.properties em src/main/resources/:

properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/reusemi_db
spring.datasource.username=reusemi_user
spring.datasource.password=password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT
jwt.secret=your-secret-key-here
jwt.expiration=86400000

# Server
server.port=8080
Compile e execute a aplicação

bash
mvn clean install
mvn spring-boot:run
📚 Documentação da API
Autenticação
Método	Endpoint	Descrição
POST	/api/auth/login	Autenticação de usuário
POST	/api/auth/register	Registro de novo usuário
Usuários
Método	Endpoint	Descrição
GET	/api/users	Listar todos os usuários
GET	/api/users/{id}	Buscar usuário por ID
PUT	/api/users/{id}	Atualizar usuário
DELETE	/api/users/{id}	Excluir usuário
Produtos
Método	Endpoint	Descrição
GET	/api/products	Listar produtos disponíveis
POST	/api/products	Cadastrar novo produto
PUT	/api/products/{id}	Atualizar produto
DELETE	/api/products/{id}	Remover produto
Trocas
Método	Endpoint	Descrição
POST	/api/trades/propose	Propor uma troca
GET	/api/trades/user/{userId}	Listar trocas do usuário
PUT	/api/trades/{tradeId}/accept	Aceitar proposta
PUT	/api/trades/{tradeId}/reject	Rejeitar proposta
🏗️ Estrutura do Projeto
text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── reusemi/
│   │           ├── config/          # Configurações (Security, JWT, etc.)
│   │           ├── controller/      # Controladores REST
│   │           ├── service/         # Lógica de negócio
│   │           ├── repository/      # Camada de dados
│   │           ├── entity/          # Entidades JPA
│   │           ├── dto/             # Objetos de transferência
│   │           ├── security/        # Configurações de segurança
│   │           └── exception/       # Tratamento de exceções
│   └── resources/
│       ├── application.properties
│       └── data.sql                # Dados iniciais
└── test/
    └── java/
        └── com/
            └── reusemi/
                ├── controller/
                ├── service/
                └── repository/
🧪 Testes
Executar todos os testes
bash
mvn test
Executar testes específicos
bash
mvn test -Dtest=UserServiceTest
mvn test -Dtest=ProductControllerTest
🤝 Como Contribuir
Fork o projeto

Crie uma branch para sua feature

bash
git checkout -b feature/nova-feature
Commit suas mudanças

bash
git commit -m 'feat: adiciona nova funcionalidade'
Push para a branch

bash
git push origin feature/nova-feature
Abra um Pull Request

Padrões de Commit
feat: Nova funcionalidade

fix: Correção de bug

docs: Documentação

style: Formatação

refactor: Refatoração de código

test: Testes

📊 Status do Projeto
Desenvolvimento Ativo - Versão 1.0.0

✅ MVP Concluído

🚧 Expandindo Funcionalidades

🔄 Revisão de Código Contínua

📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

🌟 Agradecimentos
Comunidade Spring Boot


⚠️NOTA:
Este é um projeto de demonstração para fins educacionais.

Documentação oficial do Java

Contribuidores e testadores
