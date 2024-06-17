package DB;

import java.sql.*;
public class SingletonDB {
    static private Conexao conexao = null;

    private SingletonDB()
    {

    }
    static public boolean conectar()
    {
        conexao = new Conexao();
        return conexao.conectar("jdbc:postgresql://localhost/","fipp","postgres","postgres123");
    }
    static public Conexao getConexao()
    {
        return conexao;
    }
}
