package br.fipp.botecofx;

import DB.Dals.DALCategoria;
import DB.Dals.DALUnidade;
import DB.entidade.Categoria;
import DB.entidade.Unidade;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UnidadeFormController implements Initializable {
    public TextField tfId;
    public TextField tfNome;
    public Button CadastroConfirmar;
    public Button CadastroVoltar;

    private static Unidade uni = null;
    private static boolean flag;

    public static Unidade getUni() {
        return uni;
    }

    public static void setUni(Unidade uni) {
        UnidadeFormController.uni = uni;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        UnidadeFormController.flag = flag;
    }

    public void initUni(){
        tfNome.setText(uni.getNome());
    }


    public void fecharJanela() throws IOException {
        CadastroVoltar.getScene().getWindow().hide();
        FXMLLoader fxml = new FXMLLoader(main.class.getResource("Unidade-Table-View.fxml"));
        Scene scene = new Scene(fxml.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Unidade");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onConfirmar(ActionEvent actionEvent) throws IOException {
        DALUnidade DALConf = new DALUnidade();
        String msg = "";

        if(tfNome.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo nome vazio!\n";
        if(msg.isEmpty()) {
            if (getUni() == null) {
                Unidade uni = new Unidade(tfNome.getText());
                if (!DALConf.gravar(uni)) {
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
                Unidade uni = new Unidade(getUni().getID(), tfNome.getText());
                if (!DALConf.alterar(uni)) {
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
                setUni(null);
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

    public void onVoltar(ActionEvent actionEvent) throws IOException {
       fecharJanela();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isFlag()==true)
            initUni();
        else
            tfNome.setText(null);
    }
}
