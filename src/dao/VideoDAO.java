package dao;

import model.*;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;

public class VideoDAO {

    // Cria o objeto certo (Filme ou Serie) a partir do ResultSet
    private Video criarVideo(ResultSet rs) throws SQLException {
        int    id       = rs.getInt("id");
        String titulo   = rs.getString("titulo");
        String tipo     = rs.getString("tipo");
        String genero   = rs.getString("genero");
        int    ano      = rs.getInt("ano");
        String desc     = rs.getString("descricao");
        int    curtidas = rs.getInt("curtidas");

        if ("Filme".equals(tipo)) {
            return new Filme(id, titulo, genero, ano, desc, curtidas);
        } else {
            return new Serie(id, titulo, genero, ano, desc, curtidas);
        }
    }

    // Lista todos os vídeos
    public ArrayList<Video> listarTodos() {
        ArrayList<Video> lista = new ArrayList<>();
        String sql = "SELECT * FROM video ORDER BY titulo";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(criarVideo(rs));
        } catch (Exception e) {}
        return lista;
    }

    // Busca por nome (LIKE)
    public ArrayList<Video> buscarPorNome(String nome) {
        ArrayList<Video> lista = new ArrayList<>();
        String sql = "SELECT * FROM video WHERE LOWER(titulo) LIKE LOWER(?) ORDER BY titulo";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(criarVideo(rs));
        } catch (Exception e) {}
        return lista;
    }

    // Cadastra vídeo (admin)
    public boolean cadastrar(String titulo, String tipo, String genero, int ano, String descricao) {
        String sql = "INSERT INTO video (titulo, tipo, genero, ano, descricao) VALUES (?,?,?,?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, tipo);
            ps.setString(3, genero);
            ps.setInt(4, ano);
            ps.setString(5, descricao);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {return false; }
    }

    // Exclui vídeo (admin)
    public boolean excluir(int id) {
        String sql = "DELETE FROM video WHERE id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {return false; }
    }

    // Curtir: adiciona +1 E registra o usuário na tabela curtida
    // Retorna true se curtiu, false se já tinha curtido (para descurtir)
    public boolean curtir(int usuarioId, int videoId) {
        // Verifica se já curtiu
        String check = "SELECT 1 FROM curtida WHERE usuario_id=? AND video_id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(check)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, videoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Já curtiu -> descurtir
                descurtir(usuarioId, videoId);
                return false;
            }
        } catch (Exception e) {}

        // Ainda não curtiu -> curtir
        String ins = "INSERT INTO curtida (usuario_id, video_id) VALUES (?,?)";
        String upd = "UPDATE video SET curtidas = curtidas + 1 WHERE id=?";
        try (Connection c = Conexao.conectar()) {
            PreparedStatement ps1 = c.prepareStatement(ins);
            ps1.setInt(1, usuarioId); ps1.setInt(2, videoId); ps1.executeUpdate();
            PreparedStatement ps2 = c.prepareStatement(upd);
            ps2.setInt(1, videoId); ps2.executeUpdate();
        } catch (Exception e) {}
        return true;
    }

    // Descurtir
    public void descurtir(int usuarioId, int videoId) {
        String del = "DELETE FROM curtida WHERE usuario_id=? AND video_id=?";
        String upd = "UPDATE video SET curtidas = GREATEST(curtidas - 1, 0) WHERE id=?";
        try (Connection c = Conexao.conectar()) {
            PreparedStatement ps1 = c.prepareStatement(del);
            ps1.setInt(1, usuarioId); ps1.setInt(2, videoId); ps1.executeUpdate();
            PreparedStatement ps2 = c.prepareStatement(upd);
            ps2.setInt(1, videoId); ps2.executeUpdate();
        } catch (Exception e) {}
    }

    // Verifica se usuário curtiu determinado vídeo
    public boolean jaCurtiu(int usuarioId, int videoId) {
        String sql = "SELECT 1 FROM curtida WHERE usuario_id=? AND video_id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId); ps.setInt(2, videoId);
            return ps.executeQuery().next();
        } catch (Exception e) { return false; }
    }

    // Top 5 mais curtidos (para o admin)
    public ArrayList<Video> top5() {
        ArrayList<Video> lista = new ArrayList<>();
        String sql = "SELECT * FROM video ORDER BY curtidas DESC LIMIT 5";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(criarVideo(rs));
        } catch (Exception e) {}
        return lista;
    }

    // Conta total de vídeos
    public int contarVideos() {
        String sql = "SELECT COUNT(*) FROM video";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {}
        return 0;
    }
}
