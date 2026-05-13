package controller;

import dao.VideoDAO;
import model.Video;
import java.util.ArrayList;

public class VideoController {

    private final VideoDAO dao = new VideoDAO();

    public ArrayList<Video> listarTodos() {
        return dao.listarTodos();
    }

    public ArrayList<Video> buscar(String nome) {
        return dao.buscarPorNome(nome);
    }

    // Retorna true se curtiu, false se descurtiu
    public boolean curtirOuDescurtir(int usuarioId, int videoId) {
        return dao.curtir(usuarioId, videoId);
    }

    public boolean jaCurtiu(int usuarioId, int videoId) {
        return dao.jaCurtiu(usuarioId, videoId);
    }

    public boolean cadastrar(String titulo, String tipo, String genero, int ano, String descricao) {
        return dao.cadastrar(titulo, tipo, genero, ano, descricao);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }

    public ArrayList<Video> top5() {
        return dao.top5();
    }

    public int contarVideos() {
        return dao.contarVideos();
    }
}
