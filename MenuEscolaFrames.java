package view;

import DAO.Turma_DAO;

import javax.swing.*;
import java.awt.*;

public class MenuEscolaFrames extends JFrame {

    private JButton btnAlunos;
    private JButton btnProfessores;
    private JButton btnTurmas;
    private JButton btnDisciplinas;
    private JButton btnEnderecos; // agora será Listar Endereços
    private JButton btnSair;

    // Cores Antão Ferreira
    private final Color COR_TOPO  = new Color(153, 0, 0);     // vermelho escuro
    private final Color COR_BOTAO = new Color(204, 0, 0);     // vermelho
    private final Color COR_FUNDO = new Color(240, 240, 240); // cinza claro

    public MenuEscolaFrames() {
        setTitle("Sistema Escolar - Menu Principal");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        criarComponentes();
    }

    private void criarComponentes() {
        // TOPO
        JPanel pnlTopo = new JPanel();
        pnlTopo.setBackground(COR_TOPO);
        pnlTopo.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/logoantao.jpeg"));
            Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Erro ao carregar a logo: " + e.getMessage());
        }

        JLabel lblTitulo = new JLabel("Sistema Escolar – Colégio Antão Ferreira");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        pnlTopo.add(lblLogo);
        pnlTopo.add(lblTitulo);

        // CENTRO
        JPanel pnlCentro = new JPanel();
        pnlCentro.setBackground(COR_FUNDO);
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlCentro.setLayout(new GridLayout(3, 2, 20, 20));

        btnAlunos      = criarBotaoMenu("Cadastro de Alunos");
        btnProfessores = criarBotaoMenu("Cadastro de Professores");
        btnTurmas      = criarBotaoMenu("Cadastro de Turmas");
        btnDisciplinas = criarBotaoMenu("Cadastro de Disciplinas");
        btnEnderecos   = criarBotaoMenu("Listar Endereços");
        btnSair        = criarBotaoMenu("Sair do Sistema");

        pnlCentro.add(btnAlunos);
        pnlCentro.add(btnProfessores);
        pnlCentro.add(btnTurmas);
        pnlCentro.add(btnDisciplinas);
        pnlCentro.add(btnEnderecos);
        pnlCentro.add(btnSair);

        // AÇÕES
        btnAlunos.addActionListener(e -> abrirCadastroAluno());
        btnProfessores.addActionListener(e -> new CadastroProfessorFrame().setVisible(true));
        btnTurmas.addActionListener(e -> new CadastroTurmaFrame().setVisible(true));
        btnDisciplinas.addActionListener(e -> new CadastroDisciplinaFrame().setVisible(true));
        btnEnderecos.addActionListener(e -> new ListaEnderecosFrame().setVisible(true));
        btnSair.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(pnlTopo, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(COR_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.BOLD, 16));
        return botao;
    }

    private void abrirCadastroAluno() {
        Turma_DAO turmaDAO = new Turma_DAO();

        if (turmaDAO.listar().isEmpty()) {
            int resp = JOptionPane.showConfirmDialog(
                    this,
                    "Não há turmas cadastradas.\n" +
                    "Deseja cadastrar uma turma agora?",
                    "Atenção",
                    JOptionPane.YES_NO_OPTION
            );

            if (resp == JOptionPane.YES_OPTION) {
                new CadastroTurmaFrame().setVisible(true);
            } else {
                new CadastroAlunoFrame().setVisible(true);
            }
        } else {
            new CadastroAlunoFrame().setVisible(true);
        }
    }
}
