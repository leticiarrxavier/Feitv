
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE video (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('Filme', 'Serie')),
    genero VARCHAR(100),
    ano INT,
    descricao TEXT,
    curtidas INT DEFAULT 0
);


CREATE TABLE curtida (
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    video_id INT REFERENCES video(id) ON DELETE CASCADE,
    PRIMARY KEY (usuario_id, video_id)
);


CREATE TABLE lista_reproducao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE
);


CREATE TABLE lista_video (
    lista_id INT REFERENCES lista_reproducao(id) ON DELETE CASCADE,
    video_id INT REFERENCES video(id) ON DELETE CASCADE,
    PRIMARY KEY (lista_id, video_id)
);


INSERT INTO usuario (nome, email, senha) VALUES ('Administrador', 'admin@feitv.com', 'admin123');

INSERT INTO video (titulo, tipo, genero, ano, descricao) VALUES
('Inception', 'Filme', 'Ficção Científica', 2010, 'Um ladrão que rouba segredos dos sonhos.'),
('The Dark Knight', 'Filme', 'Ação', 2008, 'Batman enfrenta o Coringa.'),
('Breaking Bad', 'Serie', 'Drama', 2008, 'Professor vira traficante.'),
('Stranger Things', 'Serie', 'Terror/Ficção', 2016, 'Crianças enfrentam o mundo invertido.'),
('Interstellar', 'Filme', 'Ficção Científica', 2014, 'Viagem espacial para salvar a humanidade.');
