package view;

import DAO.professor_dao;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroProfessorFrame extends JFrame {

    private JTextField txtNome, txtEmail, txtTelefone, txtIdExcluir;
    private JTextArea areaLista;
    private JButton btnSalvar, btnListar, btnLimpar, btnFechar, btnExcluir;

    private final Color COR_TOPO  = new Color(153, 0, 0);
    private final Color COR_BOTAO = new Color(204, 0, 0);
    private final Color COR_FUNDO = new Color(240, 240, 240);

    public CadastroProfessorFrame() {
        setTitle("Cadastro de Professores");
        setSize(800, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
    }

    private void criarComponentes() {
        // TOPO
        JPanel topo = new JPanel();
        topo.setBackground(COR_TOPO);
        JLabel lblTitulo = new JLabel("Cadastro de Professores");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        topo.add(lblTitulo);

        // FORM
        JPanel form = new JPanel();
        form.setBackground(COR_FUNDO);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        form.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblNome  = new JLabel("Nome do professor:");
        JLabel lblEmail = new JLabel("E-mail:");
        JLabel lblTel   = new JLabel("Telefone móvel:");

        txtNome     = new JTextField();
        txtEmail    = new JTextField();
        txtTelefone = new JTextField();

        form.add(lblNome);  form.add(txtNome);
        form.add(lblEmail); form.add(txtEmail);
        form.add(lblTel);   form.add(txtTelefone);

        // LISTA
        areaLista = new JTextArea(12, 35);
        areaLista.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLista);

        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBackground(COR_FUNDO);
        painelLista.setBorder(BorderFactory.createTitledBorder("Professores cadastrados"));
        painelLista.add(scroll, BorderLayout.CENTER);

        // BOTÕES
        JPanel botoes = new JPanel();
        botoes.setBackground(COR_FUNDO);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        btnListar  = new JButton("Listar Professores");
        btnLimpar  = new JButton("Limpar");
        btnFechar  = new JButton("Fechar");
        btnSalvar  = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");

        txtIdExcluir = new JTextField(5);
        JLabel lblExcluir = new JLabel("ID para excluir:");

        estilizarBotaoSecundario(btnListar);
        estilizarBotaoSecundario(btnLimpar);
        estilizarBotaoSecundario(btnFechar);
        estilizarBotaoSecundario(btnExcluir);
        estilizarBotaoPrincipal(btnSalvar);

        botoes.add(btnListar);
        botoes.add(btnLimpar);
        botoes.add(btnFechar);
        botoes.add(lblExcluir);
        botoes.add(txtIdExcluir);
        botoes.add(btnExcluir);
        botoes.add(btnSalvar);

        // AÇÕES
        btnSalvar.addActionListener(e -> salvar());
        btnListar.addActionListener(e -> listar());
        btnLimpar.addActionListener(e -> limpar());
        btnFechar.addActionListener(e -> dispose());
        btnExcluir.addActionListener(e -> excluir());

        // LAYOUT
        setLayout(new BorderLayout());
        add(topo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(COR_FUNDO);
        centro.add(form, BorderLayout.WEST);
        centro.add(painelLista, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    private void estilizarBotaoPrincipal(JButton b) {
        b.setBackground(COR_BOTAO);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
    }

    private void estilizarBotaoSecundario(JButton b) {
        b.setBackground(new Color(230, 230, 230));
        b.setForeground(Color.DARK_GRAY);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    private void salvar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String tel = txtTelefone.getText();

        if (nome.isEmpty() || email.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        Professor p = new Professor();
        p.setNomeProfessor(nome);
        p.setEmail(email);
        p.setTelefoneMovel(tel);

        professor_dao dao = new professor_dao();
        dao.inserir(p);

        JOptionPane.showMessageDialog(this, "Professor salvo com sucesso!");
        limpar();
        listar();
    }

    private void listar() {
        professor_dao dao = new professor_dao();
        List<Professor> lista = dao.listar();

        areaLista.setText("");
        for (Professor p : lista) {
            areaLista.append(
                    "ID: " + p.getIdProfessor() +
                    " | Nome: " + p.getNomeProfessor() +
                    " | E-mail: " + p.getEmail() +
                    " | Telefone: " + p.getTelefoneMovel() +
                    "\n"
            );
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtIdExcluir.setText("");
        txtNome.requestFocus();
    }

    private void excluir() {
        String idStr = txtIdExcluir.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do professor para excluir.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o professor ID " + id + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (resp == JOptionPane.YES_OPTION) {
            professor_dao dao = new professor_dao();
            dao.excluir(id);
            listar();
        }
    }
}
