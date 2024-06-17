package br.fipp.botecofx;

import DB.Dals.DALGarcon;
import DB.SingletonDB;
import DB.entidade.Garcon;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.MaskFieldUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;


public class GarconFormController implements Initializable {

    public TextField tfNome;
    public TextField tfCpf;
    public TextField tfCep;
    public TextField tfEnd;
    public TextField tfUf;
    public TextField tfFone;
    public Button CadastroVoltar;
    public Button CadastroConfirmar;
    public TextField tfNumero;
    public TextField tfCidade;

    private static Garcon garcon = null;
    private static boolean flag;

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        GarconFormController.flag = flag;
    }

    public static Garcon getGarcon() {
        return garcon;
    }

    public static void setGarcon(Garcon garcon) {
        GarconFormController.garcon = garcon;
    }

    public void initGarcon()
    {
        tfNome.setText(garcon.getNome());
        tfCpf.setText(garcon.getCPF());
        tfCep.setText(garcon.getCEP());
        tfEnd.setText(garcon.getEndereco());
        tfUf.setText(garcon.getUF());
        tfFone.setText(garcon.getFone());
        tfCidade.setText(garcon.getCidade());
    }

    public void onConfirmar(ActionEvent event) throws IOException {
        DALGarcon DALConf = new DALGarcon();

        String msg = "";

        if(tfNome.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo nome vazio!\n";

        if(tfCpf.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo CPF vazio!\n";
        else if(tfCpf.getText().length() != 14)
            msg += "Campo CPF precisa ter 11 digitos!\n";

        if(tfCep.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo CEP vazio!\n";
        else if(tfCep.getText().length() != 9)
            msg += "Campo CEP precisa ter 8 digitos!\n";

        if(tfEnd.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo endereco vazio!\n";

        if(tfUf.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo UF vazio!\n";
        else if(tfUf.getText().length() != 2)
            msg += "Campo UF precisa ter 2 caracteres!\n";

        if(tfFone.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo telefone vazio!\n";
        else if(tfFone.getText().length() != 15 && tfFone.getText().length() != 14)
            msg += "Campo telefone precisa ter 11 ou 10 digitos!\n";

        if(tfCidade.getText() == null || tfNome.getText().isEmpty())
            msg += "Campo cidade vazio!\n";

        if(msg.isEmpty()) {
            if (getGarcon() == null) {
                Garcon gar = new Garcon(tfNome.getText(), tfCpf.getText(), tfEnd.getText(), tfCidade.getText(), tfUf.getText(), tfFone.getText(), tfCep.getText());
                if (!DALConf.gravar(gar)) {

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
                Garcon gar = new Garcon(getGarcon().getId(), tfNome.getText(), tfCpf.getText(), tfCep.getText(), tfEnd.getText(), tfCidade.getText(), tfUf.getText(), tfFone.getText());
                if (!DALConf.alterar(gar)) {
                    System.out.println("en trou ");
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
                setGarcon(null);
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
    public void fecharJanela() throws IOException {
        CadastroVoltar.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("garcon-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isFlag()==true)
            initGarcon();
        else{
            tfEnd.setText(null);
            tfCidade.setText(null);
            tfNumero.setText(null);
            tfUf.setText(null);
            tfFone.setText(null);
            tfCep.setText(null);
            tfCpf.setText(null);
        }
        MaskFieldUtil.cepField(tfCep);
        MaskFieldUtil.foneField(tfFone);
        MaskFieldUtil.cpfField(tfCpf);
        tfCep.setOnKeyTyped(e->{
            String textoCep = tfCep.getText();
            if(textoCep!= null && textoCep.length() == 9) {
                new Thread(new Task<Void>(){
                    @Override
                    protected Void call() throws Exception {
                        String sjson = consultaCep(tfCep.getText(), "json");
                        JSONObject json = new JSONObject(sjson);
                        tfEnd.setText(json.getString("logradouro"));
                        tfCidade.setText(json.getString("localidade"));
                        tfUf.setText(json.getString("uf"));
                        Platform.runLater(()->tfNumero.requestFocus());
                        return null;
                    }
                }).start();
            }
            else {
                tfEnd.setText("");
                tfCidade.setText("");
                tfUf.setText("");
            }
        });

    }
    public static String consultaCep(String cep, String formato)
    {
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("https://viacep.com.br/ws/"+ cep + "/"+formato+"/");
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                dados.append(s);
            br.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dados.toString();
    }

}
