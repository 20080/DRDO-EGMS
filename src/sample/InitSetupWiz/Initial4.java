package sample.InitSetupWiz;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import sample.AESEncryption;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;
import sample.Transitions.Path;
import sample.sAlearts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class Initial4 implements Initializable {

    @FXML
    private BorderPane BP3;

    @FXML
    private AnchorPane AnchorPanea;

    @FXML
    private TextField UsernameJDBCfield;

    @FXML
    private PasswordField PasswordJDBCfiedl;

    @FXML
    private ToggleGroup Toggle2;

    @FXML
    private Button NextButton4;

    @FXML
    private RadioButton YesRadio;


    @FXML
    private RadioButton NoRadio;


    private String pathTemp = "Resources/SoftwareSystemProperties/cyptoTemp.properties";

    private File file = new File(pathTemp);

    private String UserPath = "Resources/SecurityProperties/udDB.properties";

    private String PropertyKeyName = "userName";

    private String PropertyPassName = "PassWord";

    private String EncryptedUsername, EncryptedPassword;

    @FXML
    void Next4ButtonAction() throws IOException {

        if (UsernameJDBCfield.getText().isEmpty() || PasswordJDBCfiedl.getText().isEmpty()
                || UsernameJDBCfield.getText().equals("") || PasswordJDBCfiedl.getText().equals("") ||
                UsernameJDBCfield.getText().equals(null) || PasswordJDBCfiedl.getText().equals(null)) {

            Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION, null, "Username and password can't be " +
                    "empty or null", "Please fill fields", Modality.APPLICATION_MODAL);
            alert.showAndWait();

        } else if (YesRadio.isSelected() && !file.exists()) {

            Alert alert = sAlearts.aleart(Alert.AlertType.WARNING, null, "Key must be entered and set in order to proceed else select no",
                    "Either select no or enter key and Set", Modality.APPLICATION_MODAL);
            alert.showAndWait();

        } else if (NoRadio.isSelected() || Toggle2.getSelectedToggle() == null) {
            PropertyConfigs.SetProperty("Encryption", "true", UserPath, null, null);
            EncryptedUsername = AESEncryption.encrypt(UsernameJDBCfield.getText(), null,true);
            EncryptedPassword = AESEncryption.encrypt(PasswordJDBCfiedl.getText(), null,true);

            PropertyConfigs.SetProperty(PropertyKeyName, EncryptedUsername, UserPath, null, null);
            PropertyConfigs.SetProperty(PropertyPassName, EncryptedPassword, UserPath, null, null);

            launchnext();

        } else if (YesRadio.isSelected() && file.exists()) {
            PropertyConfigs.SetProperty("Encryption", "false", UserPath, null, null);
            String EncryptedKey = PropertyConfigs.getProperty("cryptoKey", "Resources/SoftwareSystemProperties/cyptoTemp.properties",
                    null, null);
            String DecKey = AESEncryption.decrypt(EncryptedKey, null,true);
            //Can Add DEC key
            EncryptedUsername = AESEncryption.encrypt(UsernameJDBCfield.getText(), DecKey,false);
            EncryptedPassword = AESEncryption.encrypt(PasswordJDBCfiedl.getText(), DecKey,false);
            PropertyConfigs.SetProperty(PropertyKeyName, EncryptedUsername, UserPath, null, null);
            PropertyConfigs.SetProperty(PropertyPassName, EncryptedPassword, UserPath, null, null);

            launchnext();
        }


    }

    private Node node;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File file1 = new File(UserPath);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PathTransition pathButton = Path.pathLinear(1.0, NextButton4.getLayoutX() + 50, 8.0,
                NextButton4.getLayoutX(), 8.0, NextButton4);
        FadeTransition fadeButton = Fade.fade(1.0, 0.0, 1.0, NextButton4, null);
        ParallelTransition parallelTransition = new ParallelTransition(pathButton, fadeButton);
        parallelTransition.play();
        node = BP3.getCenter();

    }

    private static boolean main = false;

    public void OnYesTo() {
        PropertyConfigs.SetProperty("Encryption", "false", UserPath, null, null);
        main = true;

        try {
            AnchorPanea = FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Internals/Anchor.fxml"));
            BP3.setCenter(AnchorPanea);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FadeTransition fadeTransition = Fade.fade(1.0, 0.0, 1.0, AnchorPanea, null);
        PathTransition pathTransition = Path.pathLinear(.5, 300.0, 30.0, 300.0, 50.0, BP3.getBottom());

        ParallelTransition pt = new ParallelTransition(fadeTransition, pathTransition);
        pt.play();

    }

    boolean isMain = false;

    public void OnNoTog() throws IOException {
        PropertyConfigs.SetProperty("Encryption", "true", UserPath, null, null);
        if (main) {
            PathTransition pathTransition = Path.pathLinear(.5, 300.0, 50.0, 300.0, 30.0, BP3.getBottom());
            FadeTransition fadeTransition = Fade.fade(1.0, 1.0, 0.0, AnchorPanea, null);
            ParallelTransition pt = new ParallelTransition(fadeTransition, pathTransition);
            pt.play();
            Files.deleteIfExists(file.toPath());
            pt.setOnFinished(e -> BP3.setCenter(node));


        }

    }

    private void launchnext() {

        FadeTransition fadeT = Fade.fade(1.0, 1.0, 0.0, BP3, null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeT);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e -> {
            BorderPane borderPane = new BorderPane();
            try {
                borderPane = FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Initial5.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            BP3.getChildren().setAll(borderPane);
            FadeTransition fadeTransition = Fade.fade(1.0, null, 1.0, BP3, null);
            SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition);
            sequentialTransition1.play();
        });


    }
}

