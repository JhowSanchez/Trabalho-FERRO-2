package br.fipp.botecofx;

import DB.Dals.DALCategoria;
import DB.Dals.DALComanda;
import DB.Dals.DALGarcon;
import DB.Dals.DALProduto;
import DB.entidade.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ComandaFormController implements Initializable {

    public TextField comNome;
    public Button CadastroVoltar;
    public Button CadastroConfirma;
    public TextField comNumero;

    private static Comanda com = null;
    private static Boolean flag ;
    public ComboBox ComboGarcon;
    public TableView tabela;

    public static Comanda getCom() {
        return com;
    }

    public static void setCom(Comanda com) {
        ComandaFormController.com = com;
    }

    public static Boolean getFlag() {
        return flag;
    }

    public static void setFlag(Boolean flag) {
        ComandaFormController.flag = flag;
    }

    public void gerarGarcon() {
        DALGarcon gar = new DALGarcon();
        List<Garcon> dados = gar.get();
        ObservableList<Garcon> modelo = FXCollections.observableArrayList(dados);

        ComboGarcon.setItems(modelo);
    }

    public void gerarTabela()
    {
        TableColumn<Item, String> colProd = new TableColumn<>("Produto");
        TableColumn<Item, Integer> colQuant = new TableColumn<>("Quantidade");

        colProd.setCellValueFactory(new PropertyValueFactory<>("Produto"));
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));

        colQuant.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colQuant.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setQuant(event.getNewValue());
        });

        tabela.getColumns().addAll(colProd, colQuant);
        tabela.setEditable(true);

        DALProduto dalProduto = new DALProduto();

        List<Produto> produtos = dalProduto.get();

        List<Item> itens = produtos.stream()
                .map(p -> new Item(0, p.getID(), 0, p))
                .collect(Collectors.toList());

        ObservableList<Item> observableItens = FXCollections.observableArrayList(itens);
        tabela.setItems(observableItens);
    }



    public void onConfirmar(ActionEvent actionEvent) throws IOException {
        DALComanda dalCom = new DALComanda();
        Comanda com = null;
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String data,hora;
        data = (""+dataHoraAtual).split("T")[0];
        hora = ((""+dataHoraAtual).split("T")[1]).split("\\.")[0];
        data = data+" "+hora;


        ObservableList<Item> items = tabela.getItems();
        List<Item> itemsParaAdicionar = items.stream()
                .filter(item -> item.getQuant() >= 1)
                .collect(Collectors.toList());

        String msg = "";

        if(comNome.getText() == null || comNome.getText().isEmpty())
            msg += "Campo nome vazio!\n";

        if(comNumero.getText() == null || comNumero.getText().isEmpty())
            msg += "Campo Numero comanda vazio!\n";

        if(ComboGarcon.getSelectionModel().getSelectedIndex() == -1)
            msg += "Campo Garcon não selecionado!\n";


        if(msg.isEmpty())
        {
            if(getCom() == null)
            {
                int i = ComboGarcon.getSelectionModel().getSelectedIndex();
                Garcon p = (Garcon) ComboGarcon.getItems().get(i);

                if(ComboGarcon.getSelectionModel().getSelectedIndex() != -1)
                {
                    com = new Comanda(Integer.parseInt(comNumero.getText()),comNome.getText(),data,p,itemsParaAdicionar);
                    if(!dalCom.gravar(com))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Mensagem de Erro");
                        alert.setHeaderText(null);
                        alert.setContentText("Erro ao realizar cadastro");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Mensagem de Sucesso");
                        alert.setHeaderText(null);
                        alert.setContentText("Cadastro realizado com sucesso");
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro!");
                    alert.setContentText(msg);
                    alert.setHeaderText("Erro!!!");
                    alert.showAndWait();
                }
            }
            else{
                if(ComboGarcon.getValue().equals(getCom().getGarcon()))
                {
                    com = new Comanda(getCom().getID(), Integer.parseInt(comNumero.getText()),comNome.getText(),getCom().getDataHora(),getCom().getGarcon(),itemsParaAdicionar);
                }
                else{
                    int i = ComboGarcon.getSelectionModel().getSelectedIndex();
                    Garcon p = (Garcon) ComboGarcon.getItems().get(i);
                    com = new Comanda(getCom().getID(), Integer.parseInt(comNumero.getText()),comNome.getText(),getCom().getDataHora(),p,itemsParaAdicionar);
                }
                if(!dalCom.alterar(com))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Mensagem de Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao realizar Alteração");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem de Sucesso");
                    alert.setContentText("Alteração realizada com sucesso");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                setFlag(false);
                setCom(null);
            }
            fecharJanela();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setContentText(msg);
            alert.setHeaderText("Erro!!!");
            alert.showAndWait();
        }
    }

    public void initCom(){
        comNumero.setText(String.valueOf(com.getNumero()));
        comNome.setText(com.getNome());
        ComboGarcon.setValue(com.getGarcon());

    }

    public void fecharJanela() throws IOException {
        CadastroVoltar.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("gerenciarComanda.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Gerenciar Comanda");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onVoltar(ActionEvent actionEvent) throws IOException {
        fecharJanela();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarGarcon();
        gerarTabela();
        if(getFlag())
        {
            initCom();
        }
        else {
            comNumero.setText(null);
            comNome.setText(null);
            ComboGarcon.setValue(null);
        }
    }
}
