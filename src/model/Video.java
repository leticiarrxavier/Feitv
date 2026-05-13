package model;


public abstract class Video implements Situacao {

    private final int id;
    private String titulo;
    private final String genero;
    private final int ano;
    private final String descricao;
    private int curtidas;

    public Video(int id, String titulo, String genero, int ano, String descricao, int curtidas) {
        this.id       = id;
        this.titulo   = titulo;
        this.genero   = genero;
        this.ano      = ano;
        this.descricao = descricao;
        this.curtidas = curtidas;
    }

  
    public int    getId()        { return id; }
    public String getTitulo()    { return titulo; }
    public String getGenero()    { return genero; }
    public int    getAno()       { return ano; }
    public String getDescricao() { return descricao; }
    public int    getCurtidas()  { return curtidas; }

   
    public void setTitulo(String titulo)       { this.titulo = titulo; }
    public void setCurtidas(int curtidas)      { this.curtidas = curtidas; }

  
    public abstract String getTipo();

    
    @Override
    public String getSituacao() {
        return curtidas > 0 ? "Curtido " + curtidas + "x" : "Sem curtidas";
    }

    @Override
    public String toString() {
        return "[" + getTipo() + "] " + titulo + " (" + ano + ") - " + curtidas + " curtida(s)";
    }
}
