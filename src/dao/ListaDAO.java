package dao;

import model.*;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;

public class ListaDAO {

    // Cria uma nova lista de reprodução
    public boolean criar(String nome, int usuarioId) {
        String sql = "INSERT INTO lista_reproducao (nome, usuario_id) VALUES (?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, usuarioId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {return false; }
    }

    // Renomeia uma lista
    public boolean editar(int listaId, String novoNome) {
        String sql = "UPDATE lista_reproducao SET nome=? WHERE id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, novoNome);
            ps.setInt(2, listaId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {return false; }
    }

    // Exclui uma lista (e seus vínculos, graças ao CASCADE)
    public boolean excluir(int listaId) {
        String sql = "DELETE FROM lista_reproducao WHERE id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, listaId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {return false; }
    }

    // Retorna todas as listas de um usuário
    public ArrayList<ListaReproducao> listarPorUsuario(int usuarioId) {
        ArrayList<ListaReproducao> lista = new ArrayList<>();
        String sql = "SELECT * FROM lista_reproducao WHERE usuario_id=? ORDER BY nome";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ListaReproducao(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    usuarioId
                ));
            }
        } catch (Exception e) {}
        return lista;
    }

    // Adiciona vídeo à lista (ignora se já existir)
    public void adicionarVideo(int listaId, int videoId) {
        String sql = "INSERT INTO lista_video (lista_id, video_id) VALUES (?,?) ON CONFLICT DO NOTHING";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, listaId);
            ps.setInt(2, videoId);
            ps.executeUpdate();
        } catch (Exception e) {}
    }

    // Remove vídeo da lista
    public void removerVideo(int listaId, int videoId) {
        String sql = "DELETE FROM lista_video WHERE lista_id=? AND video_id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, listaId);
            ps.setInt(2, videoId);
            ps.executeUpdate();
        } catch (Exception e) {}
    }

    // Lista os vídeos de uma lista de reprodução
    public ArrayList<Video> listarVideos(int listaId) {
        ArrayList<Video> lista = new ArrayList<>();
        String sql = "SELECT v.* FROM video v " +
                     "JOIN lista_video lv ON v.id = lv.video_id " +
                     "WHERE lv.lista_id=? ORDER BY v.titulo";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, listaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Video v;
                if ("Filme".equals(tipo)) {
                    v = new Filme(rs.getInt("id"), rs.getString("titulo"),
                                  rs.getString("genero"), rs.getInt("ano"),
                                  rs.getString("descricao"), rs.getInt("curtidas"));
                } else {
                    v = new Serie(rs.getInt("id"), rs.getString("titulo"),
                                  rs.getString("genero"), rs.getInt("ano"),
                                  rs.getString("descricao"), rs.getInt("curtidas"));
                }
                lista.add(v);
            }
        } catch (Exception e) {}
        return lista;
    }
}
