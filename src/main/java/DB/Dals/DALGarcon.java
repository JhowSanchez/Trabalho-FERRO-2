package DB.Dals;

import DB.SingletonDB;
import DB.entidade.Garcon;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALGarcon implements IDAL<Garcon>{

    @Override
    public boolean gravar(Garcon entidade) {
        String sql= """
                INSERT INTO garcon(
                	gar_nome, gar_cpf, gar_end, gar_cidade, gar_uf, gar_fone, gar_cep)
                	VALUES ('#1', '#2', '#3', '#4', '#5', '#6', '#7')
                """;
        sql = sql.replace("#1",entidade.getNome());
        sql = sql.replace("#2",entidade.getCPF());
        sql = sql.replace("#3",entidade.getEndereco());
        sql = sql.replace("#4",entidade.getCidade());
        sql = sql.replace("#5",entidade.getUF());
        sql = sql.replace("#6",entidade.getFone());
        sql = sql.replace("#7",entidade.getCEP());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Garcon entidade) {
        String sql= """
                UPDATE garcon
                	SET gar_nome='#1', gar_cpf='#2',gar_cep='#3', gar_end='#4', gar_cidade='#5', gar_uf='#6', gar_fone='#7'
                	WHERE gar_id=#8;
                """;

        sql = sql.replace("#1",entidade.getNome());
        sql = sql.replace("#2",entidade.getCPF());
        sql = sql.replace("#3",entidade.getCEP());
        sql = sql.replace("#4",entidade.getEndereco());
        sql = sql.replace("#5",entidade.getCidade());
        sql = sql.replace("#6",entidade.getUF());
        sql = sql.replace("#7",entidade.getFone());
        sql = sql.replace("#8",""+entidade.getId());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Garcon entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM garcon WHERE gar_id="+entidade.getId());
    }

    @Override
    public Garcon get(int id) {
        Garcon garcon = null;
        String sql = "SELECT gar_id,gar_nome, gar_cpf, gar_cep, gar_end,gar_cidade, gar_uf, gar_fone FROM garcon WHERE gar_id="+id;
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
            try{
                if (rs.next()){
                      garcon = new Garcon(rs.getInt("gar_id"),rs.getString("gar_nome"),
                             rs.getString("gar_cpf"),rs.getString("gar_cep"),
                             rs.getString("gar_end"),rs.getString("gar_cidade"),
                             rs.getString("gar_uf"),rs.getString("gar_fone"));
                }
            }catch (Exception e){

            }
        return garcon;
    }

    @Override
    public List<Garcon> get(String filtro) {
        List<Garcon> dados = new ArrayList<>();
        String sql = "SELECT * FROM garcon WHERE LOWER(gar_nome) LIKE LOWER('%"+filtro+"%')";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        try{
            while (rs.next()){
                Garcon garcon = new Garcon(rs.getInt("gar_id"),rs.getString("gar_nome"),
                        rs.getString("gar_cpf"),rs.getString("gar_cep"),
                        rs.getString("gar_end"),rs.getString("gar_cidade"),
                        rs.getString("gar_uf"),rs.getString("gar_fone"));
                dados.add(garcon);
            }
        }catch (Exception e){

        }
        return dados;
    }

    @Override
    public List<Garcon> get() {
        List<Garcon> dados = new ArrayList<>();
        String sql = "SELECT gar_id,gar_nome, gar_cpf, gar_cep, gar_end,gar_cidade, gar_uf, gar_fone FROM garcon";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
        try{
            while (rs.next()){
                Garcon garcon = new Garcon(rs.getInt("gar_id"),rs.getString("gar_nome"),
                        rs.getString("gar_cpf"),rs.getString("gar_cep"),
                        rs.getString("gar_end"),rs.getString("gar_cidade"),
                        rs.getString("gar_uf"),rs.getString("gar_fone"));
                dados.add(garcon);
            }
        }catch (Exception e){

        }
        return dados;
    }
}
