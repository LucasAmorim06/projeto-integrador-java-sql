package DAO;

import model.Disciplina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class disciplina_dao {

    // Inserir nova disciplina
    public void inserir(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (Nome_Disciplina, Carga_Horaria) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, disciplina.getNomeDisciplina());
            stmt.setInt(2, disciplina.getCargaHoraria());

            stmt.executeUpdate();
            System.out.println("✅ Disciplina salva com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar disciplina: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    // Listar disciplinas
    public List<Disciplina> listar() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM Disciplina";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setIdDisciplina(rs.getInt("ID_Disciplina"));
                disciplina.setNomeDisciplina(rs.getString("Nome_Disciplina"));
                disciplina.setCargaHoraria(rs.getInt("Carga_Horaria"));
                disciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar disciplinas: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return disciplinas;
    }

    // Atualizar disciplina
    public void atualizar(Disciplina disciplina) {
        String sql = "UPDATE Disciplina SET Nome_Disciplina=?, Carga_Horaria=? WHERE ID_Disciplina=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, disciplina.getNomeDisciplina());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setInt(3, disciplina.getIdDisciplina());

            stmt.executeUpdate();
            System.out.println("✅ Disciplina atualizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar disciplina: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    // Excluir disciplina
    public void excluir(int idDisciplina) {
        String sql = "DELETE FROM Disciplina WHERE ID_Disciplina=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idDisciplina);

            stmt.executeUpdate();
            System.out.println("✅ Disciplina removida com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao remover disciplina: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
}
