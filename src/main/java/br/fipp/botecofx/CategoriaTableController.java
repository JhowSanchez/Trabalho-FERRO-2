package br.fipp.botecofx;

import DB.Dals.DALCategoria;
import DB.Dals.DALGarcon;
import DB.entidade.Categoria;
import DB.entidade.Garcon;
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

public class CategoriaTableController implements Initializable {
    public TextField tfFiltro;
    public FontAwesomeIconView buttonBusca;
    public Button buttonCadastro;
    public Button buttonVoltar;
    public Categoria p;

    @FXML
    public TableColumn ColID,ColNomeCategoria;
    @FXML
    public TableView tabelaCategoria;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarTabela();
        tfFiltro.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                DALCategoria cat = new DALCategoria();
                if(!tfFiltro.getText().isEmpty())
                {
                    List<Categoria> dados = cat.get(tfFiltro.getText());
                    gerarTabelaSolo(dados);
                }
                else
                    gerarTabela();
            }
        });
    }
    public void gerarTabela()
    {
        DALCategoria cat = new DALCategoria();
        List<Categoria> dados = cat.get() ;

        Collections.sort(dados, Comparator.comparing(Categoria::getID));
        try {
            ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColNomeCategoria.setCellValueFactory(new PropertyValueFactory<>("nome"));

            // Criar um ObservableList a partir dos dados e configurar a tabela com ele
            ObservableList<Categoria> modelo = FXCollections.observableArrayList(dados);
            tabelaCategoria.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarTabelaSolo(List<Categoria> dados)
    {
        Collections.sort(dados, Comparator.comparing(Categoria::getID));
        try {
            ColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            ColNomeCategoria.setCellValueFactory(new PropertyValueFactory<>("nome"));

            // Criar um ObservableList a partir dos dados e configurar a tabela com ele
            ObservableList<Categoria> modelo = FXCollections.observableArrayList(dados);
            tabelaCategoria.setItems(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPesquisar(ActionEvent actionEvent) {
        DALCategoria cat = new DALCategoria();
        if(!tfFiltro.getText().isEmpty())
        {
            List<Categoria> dados = cat.get(tfFiltro.getText());
            gerarTabelaSolo(dados);
        }
        else
            gerarTabela();
    }

    public void onNovoCadastro(ActionEvent actionEvent) throws IOException {
        CategoriaFormController.setFlag(false);
        buttonCadastro.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Categoria-form-view.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Categoria");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onVoltar(ActionEvent actionEvent) throws IOException {
        buttonVoltar.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Categoria");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        CategoriaFormController.setFlag(true);
        CategoriaFormController.setCat(p);
        buttonCadastro.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Categoria-form-view.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Categoria");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onApagar(ActionEvent actionEvent) {
        DALCategoria DALConf = new DALCategoria();
        DALConf.apagar(p);
        gerarTabela();
    }

    public void onMouseClick(MouseEvent mouseEvent) {
        int i = tabelaCategoria.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            p = (Categoria) tabelaCategoria.getItems().get(i);
        } else {
        }
    }

}
