package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import sample.PropertiesConfiguration.PropertyConfigs;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomCryptoPrompt implements Initializable {

    @FXML
    private PasswordField Field;

    @FXML
    private javafx.scene.control.Button Button;

    @FXML
    private CheckBox Tickk;

    @FXML
    void OnTickAction() {

    }
static String main;
    static String keyMain;

    public String ButtonAction() {


        main =Field.getText();
        if (Tickk.isSelected()){

            keyMain= AESEncryption.encrypt(main,null,true);
            PropertyConfigs.SetProperty("EncryptKey",AESEncryption.encrypt(main,null,true),
                    "Resources/PropertiesMain/Alpha.properties"
                    ,null,null);
        }
        else if (!Tickk.isSelected()){
            PropertyConfigs.SetProperty("EncryptKey","null",
                    "Resources/PropertiesMain/Alpha.properties"
                    ,null,null);
        }
        Stage stage = (Stage) Button.getScene().getWindow();
        stage.close();
        return main;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
main = null;
    }
}
