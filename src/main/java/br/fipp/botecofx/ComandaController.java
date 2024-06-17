package br.fipp.botecofx;

import DB.Dals.DALComanda;
import DB.Dals.DALGarcon;
import DB.entidade.Comanda;
import DB.entidade.Garcon;
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


public class ComandaController implements Initializable {
    public Button ComandaVolta;
    public TextField tfFiltro;
    public Button buttonCadastro;
    public Comanda p;
    public TableColumn ColGarcon;
    public TableColumn colProdutos;
    public TableColumn colTotal;
    ObservableList<Comanda> modelo;

    @FXML
    public TableView tabela;
    @FXML
    public TableColumn ColNome,ColNum,colData,colID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarTabela();
        tfFiltro.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                DALComanda com = new DALComanda();

                if(!tfFiltro.getText().isEmpty())
                {
                    List<Comanda> dados = com.get(tfFiltro.getText());
                    gerarTabelaSolo(dados);
                }
                else
                    gerarTabela();
            }
        });
    }

    public void gerarTabela()
    {
        DALComanda comanda = new DALComanda();
        List<Comanda> dados = comanda.get() ;

        Collections.sort(dados, Comparator.comparing(Comanda::getID));
        try {
            colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColGarcon.setCellValueFactory(new PropertyValueFactory<>("garcon"));
            ColNum.setCellValueFactory(new PropertyValueFactory<>("Numero"));
            ColNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            colData.setCellValueFactory(new PropertyValueFactory<>("DataHora"));
            ObservableList<Comanda> modelo = FXCollections.observableArrayList(dados);
            tabela.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarTabelaSolo(List<Comanda> dados)
    {
        Collections.sort(dados, Comparator.comparing(Comanda::getID));
        try {
            colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColGarcon.setCellValueFactory(new PropertyValueFactory<>("garcon"));
            ColNum.setCellValueFactory(new PropertyValueFactory<>("Numero"));
            ColNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            colData.setCellValueFactory(new PropertyValueFactory<>("DataHora"));

            ObservableList<Comanda> modelo = FXCollections.observableArrayList(dados);
            tabela.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onPesquisar(ActionEvent actionEvent) {
        DALComanda com = new DALComanda();

        if(!tfFiltro.getText().isEmpty())
        {
            List<Comanda> dados = com.get(tfFiltro.getText());
            gerarTabelaSolo(dados);
        }
        else
            gerarTabela();

    }


    public void onVoltar(ActionEvent actionEvent) throws IOException {
        ComandaVolta.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        ComandaFormController.setFlag(true);
        ComandaFormController.setCom(p);
        tabela.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("comanda-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Comanda");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onApagar(ActionEvent actionEvent) {
        DALComanda DALConf = new DALComanda();
        DALConf.apagar(p);
        gerarTabela();
    }

    public void onCadastro(ActionEvent actionEvent) throws IOException {
        ComandaFormController.setFlag(false);
        tabela.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("comanda-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Comanda");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onMouseClick(MouseEvent mouseEvent) {
        int i = tabela.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            p = (Comanda) tabela.getItems().get(i);
        } else {
        }
    }
}
