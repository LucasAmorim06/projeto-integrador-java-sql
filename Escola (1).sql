-- ====================================================
-- CRIAÇÃO DO BANCO DE DADOS
-- ====================================================
CREATE DATABASE Escola;
USE Escola;

-- ====================================================
-- TABELA: Endereco
-- ====================================================

CREATE TABLE Endereco (
    ID_Endereco INT AUTO_INCREMENT PRIMARY KEY,
    Rua VARCHAR(100),
    Bairro VARCHAR(100),
    CEP VARCHAR(10)
);

-- ====================================================
-- TABELA: Professor
-- ====================================================
CREATE TABLE Professor (
    ID_Professor INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Professor VARCHAR(100) NOT NULL,
    Email VARCHAR(100),
    Telefone_Movel VARCHAR(20)
);

-- ====================================================
-- TABELA: Aluno
-- ====================================================
CREATE TABLE Aluno (
    ID_Aluno INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Aluno VARCHAR(100) NOT NULL,
    Serie VARCHAR(20),
    Data_Nascimento DATE,
    ID_Endereco INT,
    ID_Professor INT,
    FOREIGN KEY (ID_Endereco) REFERENCES Endereco(ID_Endereco),
    FOREIGN KEY (ID_Professor) REFERENCES Professor(ID_Professor)
);

-- ====================================================
-- TABELA: Turma
-- ====================================================
CREATE TABLE Turma (
    ID_Turma INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Turma VARCHAR(50) NOT NULL,
    Carga_Horaria INT
);

-- ====================================================
-- RELACIONAMENTO: Aluno estuda em Turma
-- (Um aluno pertence a uma turma)
-- ====================================================
ALTER TABLE Aluno
ADD COLUMN ID_Turma INT,
ADD FOREIGN KEY (ID_Turma) REFERENCES Turma(ID_Turma);

-- ====================================================
-- TABELA: Disciplina
-- ====================================================
CREATE TABLE Disciplina (
    ID_Disciplina INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Disciplina VARCHAR(100) NOT NULL,
    Carga_Horaria INT
);

-- ====================================================
-- RELACIONAMENTO: Professor leciona Disciplina
-- (N:N -> tabela associativa)
-- ====================================================
CREATE TABLE Professor_Disciplina (
    ID_Professor INT,
    ID_Disciplina INT,
    PRIMARY KEY (ID_Professor, ID_Disciplina),
    FOREIGN KEY (ID_Professor) REFERENCES Professor(ID_Professor),
    FOREIGN KEY (ID_Disciplina) REFERENCES Disciplina(ID_Disciplina)
);

-- ====================================================
-- RELACIONAMENTO: Aluno cursa Disciplina
-- (N:N -> tabela associativa com atributo "nota")
-- ====================================================
CREATE TABLE Aluno_Disciplina (
    ID_Aluno INT,
    ID_Disciplina INT,
    Nota DECIMAL(4,2),
    PRIMARY KEY (ID_Aluno, ID_Disciplina),
    FOREIGN KEY (ID_Aluno) REFERENCES Aluno(ID_Aluno),
    FOREIGN KEY (ID_Disciplina) REFERENCES Disciplina(ID_Disciplina)
);

-- 
ALTER TABLE Aluno
DROP FOREIGN KEY Aluno_ibfk_2,  -- nome da FK pode mudar aí no seu banco
DROP COLUMN ID_Professor;

-- 2) Adicionar ID_Professor em Turma
ALTER TABLE Turma
ADD COLUMN ID_Professor INT NULL,
ADD CONSTRAINT fk_turma_professor
    FOREIGN KEY (ID_Professor) REFERENCES Professor(ID_Professor);
    
    DELETE FROM Endereco
WHERE ID_Endereco > 0;
Select * from Aluno;




