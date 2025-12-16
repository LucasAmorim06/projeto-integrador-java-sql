package DAO;

import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class professor_dao {

    public void inserir(Professor p) {
        String sql = "INSERT INTO Professor (Nome_Professor, Email, Telefone_Movel) " +
                     "VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getNomeProfessor());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getTelefoneMovel());
            stmt.executeUpdate();
            System.out.println("✅ Professor salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar professor: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    public List<Professor> listar() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Professor";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Professor p = new Professor();
                p.setIdProfessor(rs.getInt("ID_Professor"));
                p.setNomeProfessor(rs.getString("Nome_Professor"));
                p.setEmail(rs.getString("Email"));
                p.setTelefoneMovel(rs.getString("Telefone_Movel"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar professores: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return lista;
    }

    public void excluir(int idProfessor) {
        String sql = "DELETE FROM Professor WHERE ID_Professor = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProfessor);
            stmt.executeUpdate();
            System.out.println("✅ Professor excluído com sucesso!");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir professor: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
}
