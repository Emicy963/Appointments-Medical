package controller;

import model.Usuario;
import java.sql.*;
import javax.swing.JOptionPane;

public class UsuarioController {
    private static Usuario usuarioLogado = null;
    
    public boolean fazerLogin(String email, String senha) {
        String senhaEncriptada = AuthenticationController.encryptPassword(senha);
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, senhaEncriptada);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuarioLogado = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getBoolean("admin"),
                        rs.getBytes("foto")
                    );
                    return true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao fazer login: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public void logout() {
        usuarioLogado = null;
    }
    
    public Usuario cadastrarUsuario(String nome, String email, String senha, boolean admin) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        String senhaEncriptada = AuthenticationController.encryptPassword(senha);
        String sql = "INSERT INTO usuarios (nome, email, senha, admin) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senhaEncriptada);
            stmt.setBoolean(4, admin);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar usuário, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Usuario(
                        generatedKeys.getInt(1),
                        nome,
                        email,
                        senhaEncriptada,
                        admin,
                        null
                    );
                } else {
                    throw new SQLException("Falha ao criar usuário, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new IllegalArgumentException("Email já cadastrado");
            }
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
    
    public void atualizarFoto(int usuarioId, byte[] foto) {
        String sql = "UPDATE usuarios SET foto = ? WHERE id = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBytes(1, foto);
            stmt.setInt(2, usuarioId);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar foto: " + e.getMessage());
        }
    }
}