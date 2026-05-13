package dao;

import model.Usuario;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    // Cadastra um novo usuário; retorna true se ok
    public boolean cadastrar(String nome, String email, String senha) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?,?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false; // email duplicado ou outro erro
        }
    }

    // Faz login; retorna o Usuario ou null se não encontrar
    public Usuario login(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email=? AND senha=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
            }
        } catch (Exception e) {}
        return null;
    }

    // Lista todos os usuários (para o admin)
    public ArrayList<Usuario> listarTodos() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nome";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                ));
            }
        } catch (Exception e) {}
        return lista;
    }

    // Conta total de usuários
    public int contarUsuarios() {
        String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {}
        return 0;
    }
}
