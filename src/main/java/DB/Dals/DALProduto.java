package DB.Dals;

import DB.SingletonDB;
import DB.entidade.Produto;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALProduto implements IDAL <Produto>{
    @Override
    public boolean gravar(Produto entidade) {
        String sql= """
                INSERT INTO produto(
                	pro_nome, pro_preco, uni_id, cat_id)
                	VALUES ('#1','#2','#3', '#4')
                """;
        sql = sql.replace("#1",entidade.getNome());
        sql = sql.replace("#2",entidade.getPreco());
        sql = sql.replace("#3",""+entidade.getUni().getID());
        sql = sql.replace("#4",""+entidade.getCat().getID());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Produto entidade) {
        String sql= """
                UPDATE produto
                	SET pro_nome='#1', pro_preco='#2', uni_id='#3', cat_id='#4'
                	WHERE pro_id = #5
                """;
        sql = sql.replace("#1",entidade.getNome());
        sql = sql.replace("#2",entidade.getPreco());
        sql = sql.replace("#3",""+entidade.getUni().getID());
        sql = sql.replace("#4",""+entidade.getCat().getID());
        sql = sql.replace("#5",""+entidade.getID());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Produto entidade) {
        boolean teste ;

        teste = SingletonDB.getConexao().manipular("DELETE FROM produto WHERE pro_id="+entidade.getID());
        if(!teste)
            return false;
        return true;

    }

    @Override
    public Produto get(int id) {
        Produto pro = null;
        String sql = "SELECT pro_id,pro_nome,pro_preco, uni_id, cat_id FROM produto WHERE pro_id="+id;
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
        DALCategoria cat = new DALCategoria();
        DALUnidade uni = new DALUnidade();
        try{
            if (rs.next()){
                pro = new Produto(rs.getInt("pro_id"),rs.getString("pro_nome"),rs.getString("pro_preco"),
                        uni.get(rs.getInt("uni_id")),cat.get(rs.getInt("cat_id")));
            }
        }catch (Exception e){

        }
        return pro;
    }

    @Override
    public List<Produto> get(String filtro) {
        List<Produto> dados = new ArrayList<>();
        String sql = "SELECT pro_id,pro_nome,pro_preco, uni_id, cat_id FROM produto WHERE LOWER(pro_nome) LIKE LOWER('%"+filtro+"%')";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
        DALUnidade uni = new DALUnidade();
        DALCategoria cat = new DALCategoria();


        try{
            while (rs.next()){
                Produto pro = new Produto(rs.getInt("pro_id"),rs.getString("pro_nome"),rs.getString("pro_preco"),
                        uni.get(rs.getInt("uni_id")),cat.get(rs.getInt("cat_id")));
                dados.add(pro);
            }
        }catch (Exception e){

        }
        return dados;
    }

    @Override
    public List<Produto> get() {
        List<Produto> dados = new ArrayList<>();
        String sql = "SELECT pro_id,pro_nome,pro_preco, uni_id, cat_id FROM produto";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        DALUnidade uni = new DALUnidade();
        DALCategoria cat = new DALCategoria();

        try{
            while (rs.next()){
                Produto pro = new Produto(rs.getInt("pro_id"),rs.getString("pro_nome"),rs.getString("pro_preco"),
                        uni.get(rs.getInt("uni_id")),cat.get(rs.getInt("cat_id")));
                dados.add(pro);
            }
        }catch (Exception e){

        }
        return dados;
    }
}
