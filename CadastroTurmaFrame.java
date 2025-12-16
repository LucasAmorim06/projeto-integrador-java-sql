package view;

import DAO.Turma_DAO;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroTurmaFrame extends JFrame {

    private JTextField txtNome, txtCarga, txtIdExcluir;
    private JTextArea areaLista;
    private JButton btnSalvar, btnListar, btnLimpar, btnFechar, btnExcluir;

    private final Color COR_TOPO  = new Color(153, 0, 0);
    private final Color COR_BOTAO = new Color(204, 0, 0);
    private final Color COR_FUNDO = new Color(240, 240, 240);

    public CadastroTurmaFrame() {
        setTitle("Cadastro de Turmas");
        setSize(800, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
    }

    private void criarComponentes() {
        JPanel topo = new JPanel();
        topo.setBackground(COR_TOPO);
        JLabel lblTitulo = new JLabel("Cadastro de Turmas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        topo.add(lblTitulo);

        JPanel form = new JPanel();
        form.setBackground(COR_FUNDO);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        form.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel lblNome  = new JLabel("Nome da turma:");
        JLabel lblCarga = new JLabel("Carga horária (horas):");

        txtNome  = new JTextField();
        txtCarga = new JTextField();

        form.add(lblNome);  form.add(txtNome);
        form.add(lblCarga); form.add(txtCarga);

        areaLista = new JTextArea(12, 35);
        areaLista.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLista);

        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBackground(COR_FUNDO);
        painelLista.setBorder(BorderFactory.createTitledBorder("Turmas cadastradas"));
        painelLista.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.setBackground(COR_FUNDO);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        btnListar  = new JButton("Listar Turmas");
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
        String cargaStr = txtCarga.getText();

        if (nome.isEmpty() || cargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha nome e carga horária.");
            return;
        }

        int carga;
        try {
            carga = Integer.parseInt(cargaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Carga horária inválida.");
            return;
        }

        Turma t = new Turma();
        t.setNomeTurma(nome);
        t.setCargaHoraria(carga);

        Turma_DAO dao = new Turma_DAO();
        dao.inserir(t);

        JOptionPane.showMessageDialog(this, "Turma salva com sucesso!");
        limpar();
        listar();
    }

    private void listar() {
        Turma_DAO dao = new Turma_DAO();
        List<Turma> lista = dao.listar();

        areaLista.setText("");
        for (Turma t : lista) {
            areaLista.append(
                    "ID: " + t.getIdTurma() +
                    " | Nome: " + t.getNomeTurma() +
                    " | Carga horária: " + t.getCargaHoraria() + "h" +
                    "\n"
            );
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtCarga.setText("");
        txtIdExcluir.setText("");
        txtNome.requestFocus();
    }

    private void excluir() {
        String idStr = txtIdExcluir.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID da turma para excluir.");
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
                "Tem certeza que deseja excluir a turma ID " + id + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );
        if (resp == JOptionPane.YES_OPTION) {
            Turma_DAO dao = new Turma_DAO();
            dao.excluir(id);
            listar();
        }
    }
}
