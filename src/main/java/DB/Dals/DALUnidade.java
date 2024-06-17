package DB.Dals;

import DB.SingletonDB;
import DB.entidade.Categoria;
import DB.entidade.Unidade;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALUnidade implements  IDAL <Unidade>{
    @Override
    public boolean gravar(Unidade entidade) {
        String sql= """
                INSERT INTO unidade(
                	uni_nome)
                	VALUES ('#1')
                """;
        sql = sql.replace("#1",entidade.getNome());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Unidade entidade) {
        String sql= """
                UPDATE unidade
                	SET uni_nome ='#1'
                	WHERE uni_id=#2;
                """;
        sql = sql.replace("#1",entidade.getNome());
        sql = sql.replace("#2",""+entidade.getID());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Unidade entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM unidade WHERE uni_id="+entidade.getID());
    }

    @Override
    public Unidade get(int id) {
        Unidade uni = null;
        String sql = "SELECT uni_id,uni_nome FROM unidade WHERE uni_id="+id;
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
        try{
            if (rs.next()){
                uni = new Unidade(rs.getInt("uni_id"),rs.getString("uni_nome"));
            }
        }catch (Exception e){

        }
        return uni;
    }

    @Override
    public List<Unidade> get(String filtro) {
        List<Unidade> dados = new ArrayList<>();
        String sql = "SELECT uni_id,uni_nome FROM unidade WHERE LOWER(uni_nome) LIKE LOWER('%"+filtro+"%')";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        try{
            while (rs.next()){
                Unidade uni = new Unidade(rs.getInt("uni_id"),rs.getString("uni_nome"));
                dados.add(uni);
            }
        }catch (Exception e){

        }
        return dados;
    }

    @Override
    public List<Unidade> get() {
        List<Unidade> dados = new ArrayList<>();
        String sql = "SELECT uni_id,uni_nome FROM unidade ";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        try{
            while (rs.next()){
                Unidade uni = new Unidade(rs.getInt("uni_id"),rs.getString("uni_nome"));
                dados.add(uni);
            }
        }catch (Exception e){

        }
        return dados;
    }
}
