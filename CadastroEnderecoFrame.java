package view;

import DAO.Endereco_DAO;
import model.Endereco;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroEnderecoFrame extends JFrame {

    private JTextField txtRua, txtBairro, txtCep, txtIdExcluir;
    private JTextArea areaLista;
    private JButton btnSalvar, btnListar, btnLimpar, btnFechar, btnExcluir;

    private final Color COR_TOPO  = new Color(153, 0, 0);
    private final Color COR_BOTAO = new Color(204, 0, 0);
    private final Color COR_FUNDO = new Color(240, 240, 240);

    public CadastroEnderecoFrame() {
        setTitle("Cadastro de Endereços");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
    }

    private void criarComponentes() {
        JPanel topo = new JPanel();
        topo.setBackground(COR_TOPO);
        JLabel lblTitulo = new JLabel("Cadastro de Endereços");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        topo.add(lblTitulo);

        JPanel form = new JPanel();
        form.setBackground(COR_FUNDO);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        form.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblRua   = new JLabel("Rua:");
        JLabel lblBairro= new JLabel("Bairro:");
        JLabel lblCep   = new JLabel("CEP:");

        txtRua    = new JTextField();
        txtBairro = new JTextField();
        txtCep    = new JTextField();

        form.add(lblRua);   form.add(txtRua);
        form.add(lblBairro);form.add(txtBairro);
        form.add(lblCep);   form.add(txtCep);

        areaLista = new JTextArea(12, 35);
        areaLista.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaLista);

        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBackground(COR_FUNDO);
        painelLista.setBorder(BorderFactory.createTitledBorder("Endereços cadastrados"));
        painelLista.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.setBackground(COR_FUNDO);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        btnListar  = new JButton("Listar Endereços");
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
        String rua    = txtRua.getText();
        String bairro = txtBairro.getText();
        String cep    = txtCep.getText();

        if (rua.isEmpty() || bairro.isEmpty() || cep.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha rua, bairro e CEP.");
            return;
        }

        Endereco e = new Endereco();
        e.setRua(rua);
        e.setBairro(bairro);
        e.setCep(cep);

        Endereco_DAO dao = new Endereco_DAO();
        dao.inserir(e);

        JOptionPane.showMessageDialog(this, "Endereço salvo com sucesso!");
        limpar();
        listar();
    }

    private void listar() {
        Endereco_DAO dao = new Endereco_DAO();
        List<Endereco> lista = dao.listar();

        areaLista.setText("");
        for (Endereco e : lista) {
            areaLista.append(
                    "ID: " + e.getIdEndereco() +
                    " | Rua: " + e.getRua() +
                    " | Bairro: " + e.getBairro() +
                    " | CEP: " + e.getCep() +
                    "\n"
            );
        }
    }

    private void limpar() {
        txtRua.setText("");
        txtBairro.setText("");
        txtCep.setText("");
        txtIdExcluir.setText("");
        txtRua.requestFocus();
    }

    private void excluir() {
        String idStr = txtIdExcluir.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do endereço para excluir.");
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
                "Tem certeza que deseja excluir o endereço ID " + id + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (resp == JOptionPane.YES_OPTION) {
            Endereco_DAO dao = new Endereco_DAO();
            dao.excluir(id);
            listar();
        }
    }
}
