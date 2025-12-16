package view;

import DAO.Endereco_DAO;
import model.Endereco;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListaEnderecosFrame extends JFrame {

    private JTextArea areaLista;

    // mesmas cores
    private final Color COR_TOPO  = new Color(153, 0, 0);
    private final Color COR_FUNDO = new Color(240, 240, 240);

    public ListaEnderecosFrame() {
        setTitle("Endereços cadastrados");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criarComponentes();
        listarEnderecos();
    }

    private void criarComponentes() {
        JPanel topo = new JPanel();
        topo.setBackground(COR_TOPO);
        JLabel lbl = new JLabel("Endereços cadastrados");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        topo.add(lbl);

        areaLista = new JTextArea(15, 40);
        areaLista.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaLista);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(COR_FUNDO);
        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centro.add(scroll, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(topo, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

    private void listarEnderecos() {
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
}
