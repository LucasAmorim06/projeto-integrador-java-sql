package model;

import java.util.Date;

public class Aluno {
    private int idAluno;
    private String nomeAluno;
    private String serie;
    private Date dataNascimento;
    private Endereco endereco;
    private Professor professor;
    private Turma turma;

    public Aluno() {}

    public Aluno(int idAluno, String nomeAluno, String serie, Date dataNascimento,
                 Endereco endereco, Professor professor, Turma turma) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.serie = serie;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.professor = professor;
        this.turma = turma;
    }

    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    @Override
    public String toString() {
        return nomeAluno + " (" + serie + ")";
    }
}

