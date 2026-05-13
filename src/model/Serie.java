package model;

public class Serie extends Video {

    public Serie(int id, String titulo, String genero, int ano, String descricao, int curtidas) {
        super(id, titulo, genero, ano, descricao, curtidas);
    }

    @Override
    public String getTipo() {
        return "Serie";
    }
}
