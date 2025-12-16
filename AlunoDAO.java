package DAO;

import model.Aluno;
import model.Endereco;
import model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // INSERIR (já com ID_Endereco e ID_Turma)
    public void inserir(Aluno aluno) {
        String sql = "INSERT INTO Aluno (Nome_Aluno, Serie, Data_Nascimento, ID_Endereco, ID_Turma) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, aluno.getNomeAluno());
            stmt.setString(2, aluno.getSerie());
            stmt.setDate(3, new java.sql.Date(aluno.getDataNascimento().getTime()));

            if (aluno.getEndereco() != null) {
                stmt.setInt(4, aluno.getEndereco().getIdEndereco());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            if (aluno.getTurma() != null) {
                stmt.setInt(5, aluno.getTurma().getIdTurma());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("✅ Aluno salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar aluno: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    // LISTAR (JOIN com Endereco + Turma)
    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();

        String sql =
                "SELECT a.ID_Aluno, a.Nome_Aluno, a.Serie, a.Data_Nascimento, " +
                "e.ID_Endereco, e.Rua, e.Bairro, e.CEP, " +
                "t.ID_Turma, t.Nome_Turma " +
                "FROM Aluno a " +
                "LEFT JOIN Endereco e ON a.ID_Endereco = e.ID_Endereco " +
                "LEFT JOIN Turma t ON a.ID_Turma = t.ID_Turma";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("ID_Aluno"));
                aluno.setNomeAluno(rs.getString("Nome_Aluno"));
                aluno.setSerie(rs.getString("Serie"));
                aluno.setDataNascimento(rs.getDate("Data_Nascimento"));

                int idEnd = rs.getInt("ID_Endereco");
                if (!rs.wasNull()) {
                    Endereco endereco = new Endereco();
                    endereco.setIdEndereco(idEnd);
                    endereco.setRua(rs.getString("Rua"));
                    endereco.setBairro(rs.getString("Bairro"));
                    endereco.setCep(rs.getString("CEP"));
                    aluno.setEndereco(endereco);
                }

                int idTurma = rs.getInt("ID_Turma");
                if (!rs.wasNull()) {
                    Turma turma = new Turma();
                    turma.setIdTurma(idTurma);
                    turma.setNomeTurma(rs.getString("Nome_Turma"));
                    aluno.setTurma(turma);
                }

                alunos.add(aluno);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar alunos: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return alunos;
    }

    // 🔴 EXCLUIR ALUNO + ENDEREÇO LIGADO
    public void excluir(int idAluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();

            // 1) Descobrir o ID_Endereco do aluno
            String sqlBusca = "SELECT ID_Endereco FROM Aluno WHERE ID_Aluno = ?";
            stmt = conn.prepareStatement(sqlBusca);
            stmt.setInt(1, idAluno);
            rs = stmt.executeQuery();

            Integer idEndereco = null;
            if (rs.next()) {
                idEndereco = rs.getInt("ID_Endereco");
                if (rs.wasNull()) {
                    idEndereco = null;
                }
            }

            rs.close();
            stmt.close();

            // 2) Apagar o aluno
            String sqlDelAluno = "DELETE FROM Aluno WHERE ID_Aluno = ?";
            stmt = conn.prepareStatement(sqlDelAluno);
            stmt.setInt(1, idAluno);
            stmt.executeUpdate();
            stmt.close();

            // 3) Se tinha endereço, apagar o endereço também
            if (idEndereco != null && idEndereco > 0) {
                String sqlDelEnd = "DELETE FROM Endereco WHERE ID_Endereco = ?";
                stmt = conn.prepareStatement(sqlDelEnd);
                stmt.setInt(1, idEndereco);
                stmt.executeUpdate();
            }

            System.out.println("✅ Aluno e endereço excluídos com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir aluno/endereço: " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }
    }
}
