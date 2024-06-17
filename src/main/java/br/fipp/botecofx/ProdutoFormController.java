package br.fipp.botecofx;

import DB.Dals.DALCategoria;
import DB.Dals.DALProduto;
import DB.Dals.DALUnidade;
import DB.entidade.Categoria;
import DB.entidade.Produto;
import DB.entidade.Unidade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.MaskFieldUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;


public class ProdutoFormController implements Initializable {
    public TextField tfNome;
    public TextField tfPreco;
    public Button CadastroConfirmar;
    public Button CadastroVoltar;
    public ComboBox comboCategoria;
    public ComboBox comboUnidade;

    private static Produto pro = null;
    private static boolean flag;

    public static Produto getPro() {
        return pro;
    }

    public static void setPro(Produto pro) {
        ProdutoFormController.pro = pro;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        ProdutoFormController.flag = flag;
    }

    public void onConfirmar() throws IOException {
        DALProduto DALConf = new DALProduto();

        String msg = "";

        if(tfNome.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo nome vazio!\n";

        if(tfPreco.getText() == null || tfPreco.getText().isEmpty())
            msg += "Campo preço vazio!\n";

        if(msg.isEmpty()) {
            if (getPro() == null) {

                int i = comboCategoria.getSelectionModel().getSelectedIndex();
                Categoria cat = (Categoria) comboCategoria.getItems().get(i);
                int j = comboUnidade.getSelectionModel().getSelectedIndex();
                Unidade uni = (Unidade) comboUnidade.getItems().get(j);

                Produto prod = new Produto(tfNome.getText(),tfPreco.getText(),uni,cat);
                if (!DALConf.gravar(prod)) {
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
            } else {
                Produto prod;
                if(comboUnidade.getValue().equals(getPro().getUni()) && comboCategoria.getValue().equals(getPro().getCat()))
                    prod = new Produto(getPro().getID(), tfNome.getText(),tfPreco.getText(),getPro().getUni(),getPro().getCat());
                else if(comboUnidade.getValue().equals(getPro().getUni()))
                {
                    int i = comboCategoria.getSelectionModel().getSelectedIndex();
                    Categoria cat = (Categoria) comboCategoria.getItems().get(i);
                    prod = new Produto(getPro().getID(), tfNome.getText(),tfPreco.getText(),getPro().getUni(),cat);
                }
                else if(comboCategoria.getValue().equals(getPro().getCat()))
                {
                    int j = comboUnidade.getSelectionModel().getSelectedIndex();
                    Unidade uni = (Unidade) comboUnidade.getItems().get(j);
                    prod = new Produto(getPro().getID(), tfNome.getText(),tfPreco.getText(),uni,getPro().getCat());

                }
                else
                {
                    int i = comboCategoria.getSelectionModel().getSelectedIndex();
                    Categoria cat = (Categoria) comboCategoria.getItems().get(i);
                    int j = comboUnidade.getSelectionModel().getSelectedIndex();
                    Unidade uni = (Unidade) comboUnidade.getItems().get(j);

                    prod = new Produto(getPro().getID(), tfNome.getText(),tfPreco.getText(),uni,cat);
                }

                if (!DALConf.alterar(prod)) {

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
                setPro(null);
            }
            fecharJanela();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setContentText(msg);
            alert.setHeaderText("Erro!!!");
            alert.showAndWait();
        }
    }

    public void initProd(){
        tfNome.setText(pro.getNome());
        tfPreco.setText(String.valueOf(pro.getPreco()));
        comboUnidade.setValue(pro.getUni());
        comboCategoria.setValue(pro.getCat());
    }

    public void gerarCategoria() {
        DALCategoria cat = new DALCategoria();
        List<Categoria> dados = cat.get();
        ObservableList<Categoria> modelo = FXCollections.observableArrayList(dados);

        comboCategoria.setItems(modelo); // Define o modelo de itens para o comboCategoria

    }

    public void gerarUnidade()
    {
        DALUnidade uni = new DALUnidade();
        List<Unidade> dados = uni.get();
        ObservableList<Unidade> modelo = FXCollections.observableArrayList(dados);

        comboUnidade.setItems(modelo); // Define o modelo de itens para o comboCategoria

    }
    public void fecharJanela() throws IOException {
        CadastroVoltar.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Produto-Table-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Produto");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void onVoltar(ActionEvent actionEvent) throws IOException {
        fecharJanela();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gerarCategoria();
        gerarUnidade();
        if(isFlag())
            initProd();
        else
        {
            tfNome.setText(null);
            tfPreco.setText(null);
            comboCategoria.setValue(null);
            comboUnidade.setValue(null);
        }
        MaskFieldUtil.monetaryField(tfPreco);
    }

}
