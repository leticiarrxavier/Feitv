package controller;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.ArrayList;

public class UsuarioController {

    private final UsuarioDAO dao = new UsuarioDAO();

    public boolean cadastrar(String nome, String email, String senha) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) return false;
        return dao.cadastrar(nome, email, senha);
    }

    public Usuario login(String email, String senha) {
        return dao.login(email, senha);
    }

    public ArrayList<Usuario> listarTodos() {
        return dao.listarTodos();
    }

    public int contarUsuarios() {
        return dao.contarUsuarios();
    }
}
