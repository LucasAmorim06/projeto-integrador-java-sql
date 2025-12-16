package view;

import DAO.disciplina_dao;
import model.Disciplina;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroDisciplinaFrame extends JFrame {

    private JTextField txtNomeDisciplina;
    private JTextField txtCargaHoraria;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnFechar;
    private JButton btnListar;
    private JTextArea areaLista;

    public CadastroDisciplinaFrame() {
        setTitle("Cadastro de Disciplinas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
    }

    private void criarComponentes() {
        // Topo
        JPanel pnlTopo = new JPanel();
        pnlTopo.setBackground(new Color(0, 102, 51));
        JLabel lblTitulo = new JLabel("Cadastro de Disciplinas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        pnlTopo.add(lblTitulo);

        // Formulário
        JPanel pnlForm = new JPanel();
        pnlForm.setBackground(new Color(220, 244, 228));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlForm.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome da disciplina:");
        JLabel lblCarga = new JLabel("Carga horária (horas):");

        txtNomeDisciplina = new JTextField();
        txtCargaHoraria = new JTextField();

        pnlForm.add(lblNome);
        pnlForm.add(txtNomeDisciplina);
        pnlForm.add(lblCarga);
        pnlForm.add(txtCargaHoraria);

        // Botões
        JPanel pnlBotoes = new JPanel();
        pnlBotoes.setBackground(new Color(220, 244, 228));
        pnlBotoes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        pnlBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");
        btnListar = new JButton("Listar Disciplinas");

        estilizarBotaoAcao(btnSalvar);
        estilizarBotaoSecundario(btnLimpar);
        estilizarBotaoSecundario(btnFechar);
        estilizarBotaoSecundario(btnListar);

        pnlBotoes.add(btnListar);
        pnlBotoes.add(btnLimpar);
        pnlBotoes.add(btnFechar);
        pnlBotoes.add(btnSalvar);

        // Lista
        areaLista = new JTextArea(10, 30);
        areaLista.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLista);

        JPanel pnlLista = new JPanel(new BorderLayout());
        pnlLista.setBackground(new Color(220, 244, 228));
        pnlLista.setBorder(BorderFactory.createTitledBorder("Disciplinas cadastradas"));
        pnlLista.add(scroll, BorderLayout.CENTER);

        // Ações
        btnSalvar.addActionListener(e -> salvarDisciplina());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> dispose());
        btnListar.addActionListener(e -> listarDisciplinas());

        // Montagem
        setLayout(new BorderLayout());
        add(pnlTopo, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);
        add(pnlBotoes, BorderLayout.SOUTH);
        add(pnlLista, BorderLayout.EAST);
    }

    private void estilizarBotaoAcao(JButton botao) {
        botao.setBackground(new Color(0, 153, 76));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.BOLD, 13));
    }

    private void estilizarBotaoSecundario(JButton botao) {
        botao.setBackground(new Color(200, 230, 210));
        botao.setForeground(Color.DARK_GRAY);
        botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    private void salvarDisciplina() {
        String nome = txtNomeDisciplina.getText();
        String cargaStr = txtCargaHoraria.getText();

        if (nome.isEmpty() || cargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int carga;
        try {
            carga = Integer.parseInt(cargaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Carga horária deve ser um número inteiro.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Disciplina disciplina = new Disciplina();
        disciplina.setNomeDisciplina(nome);
        disciplina.setCargaHoraria(carga);

        disciplina_dao dao = new disciplina_dao();
        dao.inserir(disciplina);

        JOptionPane.showMessageDialog(this,
                "Disciplina salva com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        limparCampos();
    }

    private void listarDisciplinas() {
        disciplina_dao dao = new disciplina_dao();
        List<Disciplina> lista = dao.listar();

        areaLista.setText("");
        for (Disciplina d : lista) {
            areaLista.append(
                    "ID: " + d.getIdDisciplina()
                            + " | Nome: " + d.getNomeDisciplina()
                            + " | Carga: " + d.getCargaHoraria() + "h"
                            + "\n"
            );
        }
    }

    private void limparCampos() {
        txtNomeDisciplina.setText("");
        txtCargaHoraria.setText("");
        txtNomeDisciplina.requestFocus();
    }
}
