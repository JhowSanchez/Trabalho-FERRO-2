package br.fipp.botecofx;

import DB.SingletonDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuController {
    public Button buttonGarcon;
    public Button buttonComanda;
    public Button buttonCategoria;
    public Button buttonUnidade;
    public Button buttonProdutos;
    public Button buttonRelatorioPreco;
    public Button buttonRelatorioVendas;
    public Button buttornRelatorioComanda;

    public void onGerenciarGarcon(ActionEvent actionEvent) throws IOException {
        buttonGarcon.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("garcon-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Garçoes");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onGerenciarProduto(ActionEvent actionEvent) throws IOException {
        buttonProdutos.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Produto-Table-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Produtos");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }


    public void onGerenciarCategoria(ActionEvent actionEvent) throws IOException {
        buttonCategoria.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Categoria-Table-View.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Categoria");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onGerenciarUnidade(ActionEvent actionEvent) throws IOException {
        buttonUnidade.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Unidade-Table-View.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Unidade");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onGerenciarComanda(ActionEvent actionEvent) throws IOException {
        buttonComanda.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("gerenciarComanda.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Comandas");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onRelatorioPreco(ActionEvent actionEvent) {
        gerarRelatorio("select pro_nome,pro_preco from produto","./Relatorios/rel_precos.jrxml");
    }

    public void onRelatorioVendas(ActionEvent actionEvent) {
        gerarRelatorio("SELECT \n" +
                "    c.com_nome AS \"Nome da Comanda\",\n" +
                "    p.pro_nome AS \"Nome do Produto\",\n" +
                "    i.it_quant AS \"Quantidade\",\n" +
                "    CAST(REPLACE(p.pro_preco, ',', '.') AS numeric) AS \"Preço do Produto\",\n" +
                "    i.it_quant * CAST(REPLACE(p.pro_preco, ',', '.') AS numeric) AS \"Total\",\n" +
                "    SUM(i.it_quant * CAST(REPLACE(p.pro_preco, ',', '.') AS numeric)) OVER (ORDER BY c.com_nome) AS \"Total Acumulado\"\n" +
                "FROM \n" +
                "    comanda c\n" +
                "JOIN \n" +
                "    item i ON c.com_id = i.com_id\n" +
                "JOIN \n" +
                "    produto p ON i.prod_id = p.pro_id\n" +
                "WHERE \n" +
                "    CAST(c.com_data AS date) = CURRENT_DATE\n" +
                "ORDER BY \n" +
                "    c.com_nome;","./Relatorios/rel_vendas.jrxml");
    }

    public void onRelatorioComanda(ActionEvent actionEvent) {
        gerarRelatorio("SELECT \n" +
                "    c.com_nome AS \"Nome da Comanda\",\n" +
                "    p.pro_nome AS \"Nome do Produto\",\n" +
                "    i.it_quant AS \"Quantidade\",\n" +
                "    CAST(REPLACE(p.pro_preco, ',', '.') AS numeric) AS \"Preço do Produto\",\n" +
                "    i.it_quant * CAST(REPLACE(p.pro_preco, ',', '.') AS numeric) AS \"Total\"\n" +
                "FROM \n" +
                "    comanda c\n" +
                "JOIN \n" +
                "    item i ON c.com_id = i.com_id\n" +
                "JOIN \n" +
                "    produto p ON i.prod_id = p.pro_id\n" +
                "ORDER BY \n" +
                "    c.com_nome;\n","./Relatorios/rel_Comandas.jrxml");
    }
    private void gerarRelatorio(String sql,String relat)
    {
        ResultSet rs = null;
        try {
            // SQL para obter os dados para o relatório
            rs = SingletonDB.getConexao().consultar(sql);

            // Implementação da interface JRDataSource para DataSource ResultSet
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            // Compilando o relatório .jrxml para .jasper (se necessário)
            String jasperReport = JasperCompileManager.compileReportToFile(relat);

            // Chamando o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrRS);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH); // Maximizado
            viewer.setTitle("Relatório de Comandas"); // Título do relatório
            viewer.setVisible(true);
        } catch (JRException erro) {
            erro.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
