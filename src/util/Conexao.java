package util;

import java.sql.*;

public class Conexao {

  
    private static final String URL = "jdbc:postgresql://localhost:5432/feitv?sslmode=disable";
    private static final String USUARIO = "postgres";
    private static final String SENHA   = "123";
  

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
