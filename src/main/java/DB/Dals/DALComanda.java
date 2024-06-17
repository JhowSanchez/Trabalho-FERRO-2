package DB.Dals;

import DB.SingletonDB;
import  DB.entidade.Item;
import DB.entidade.Comanda;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALComanda implements IDAL<Comanda>{

    @Override
    public boolean gravar(Comanda entidade) {
        String sql = """
        INSERT INTO comanda(
            com_numero, com_nome, com_data, gar_id)
            VALUES ('#1', '#2', '#3', '#4');
        """;

        sql = sql.replace("#1",""+ entidade.getNumero());
        sql = sql.replace("#2", entidade.getNome());
        sql = sql.replace("#3", entidade.getDataHora());
        sql = sql.replace("#4", "" + entidade.getGarcon().getId());

        boolean sucessoComanda = SingletonDB.getConexao().manipular(sql);

        if (!sucessoComanda) {
            return false;
        }

        ResultSet rs;
        sql = "SELECT * FROM comanda ORDER BY com_id DESC LIMIT 1;";
        rs = SingletonDB.getConexao().consultar(sql);
        Comanda comanda = null;
        try{
            if (rs.next()){
                String dataNaoFormatada = rs.getString("com_data");
                String[] dataHoraSplitada = dataNaoFormatada.split(" ");;
                String hora = dataHoraSplitada[1];
                String data = dataHoraSplitada[0];
                DALGarcon gar = new DALGarcon();


                String[] dataSplitada = data.split("-");
                data = dataSplitada[2]+"-"+dataSplitada[1]+"-"+dataSplitada[0]+" "+hora;
                comanda = new Comanda(rs.getInt("com_id"),rs.getInt("com_numero"),
                        rs.getString("com_nome"),data,gar.get(rs.getInt("gar_id")));
            }
        }catch (Exception e){
            
        }
        List<Item> itens = entidade.getItem();
        for (Item item : itens) {
            String sqlItem = """
            INSERT INTO item(
                it_quant, com_id, prod_id)
                VALUES ('#1', '#2', '#3')
            """;

            sqlItem = sqlItem.replace("#1", "" + item.getQuant());
            sqlItem = sqlItem.replace("#2", "" + comanda.getID());
            sqlItem = sqlItem.replace("#3", "" + item.getProID());


            boolean sucessoItem = SingletonDB.getConexao().manipular(sqlItem);

            if (!sucessoItem) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean alterar(Comanda entidade) {
        String sql= """
                UPDATE comanda
                	SET com_numero= #1, com_nome='#2', com_data='#3',gar_id= #4
                	WHERE com_id=#5;
                """;

        sql = sql.replace("#1", "" + entidade.getNumero());
        sql = sql.replace("#2", entidade.getNome());
        sql = sql.replace("#3", entidade.getDataHora());
        sql = sql.replace("#4",""+entidade.getGarcon().getId());
        sql = sql.replace("#5",""+entidade.getID());


        boolean sucessoComanda = SingletonDB.getConexao().manipular(sql);
        boolean flag = false;
        if (!sucessoComanda) {
            return false;
        }

        List<Item> itens = entidade.getItem();
        for (int i =0; i < itens.size();i++) {
            String sqlItem = """
            UPDATE item
                SET it_quant = #1
                WHERE com_id = #2 and prod_id = #3
            """;
            sqlItem = sqlItem.replace("#1", "" + itens.get(i).getQuant());
            sqlItem = sqlItem.replace("#2", "" + entidade.getID());
            sqlItem = sqlItem.replace("#3","" + itens.get(i).getProduto().getID());
            boolean sucessoItem = SingletonDB.getConexao().manipular(sqlItem);
            if (sucessoItem) {
                flag = true;
            }
        }
        if(!flag)
        {
            for (int i =0; i < itens.size();i++)  {
                String sqlItem = """
                        INSERT INTO item(
                        	it_quant, com_id, prod_id)
                        	VALUES (#1, #2, #3);
                        	""";

                sqlItem = sqlItem.replace("#1", "" + itens.get(i).getQuant());
                sqlItem = sqlItem.replace("#2", "" + entidade.getID());
                sqlItem = sqlItem.replace("#3", "" + itens.get(i).getProduto().getID());

                boolean sucessoItem2 = SingletonDB.getConexao().manipular(sqlItem);

                if (!sucessoItem2) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean apagar(Comanda entidade) {

        SingletonDB.getConexao().manipular("DELETE FROM item WHERE com_id="+entidade.getID());
        return SingletonDB.getConexao().manipular("DELETE FROM comanda WHERE com_id="+entidade.getID());
    }

    @Override
    public Comanda get(int id) {
        Comanda comanda = null;
        String sql = "SELECT com_id,com_nome, com_numero, com_data, gar_id FROM comanda where com_id ="+id;
        String slqItem  = "SELECT * FROM item where com_id ="+id;
        ResultSet rs = null;
        ResultSet rs1 = null;
        rs = SingletonDB.getConexao().consultar(sql);
        rs1 = SingletonDB.getConexao().consultar(slqItem);
        try{
            if (rs.next()){

                String dataNaoFormatada = rs.getString("com_data");
                String[] dataHoraSplitada = dataNaoFormatada.split(" ");;
                String hora = dataHoraSplitada[1];
                String data = dataHoraSplitada[0];
                String[] dataSplitada = data.split("-");
                data = dataSplitada[2]+"-"+dataSplitada[1]+"-"+dataSplitada[0]+" "+hora;

                DALGarcon gar = new DALGarcon();
                List<Item> items = new ArrayList<>();
                while (rs1.next()) {
                    items.add(new Item(rs1.getInt("com_id"), rs1.getInt("pro_id"), rs1.getInt("it_quant")));
                }

                comanda = new Comanda(rs.getInt("com_id"), rs.getInt("com_numero"),
                        rs.getString("com_nome"), data,
                        gar.get(rs.getInt("gar_id")), items);
            }
        }catch (Exception e){

        }
        return comanda;
    }

    @Override
    public List<Comanda> get(String filtro) {
        List<Comanda> dados = new ArrayList<>();
        String sql = "SELECT * FROM comanda WHERE LOWER(com_nome) LIKE LOWER('%"+filtro+"%')";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);

        try{
            while (rs.next()){
                String dataNaoFormatada = rs.getString("com_data");
                String[] dataHoraSplitada = dataNaoFormatada.split(" ");;
                String hora = dataHoraSplitada[1];
                String data = dataHoraSplitada[0];
                String[] dataSplitada = data.split("-");
                data = dataSplitada[2]+"-"+dataSplitada[1]+"-"+dataSplitada[0]+" "+hora;

                DALGarcon gar = new DALGarcon();

                Comanda comanda = new Comanda(rs.getInt("com_id"),rs.getInt("com_numero"),
                        rs.getString("com_nome"),data,gar.get(rs.getInt("gar_id")));
                dados.add(comanda);
            }
        }catch (Exception e){

        }
        return dados;
    }

    @Override
    public List<Comanda> get() {
        List<Comanda> dados = new ArrayList<>();
        String sql = "SELECT com_id,com_nome, com_numero, com_data, gar_id FROM comanda";
        ResultSet rs = null;
        rs = SingletonDB.getConexao().consultar(sql);
        try{
            while (rs.next()){
                String dataNaoFormatada = rs.getString("com_data");
                String[] dataHoraSplitada = dataNaoFormatada.split(" ");;
                String hora = dataHoraSplitada[1];
                String data = dataHoraSplitada[0];
                String[] dataSplitada = data.split("-");
                data = dataSplitada[2]+"-"+dataSplitada[1]+"-"+dataSplitada[0]+" "+hora;
                DALGarcon gar = new DALGarcon();

                Comanda comanda = new Comanda(rs.getInt("com_id"),rs.getInt("com_numero"),
                        rs.getString("com_nome"),data,gar.get(rs.getInt("gar_id")));
                dados.add(comanda);
            }
        }catch (Exception e){

        }
        return dados;
    }
}
