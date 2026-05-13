package model;

import java.util.ArrayList;

public class ListaReproducao {

    private final int id;
    private String nome;
    private final int usuarioId;
    private ArrayList<Video> videos;

    public ListaReproducao(int id, String nome, int usuarioId) {
        this.id        = id;
        this.nome      = nome;
        this.usuarioId = usuarioId;
        this.videos    = new ArrayList<>();
    }

    public int    getId()        { return id; }
    public String getNome()      { return nome; }
    public void   setNome(String nome) { this.nome = nome; }
    public int    getUsuarioId() { return usuarioId; }

    public ArrayList<Video> getVideos()        { return videos; }
    public void setVideos(ArrayList<Video> v)  { this.videos = v; }

    @Override
    public String toString() { return nome; }
}
