# FEItv — Guia de Instalação no NetBeans

## Pré-requisitos
- NetBeans 12+ (ou Apache NetBeans)
- JDK 11 ou superior
- PostgreSQL instalado e rodando

---

## 1. Baixar o driver JDBC do PostgreSQL

O projeto precisa do arquivo `postgresql-42.7.3.jar` na pasta `lib/`.

1. Acesse: https://jdbc.postgresql.org/download/
2. Baixe o arquivo **postgresql-42.7.3.jar** (ou versão mais recente)
3. Cole o arquivo dentro da pasta `lib/` do projeto

---

## 2. Criar o banco de dados

1. Abra o **pgAdmin** ou o **psql**
2. Execute o arquivo `banco.sql` que está na raiz do projeto:
   ```
   psql -U postgres -f banco.sql
   ```
   Ou copie e cole o conteúdo do `banco.sql` no pgAdmin e execute.

3. Isso vai criar o banco `feitv` com todas as tabelas e dados de exemplo.

---

## 3. Configurar a conexão

Edite o arquivo `src/util/Conexao.java` se necessário:

```java
private static final String URL     = "jdbc:postgresql://localhost:5432/feitv";
private static final String USUARIO = "postgres";   // seu usuário do PostgreSQL
private static final String SENHA   = "1234";       // sua senha do PostgreSQL
```

---

## 4. Abrir no NetBeans

1. Abra o NetBeans
2. Vá em **File → Open Project**
3. Navegue até a pasta `FeiTv_Final` e clique em **Open Project**
4. Na aba **Projects**, expanda o projeto → clique com botão direito em **Libraries** → **Add JAR/Folder**
5. Selecione o arquivo `lib/postgresql-42.7.3.jar`
6. Clique com botão direito no projeto → **Run** (ou pressione F6)

---

## 5. Login padrão

| Tipo       | Email               | Senha     |
|------------|---------------------|-----------|
| Admin      | admin@feitv.com     | admin123  |
| Usuário    | (cadastre um novo)  | (sua senha)|

---

## Estrutura do Projeto

```
FeiTv_Final/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Video.java         (classe abstrata)
│   │   ├── Filme.java
│   │   ├── Serie.java
│   │   ├── Usuario.java
│   │   ├── ListaReproducao.java
│   │   └── Situacao.java      (interface)
│   ├── view/
│   │   ├── TelaLogin.java
│   │   ├── TelaCadastro.java
│   │   ├── TelaPrincipal.java
│   │   ├── TelaFavoritos.java
│   │   └── TelaAdmin.java
│   ├── controller/
│   │   ├── VideoController.java
│   │   ├── UsuarioController.java
│   │   └── ListaController.java
│   ├── dao/
│   │   ├── VideoDAO.java
│   │   ├── UsuarioDAO.java
│   │   └── ListaDAO.java
│   └── util/
│       └── Conexao.java
├── lib/
│   └── postgresql-42.7.3.jar  ← VOCÊ PRECISA BAIXAR E COLOCAR AQUI
├── nbproject/
│   ├── project.xml
│   ├── project.properties
│   └── build-impl.xml
├── banco.sql
├── build.xml
└── manifest.mf
```
