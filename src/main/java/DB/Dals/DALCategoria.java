package DB.Dals;

import DB.SingletonDB;
import DB.entidade.Categoria;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALCategoria implements  IDAL<Categoria>{
    @Override
    public boolean gravar(Categoria entidade) {
        String sql= """
                INSERT INTO categoria(
                	cat_nome)
                	VALUES ('#1')
                """;
        sql = sql.replace("#1",entidade.getNome());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Categoria entidade) {
        String sql= """
                UPDATE categoria
                	SET cat_nome ='#1'
                	WHERE cat_id=#2;
                """;
        sql = sql.replace("#1",entidade.getNome());
        sql = sql.replace("#2",""+entidade.getID());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Categoria entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM categoria WHERE cat_id="+entidade.getID());
    }

    @Override
    public Categoria get(int id) {
        Categoria cat = null;
        String sql = "SELECT cat_id,cat_nome FROM categoria WHERE cat_id="+id;
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
        try{
            if (rs.next()){
                cat = new Categoria(rs.getInt("cat_id"),rs.getString("cat_nome"));
            }
        }catch (Exception e){

        }
        return cat;
    }

    @Override
    public List<Categoria> get(String filtro) {
        List<Categoria> dados = new ArrayList<>();
        String sql = "SELECT cat_id,cat_nome FROM categoria WHERE LOWER(cat_nome) LIKE LOWER('%"+filtro+"%')";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        try{
            while (rs.next()){
                Categoria cat = new Categoria(rs.getInt("cat_id"),rs.getString("cat_nome"));
                dados.add(cat);
            }
        }catch (Exception e){

        }
        return dados;
    }

    @Override
    public List<Categoria> get() {
        List<Categoria> dados = new ArrayList<>();
        String sql = "SELECT cat_id,cat_nome FROM categoria";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        try{
            while (rs.next()){
                Categoria cat = new Categoria(rs.getInt("cat_id"),rs.getString("cat_nome"));
                dados.add(cat);
            }
        }catch (Exception e){

        }
        return dados;
    }
}
