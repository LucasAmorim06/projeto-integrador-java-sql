package view;

import DAO.professor_dao;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListaProfessoresFrame extends JFrame {

    private JTextArea area;

    public ListaProfessoresFrame() {
        setTitle("Professores Cadastrados");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topo = new JPanel();
        topo.setBackground(new Color(0, 102, 51));
        JLabel lbl = new JLabel("Lista de Professores");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        topo.add(lbl);

        area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);

        carregarProfessores();

        setLayout(new BorderLayout());
        add(topo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void carregarProfessores() {
        professor_dao dao = new professor_dao();
        List<Professor> lista = dao.listar();

        area.setText("");

        for (Professor p : lista) {
            area.append(
                "ID: " + p.getIdProfessor() +
                " | Nome: " + p.getNomeProfessor() +
                " | Email: " + p.getEmail() +
                "\n"
            );
        }
    }
}
