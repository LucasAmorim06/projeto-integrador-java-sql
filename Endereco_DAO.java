package DAO;

import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Endereco_DAO {

    // Inserir endereço e retornar o ID gerado (usado no CadastroAlunoFrame)
    public int inserirRetornandoId(Endereco e) {
        String sql = "INSERT INTO Endereco (Rua, Bairro, CEP) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idGerado = -1;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, e.getRua());
            stmt.setString(2, e.getBairro());
            stmt.setString(3, e.getCep());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }

        } catch (SQLException ex) {
            System.err.println("❌ Erro ao inserir endereço: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return idGerado;
    }

    // Inserir simples (usado no CadastroEnderecoFrame)
    public void inserir(Endereco e) {
        String sql = "INSERT INTO Endereco (Rua, Bairro, CEP) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, e.getRua());
            stmt.setString(2, e.getBairro());
            stmt.setString(3, e.getCep());

            stmt.executeUpdate();
            System.out.println("✅ Endereço salvo com sucesso!");

        } catch (SQLException ex) {
            System.err.println("❌ Erro ao salvar endereço: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    // Listar todos os endereços
    public List<Endereco> listar() {
        List<Endereco> lista = new ArrayList<>();
        String sql = "SELECT * FROM Endereco";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Endereco e = new Endereco();
                e.setIdEndereco(rs.getInt("ID_Endereco"));
                e.setRua(rs.getString("Rua"));
                e.setBairro(rs.getString("Bairro"));
                e.setCep(rs.getString("CEP"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.err.println("❌ Erro ao listar endereços: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return lista;
    }

    // Excluir por ID
    public void excluir(int idEndereco) {
        String sql = "DELETE FROM Endereco WHERE ID_Endereco = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEndereco);
            stmt.executeUpdate();
            System.out.println("✅ Endereço excluído com sucesso!");

        } catch (SQLException ex) {
            System.err.println("❌ Erro ao excluir endereço: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
}
