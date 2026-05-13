# FEItv - Projeto CCM310

Plataforma de informações sobre vídeos (filmes e séries), desenvolvida em Java com Swing, JDBC PostgreSQL e padrão MVC.

---

## Tecnologias

- Java 11+
- Swing (GUI)
- JDBC + PostgreSQL
- Padrão MVC (Model / View / Controller)

---

## Como rodar

### 1. Banco de dados

1. Abra o pgAdmin (ou psql)
2. Execute o arquivo `banco.sql` que está na raiz do projeto
3. Isso cria o banco `feitv` com todas as tabelas e dados de exemplo

### 2. Configurar a conexão

Abra `src/util/Conexao.java` e ajuste se necessário:

```java
private static final String URL     = "jdbc:postgresql://localhost:5432/feitv";
private static final String USUARIO = "postgres";
private static final String SENHA   = "1234";
```

### 3. Driver JDBC

Adicione o driver PostgreSQL ao classpath:
- Baixe em: https://jdbc.postgresql.org/download/
- No IntelliJ: File > Project Structure > Libraries > "+"
- No Eclipse: Build Path > Add External JARs

### 4. Compilar e rodar

- **IntelliJ / Eclipse:** Abra a pasta `src/` como projeto, adicione o driver JDBC e rode `Main.java`
- **Terminal:**
```bash
javac -cp .:postgresql-42.x.x.jar src/**/*.java src/*.java
java -cp .:postgresql-42.x.x.jar:src Main
```

---

## Login padrão

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
│   ├── TelaLogin.java      ← personalize o visual!
│   ├── TelaCadastro.java   ← personalize o visual!
│   ├── TelaPrincipal.java
│   ├── TelaFavoritos.java
│   └── TelaAdmin.java
└── util/
    └── Conexao.java
```

---

## Funcionalidades

### Usuário
- Cadastrar conta e fazer login
- Buscar vídeos por nome
- Ver detalhes (tipo, gênero, ano, descrição, situação)
- Curtir e descurtir vídeos (toggle)
- Gerenciar listas de reprodução (criar, editar, excluir)
- Adicionar e remover vídeos das listas

### Administrador
- Login separado (botão "Entrar como Admin")
- Cadastrar vídeos (Filme ou Série)
- Excluir vídeos
- Consultar usuários cadastrados
- Ver estatísticas: total de vídeos, total de usuários, Top 5 mais curtidos

---

## Diagrama de Classes (conforme especificação)

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

---

## Personalização das telas de Login e Cadastro

As telas `TelaLogin.java` e `TelaCadastro.java` têm a **lógica pronta** mas o layout é livre para você editar. Os campos e botões já estão declarados como `protected` — basta reposicioná-los e estilizá-los como quiser dentro do construtor.
