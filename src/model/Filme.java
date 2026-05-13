package model;

public class Filme extends Video {

    public Filme(int id, String titulo, String genero, int ano, String descricao, int curtidas) {
        super(id, titulo, genero, ano, descricao, curtidas);
    }

    @Override
    public String getTipo() {
        return "Filme";
    }
}
