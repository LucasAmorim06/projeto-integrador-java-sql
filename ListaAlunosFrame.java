package view;

import DAO.AlunoDAO;
import model.Aluno;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListaAlunosFrame extends JFrame {

    private JTextArea area;

    public ListaAlunosFrame() {
        setTitle("Alunos Cadastrados");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topo = new JPanel();
        topo.setBackground(new Color(0, 102, 51));
        JLabel lbl = new JLabel("Lista de Alunos");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        topo.add(lbl);

        area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);

        carregarAlunos();

        setLayout(new BorderLayout());
        add(topo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void carregarAlunos() {
        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.listar();

        area.setText("");

        for (Aluno a : lista) {
            area.append(
                "ID: " + a.getIdAluno() +
                " | Nome: " + a.getNomeAluno() +
                " | Série: " + a.getSerie() +
                "\n"
            );
        }
    }
}
