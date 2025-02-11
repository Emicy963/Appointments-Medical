package controller;

import model.Appointment;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {
    
    public Appointment marcarConsulta(int usuarioId, int profissionalId, 
                                    LocalDateTime dataHora, 
                                    String tipoConsulta, String observacoes) {
        String sql = "INSERT INTO consultas (usuario_id, profissional_id, data_hora, tipo_consulta, observacao) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, profissionalId);
            stmt.setTimestamp(3, Timestamp.valueOf(dataHora));
            stmt.setString(4, tipoConsulta);
            stmt.setString(5, observacoes);
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Appointment(
                        generatedKeys.getInt(1),
                        usuarioId,
                        profissionalId,
                        dataHora,
                        tipoConsulta,
                        observacoes
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar consulta: " + e.getMessage());
        }
        return null;
    }
    
    public List<Appointment> listarConsultas(int usuarioId) {
        List<Appointment> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE usuario_id = ? ORDER BY data_hora";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("profissional_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getString("tipo_consulta"),
                        rs.getString("observacao")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage());
        }
        return consultas;
    }
    
    public List<Appointment> pesquisarConsultas(int usuarioId, String termo) {
        List<Appointment> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE usuario_id = ? AND " +
                    "(tipo_consulta LIKE ? OR observacao LIKE ? OR " +
                    "DATE_FORMAT(data_hora, '%d/%m/%Y') LIKE ?)";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String termoPesquisa = "%" + termo + "%";
            stmt.setInt(1, usuarioId);
            stmt.setString(2, termoPesquisa);
            stmt.setString(3, termoPesquisa);
            stmt.setString(4, termoPesquisa);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("profissional_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getString("tipo_consulta"),
                        rs.getString("observacao")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar consultas: " + e.getMessage());
        }
        return consultas;
    }
    
    public Appointment editarConsulta(int consultaId, 
                                    LocalDateTime novaDataHora, 
                                    String novoTipoConsulta, 
                                    String novasObservacoes) {
        String sql = "UPDATE consultas SET data_hora = ?, tipo_consulta = ?, observacao = ? " +
                    "WHERE id = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(novaDataHora));
            stmt.setString(2, novoTipoConsulta);
            stmt.setString(3, novasObservacoes);
            stmt.setInt(4, consultaId);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Consulta não encontrada");
            }
            
            // Retorna a consulta atualizada
            sql = "SELECT * FROM consultas WHERE id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(sql)) {
                selectStmt.setInt(1, consultaId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        return new Appointment(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getInt("profissional_id"),
                            rs.getTimestamp("data_hora").toLocalDateTime(),
                            rs.getString("tipo_consulta"),
                            rs.getString("observacao")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar consulta: " + e.getMessage());
        }
        return null;
    }
    
    public boolean excluirConsulta(int consultaId) {
        String sql = "DELETE FROM consultas WHERE id = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, consultaId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir consulta: " + e.getMessage());
        }
    }
}