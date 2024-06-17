package br.fipp.botecofx;


import DB.Dals.DALCategoria;
import DB.Dals.DALProduto;
import DB.Dals.DALUnidade;
import DB.entidade.Categoria;
import DB.entidade.Produto;
import DB.entidade.Unidade;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoTableController implements Initializable {
    public TextField tfFiltro;
    public FontAwesomeIconView buttonBusca;
    public Button buttonCadastro;
    public Button buttonVoltar;
    public Produto p;

    @FXML
    public TableView tabelaProduto;
    @FXML
    public TableColumn ColID,ColNomeProd,ColCategoria,ColUnid,ColPreco;


    public void gerarTabela()
    {
        DALProduto prod = new DALProduto();
        List<Produto> dados = prod.get() ;

        Collections.sort(dados, Comparator.comparing(Produto::getID));
        try {
            ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColNomeProd.setCellValueFactory(new PropertyValueFactory<>("nome"));
            ColPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
            ColUnid.setCellValueFactory(new PropertyValueFactory<>("uni"));
            ColCategoria.setCellValueFactory(new PropertyValueFactory<>("cat"));

            ObservableList<Produto> modelo = FXCollections.observableArrayList(dados);
            tabelaProduto.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarTabelaSolo(List<Produto> dados)
    {

        Collections.sort(dados, Comparator.comparing(Produto::getID));
        try {
            ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColNomeProd.setCellValueFactory(new PropertyValueFactory<>("nome"));
            ColPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
            ColUnid.setCellValueFactory(new PropertyValueFactory<>("uni"));
            ColCategoria.setCellValueFactory(new PropertyValueFactory<>("cat"));

            ObservableList<Produto> modelo = FXCollections.observableArrayList(dados);
            tabelaProduto.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPesquisar(ActionEvent actionEvent) {
        DALProduto prod = new DALProduto();

        if(!tfFiltro.getText().isEmpty())
        {
            List<Produto> dados = prod.get(tfFiltro.getText());
            gerarTabelaSolo(dados);
        }
        else
            gerarTabela();
    }

    public void onNovoCadastro(ActionEvent actionEvent) throws IOException {
        ProdutoFormController.setFlag(false);
        buttonCadastro.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("produto-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Produto");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void onVoltar(ActionEvent actionEvent) throws IOException {
        buttonVoltar.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Produto");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        ProdutoFormController.setFlag(true);
        ProdutoFormController.setPro(p);
        buttonCadastro.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("produto-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Produto");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onApagar(ActionEvent actionEvent) {
        DALProduto DALConf = new DALProduto();

        System.out.println(DALConf.apagar(p));
        gerarTabela();
    }
    public void onMouseClick(MouseEvent mouseEvent) {
        int i = tabelaProduto.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            p = (Produto) tabelaProduto.getItems().get(i);
        } else {
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarTabela();
        tfFiltro.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                DALProduto prod = new DALProduto();

                if(!tfFiltro.getText().isEmpty())
                {
                    List<Produto> dados = prod.get(tfFiltro.getText());
                    gerarTabelaSolo(dados);
                }
                else
                    gerarTabela();
            }
        });
    }


}
