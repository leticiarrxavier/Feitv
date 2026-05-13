package controller;

import dao.ListaDAO;
import model.ListaReproducao;
import model.Video;
import java.util.ArrayList;

public class ListaController {

    private ListaDAO dao = new ListaDAO();

    public boolean criar(String nome, int usuarioId) {
        if (nome.isEmpty()) return false;
        return dao.criar(nome, usuarioId);
    }

    public boolean editar(int listaId, String novoNome) {
        if (novoNome.isEmpty()) return false;
        return dao.editar(listaId, novoNome);
    }

    public boolean excluir(int listaId) {
        return dao.excluir(listaId);
    }

    public ArrayList<ListaReproducao> listarPorUsuario(int usuarioId) {
        return dao.listarPorUsuario(usuarioId);
    }

    public void adicionarVideo(int listaId, int videoId) {
        dao.adicionarVideo(listaId, videoId);
    }

    public void removerVideo(int listaId, int videoId) {
        dao.removerVideo(listaId, videoId);
    }

    public ArrayList<Video> listarVideos(int listaId) {
        return dao.listarVideos(listaId);
    }
}
