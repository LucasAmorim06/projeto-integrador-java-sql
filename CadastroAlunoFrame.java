package view;

import DAO.AlunoDAO;
import DAO.Endereco_DAO;
import DAO.Turma_DAO;
import model.Aluno;
import model.Endereco;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CadastroAlunoFrame extends JFrame {

    private JTextField txtNome;
    private JTextField txtSerie;
    private JTextField txtDataNascimento;
    private JTextField txtEndereco;
    private JTextField txtTurma;
    private JTextArea areaLista;

    private JTextField txtIdExcluir;
    private JButton btnSalvar, btnListar, btnLimpar, btnFechar, btnExcluir;

    private final Color COR_TOPO  = new Color(153, 0, 0);
    private final Color COR_BOTAO = new Color(204, 0, 0);
    private final Color COR_FUNDO = new Color(240, 240, 240);

    public CadastroAlunoFrame() {
        setTitle("Cadastro de Alunos");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
    }

    private void criarComponentes() {
        // TOPO
        JPanel topo = new JPanel();
        topo.setBackground(COR_TOPO);
        JLabel lblTitulo = new JLabel("Cadastro de Alunos");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        topo.add(lblTitulo);

        // FORM
        JPanel form = new JPanel();
        form.setBackground(COR_FUNDO);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        form.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblNome  = new JLabel("Nome do aluno:");
        JLabel lblSerie = new JLabel("Série:");
        JLabel lblData  = new JLabel("Data de Nascimento (dd/MM/yyyy):");
        JLabel lblEnd   = new JLabel("Endereço:");
        JLabel lblTurma = new JLabel("Turma:");

        txtNome           = new JTextField();
        txtSerie          = new JTextField();
        txtDataNascimento = new JTextField();
        txtEndereco       = new JTextField();
        txtTurma          = new JTextField();

        form.add(lblNome);  form.add(txtNome);
        form.add(lblSerie); form.add(txtSerie);
        form.add(lblData);  form.add(txtDataNascimento);
        form.add(lblEnd);   form.add(txtEndereco);
        form.add(lblTurma); form.add(txtTurma);

        // LISTA
        areaLista = new JTextArea(12, 40);
        areaLista.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLista);

        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBackground(COR_FUNDO);
        painelLista.setBorder(BorderFactory.createTitledBorder("Alunos cadastrados"));
        painelLista.add(scroll, BorderLayout.CENTER);

        // BOTÕES
        JPanel botoes = new JPanel();
        botoes.setBackground(COR_FUNDO);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        btnListar  = new JButton("Listar Alunos");
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
        btnSalvar.addActionListener(e -> salvarAluno());
        btnListar.addActionListener(e -> listarAlunos());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> dispose());
        btnExcluir.addActionListener(e -> excluirAluno());

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

    // ---------- SALVAR ----------
    private void salvarAluno() {
        String nome   = txtNome.getText();
        String serie  = txtSerie.getText();
        String dataStr= txtDataNascimento.getText();
        String end    = txtEndereco.getText();
        String turma  = txtTurma.getText();

        if (nome.isEmpty() || serie.isEmpty() || dataStr.isEmpty()
                || end.isEmpty() || turma.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha nome, série, data, endereço e turma.");
            return;
        }

        Date dataNasc;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataNasc = sdf.parse(dataStr);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use dd/MM/yyyy.");
            return;
        }

        // 1) Endereço
        Endereco endereco = new Endereco();
        endereco.setRua(end);
        endereco.setBairro("");
        endereco.setCep("");

        Endereco_DAO endDAO = new Endereco_DAO();
        int idEnd = endDAO.inserirRetornandoId(endereco);
        if (idEnd <= 0) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar endereço.");
            return;
        }
        endereco.setIdEndereco(idEnd);

        // 2) Turma (buscar ou criar)
        Turma_DAO turmaDAO = new Turma_DAO();
        int idTurma = turmaDAO.buscarOuCriarPorNome(turma);
        Turma turmaObj = null;
        if (idTurma > 0) {
            turmaObj = new Turma();
            turmaObj.setIdTurma(idTurma);
            turmaObj.setNomeTurma(turma);
        }

        // 3) Aluno
        Aluno aluno = new Aluno();
        aluno.setNomeAluno(nome);
        aluno.setSerie(serie);
        aluno.setDataNascimento(dataNasc);
        aluno.setEndereco(endereco);
        aluno.setTurma(turmaObj);

        AlunoDAO dao = new AlunoDAO();
        dao.inserir(aluno);

        JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
        limparCampos();
        listarAlunos();
    }

    // ---------- LISTAR ----------
    private void listarAlunos() {
        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.listar();

        areaLista.setText("");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Aluno a : lista) {
            String data = (a.getDataNascimento() != null)
                    ? sdf.format(a.getDataNascimento())
                    : "";

            String end = (a.getEndereco() != null) ? a.getEndereco().getRua() : "";
            String turma = (a.getTurma() != null) ? a.getTurma().getNomeTurma() : "";

            areaLista.append(
                    "ID: " + a.getIdAluno() +
                    " | Nome: " + a.getNomeAluno() +
                    " | Série: " + a.getSerie() +
                    " | Data Nasc.: " + data +
                    " | Endereço: " + end +
                    " | Turma: " + turma +
                    "\n"
            );
        }
    }

    // ---------- LIMPAR ----------
    private void limparCampos() {
        txtNome.setText("");
        txtSerie.setText("");
        txtDataNascimento.setText("");
        txtEndereco.setText("");
        txtTurma.setText("");
        txtIdExcluir.setText("");
        txtNome.requestFocus();
    }

    // ---------- EXCLUIR ----------
    private void excluirAluno() {
        String idStr = txtIdExcluir.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do aluno para excluir.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o aluno ID " + id + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (resp == JOptionPane.YES_OPTION) {
            AlunoDAO dao = new AlunoDAO();
            dao.excluir(id);
            listarAlunos();
        }
    }
}
