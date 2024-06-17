package br.fipp.botecofx;

import DB.Dals.DALCategoria;
import DB.Dals.DALUnidade;
import DB.Dals.IDAL;
import DB.entidade.Categoria;
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

public class UnidadeTableController implements Initializable {
    public TextField tfFiltro;
    public FontAwesomeIconView buttonBusca;
    public Button buttonCadastro;
    public Button buttonVoltar;
    public Unidade p;

    @FXML
    public TableView tabelaUnideal;
    @FXML
    public TableColumn ColID,ColNomeCategoria;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarTabela();
        tfFiltro.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                DALUnidade uni = new DALUnidade();

                if(!tfFiltro.getText().isEmpty())
                {
                    List<Unidade> dados = uni.get(tfFiltro.getText());
                    gerarTabelaSolo(dados);
                }
                else
                    gerarTabela();

            }
        });
    }
    public void gerarTabela()
    {
        DALUnidade uni = new DALUnidade();
        List<Unidade> dados = uni.get() ;

        Collections.sort(dados, Comparator.comparing(Unidade::getID));
        try {
            ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColNomeCategoria.setCellValueFactory(new PropertyValueFactory<>("nome"));

            // Criar um ObservableList a partir dos dados e configurar a tabela com ele
            ObservableList<Unidade> modelo = FXCollections.observableArrayList(dados);
            tabelaUnideal.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarTabelaSolo(List<Unidade> dados)
    {

        Collections.sort(dados, Comparator.comparing(Unidade::getID));
        try {
            ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColNomeCategoria.setCellValueFactory(new PropertyValueFactory<>("nome"));

            // Criar um ObservableList a partir dos dados e configurar a tabela com ele
            ObservableList<Unidade> modelo = FXCollections.observableArrayList(dados);
            tabelaUnideal.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPesquisar(ActionEvent actionEvent) {
        DALUnidade uni = new DALUnidade();

        if(!tfFiltro.getText().isEmpty())
        {
            List<Unidade> dados = uni.get(tfFiltro.getText());
            gerarTabelaSolo(dados);
        }
        else
            gerarTabela();

    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        UnidadeFormController.setFlag(true);
        UnidadeFormController.setUni(p);
        buttonCadastro.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Unidade-form-view.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Unidade");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onApagar(ActionEvent actionEvent) {
        DALUnidade DALConf = new DALUnidade();
        DALConf.apagar(p);
        gerarTabela();
    }

    public void onNovoCadastro(ActionEvent actionEvent) throws IOException {
        UnidadeFormController.setFlag(false);
        buttonCadastro.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Unidade-form-view.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Unidade");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onVoltar(ActionEvent actionEvent) throws IOException {

        buttonVoltar.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Unidade");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void onMouseClick(MouseEvent mouseEvent) {
        int i = tabelaUnideal.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            p = (Unidade) tabelaUnideal.getItems().get(i);
        } else {
        }
    }
}
