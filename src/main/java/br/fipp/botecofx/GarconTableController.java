package br.fipp.botecofx;

import DB.Dals.DALGarcon;
import DB.entidade.Garcon;
import DB.entidade.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class GarconTableController implements Initializable {
    public TextField tfFiltro;
    public Button buttonVoltar;
    @FXML
    public TableView tabelaGarcon;
    @FXML
    public TableColumn colID,colNome,ColFone,colEnd,colCid,colUf,colCpf,colCep;
    public Button buttonCadastro;
    ObservableList<Garcon> modelo;

    public  Garcon p;
    private static Item item;

    public static Item getItem() {
        return item;
    }

    public static void setItem(Item item) {
        GarconTableController.item = item;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarTabela();
        tfFiltro.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String aux;
                DALGarcon gar = new DALGarcon();
                List <Garcon> garcon ;
                if(!tfFiltro.getText().isEmpty())
                {
                    aux = tfFiltro.getText();
                    garcon = gar.get(aux);
                    gerarTabelaSolo(garcon);
                }
                else
                    gerarTabela();
            }
        });
    }
    public void gerarTabela()
    {
        DALGarcon garcon = new DALGarcon();
        List <Garcon>dados = garcon.get() ;

        Collections.sort(dados, Comparator.comparing(Garcon::getId));
        try {
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
            colCep.setCellValueFactory(new PropertyValueFactory<>("CEP"));
            colEnd.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
            colCid.setCellValueFactory(new PropertyValueFactory<>("Cidade"));
            colUf.setCellValueFactory(new PropertyValueFactory<>("UF"));
            ColFone.setCellValueFactory(new PropertyValueFactory<>("Fone"));


            // Criar um ObservableList a partir dos dados e configurar a tabela com ele
            ObservableList<Garcon> modelo = FXCollections.observableArrayList(dados);
            tabelaGarcon.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void gerarTabelaSolo(List<Garcon> dados)
    {
        try {
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
            colCep.setCellValueFactory(new PropertyValueFactory<>("CEP"));
            colEnd.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
            colCid.setCellValueFactory(new PropertyValueFactory<>("Cidade"));
            colUf.setCellValueFactory(new PropertyValueFactory<>("UF"));
            ColFone.setCellValueFactory(new PropertyValueFactory<>("Fone"));


            // Criar um ObservableList a partir dos dados e configurar a tabela com ele
            ObservableList<Garcon> modelo = FXCollections.observableArrayList(dados);
            tabelaGarcon.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPesquisar(ActionEvent actionEvent) {
        String aux;
        DALGarcon gar = new DALGarcon();
        List <Garcon> garcon ;
        if(!tfFiltro.getText().isEmpty())
        {
            aux = tfFiltro.getText();
            garcon = gar.get(aux);
            gerarTabelaSolo(garcon);
        }
        else
            gerarTabela();
    }
    public void onApagar(ActionEvent event) {
        DALGarcon DALConf = new DALGarcon();
        DALConf.apagar(p);
        gerarTabela();
    }

    public void onAlterar(ActionEvent event) throws IOException {
        GarconFormController.setFlag(true);
        GarconFormController.setGarcon(p);
        tabelaGarcon.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("garcon-form-view.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Alterar Garçon");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onVoltar(ActionEvent event) throws IOException {
        buttonVoltar.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Garçon");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onMouseeClick(MouseEvent mouseEvent) {
        int i = tabelaGarcon.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            p = (Garcon) tabelaGarcon.getItems().get(i);
        } else {
        }
    }

    public void onCadastro(ActionEvent actionEvent) throws IOException {
        GarconFormController.setFlag(false);
        tabelaGarcon.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("garcon-form-view.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Garçon");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}