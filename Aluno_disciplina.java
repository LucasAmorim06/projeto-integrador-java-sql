package model;

public class Aluno_disciplina {
    private Aluno aluno;
    private Disciplina disciplina;
    private double nota;

    public Aluno_disciplina() {}

    public Aluno_disciplina(Aluno aluno, Disciplina disciplina, double nota) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
    }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}
