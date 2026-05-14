
Projeto feitv, inspirado sobre uma plataforma de videos como netflix ou youtube.

Nós utilizamos no projeto
- Java 11+
- Swing (GUI)
- JDBC + PostgreSQL
- Padrão MVC (Model / View / Controller)

---

O que fizemos para rodar o projeto

## 1. fizemos o Banco de dados

1. Abrimos o pgAdmin (ou psql)
2. Executamos o arquivo `banco.sql` feito por nós e que está na raiz do projeto
3. Sendo assim isso criou o banco `feitv` com todas as tabelas e dados de exemplo

## 2. Configuraramos a conexão

Criamos`src/util/Conexao.java` e colocamos as informações do Banco de Dados no Pgadmin


private static final String URL     = "jdbc:postgresql://localhost:5432/feitv?sslmode=disable";
private static final String USUARIO = "postgres";
private static final String SENHA   = "123";


## 3. Driver JDBC

Adicionamos o driver PostgreSQL ao classpath:
- Baixe em: https://jdbc.postgresql.org/download/
- No IntelliJ: File > Project Structure > Libraries > "+"
- No Eclipse: Build Path > Add External JARs

### 4. Compilamos e rodamos

**IntelliJ / Eclipse:** Abrimos a pasta `src/` como projeto, adicionamos o driver JDBC e rodamos o `Main.java`
## no terminal colocamos
bash
javac -cp .:postgresql-42.x.x.jar src/**/*.java src/*.java
java -cp .:postgresql-42.x.x.jar:src Main


---

## Login para administrador feito por fora e login normal com cadastro feito dentro do projeto

| Perfil | Email | Senha |
|--------|-------|-------|
| Admin  | admin@feitv.com | admin123 |
| Usuário | (cadastre na tela) | (sua senha) |

---

## Estrutura do projeto

```
src/
├── Main.java

├── model/
│   ├── Situacao.java       ← interface
│   ├── Video.java          ← classe abstrata
│   ├── Filme.java          ← herda Video
│   ├── Serie.java          ← herda Video
│   ├── Usuario.java
│   └── ListaReproducao.java

├── dao/
│   ├── VideoDAO.java
│   ├── UsuarioDAO.java
│   └── ListaDAO.java

├── controller/
│   ├── VideoController.java
│   ├── UsuarioController.java
│   └── ListaController.java

├── view/
│   ├── TelaLogin.java      ← personalizamos o design
│   ├── TelaCadastro.java   ← personalizamos o design
│   ├── TelaPrincipal.java  ← personalizamos o design
│   ├── TelaFavoritos.java  ← personalizamos o design
│   └── TelaAdmin.java      ← personalizamos o design

└── util/
    └── Conexao.java
```

---

## Funcionalidades

### Usuário
- Cadastramos a conta e fizemos login
- Buscamos vídeos por nome
- Vemos detalhes (tipo, gênero, ano, descrição, situação)
- Curtirmos e descurtirmos vídeos (toggle)
- Gerenciamos listas de reprodução (criar, editar, excluir)
- Adicionamos e removemos vídeos das listas

### Administrador como fizemos em dupla
- Tem o Login separado (botão "Entrar como Admin")
- Cadastramos os vídeos (Filme ou Série)
- Excluimos vídeos
- Consultamos usuários cadastrados
- Vemos estatísticas: total de vídeos, total de usuários, Top 5 mais curtidos

---

## Diagrama de Classes 

```
<<interface>>
Situacao
    ↑ (implements)
Video (abstract)
├── Filme
└── Serie

Usuario
ListaReproducao
```

