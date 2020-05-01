package sample.InitSetupWiz.InternalsClass;

/**
 * Created by Suraj on 4/30/2017.
 */
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import sample.AESEncryption;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;
import sample.sAlearts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AnchorpString implements Initializable {

    @FXML
    private PasswordField Fiedl2;

    @FXML
    private PasswordField Field1;
    @FXML
    private AnchorPane Anchor;

    @FXML
    private ImageView ImageID;

    @FXML
   void OnSetAction(ActionEvent event) {

        if (!Objects.equals(Field1.getLength(),16)){


            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,"null","Invalid keySize",
                    "not 16 bytes please re enter",Modality.APPLICATION_MODAL);
            alert.showAndWait();
            FadeTransition fadeTransition = Fade.fade(0.5,1.0,0.0,Anchor,null);
            FadeTransition fadeTransition1 = Fade.fade(0.5,0.0,1.0,Anchor,null);
            SequentialTransition st1 = new SequentialTransition(fadeTransition,fadeTransition1);
            st1.play();
            Field1.clear();
            Fiedl2.clear();
        }
   else if(!Objects.equals(String.valueOf(Field1.getText()),String.valueOf(Fiedl2.getText()))){
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,"null","Invalid keySize",
                    "Keys did not match",Modality.APPLICATION_MODAL);
            alert.showAndWait();
            FadeTransition fadeTransition = Fade.fade(0.5,1.0,0.0,Anchor,null);
            FadeTransition fadeTransition1 = Fade.fade(0.5,0.0,1.0,Anchor,null);
            SequentialTransition st1 = new SequentialTransition(fadeTransition,fadeTransition1);
            st1.play();

            Field1.clear();
            Fiedl2.clear();


        }
        else if ((Field1.getLength() == 16 || Fiedl2.getLength() == 16 || Objects.equals(Fiedl2.getText(), Field1.getText())))
        {

             File file = new File("Resources/SoftwareSystemProperties/cyptoTemp.properties");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                String keyEn = AESEncryption.encrypt(Fiedl2.getText(), null,true);

        PropertyConfigs.SetProperty("cryptoKey", keyEn, "Resources/SoftwareSystemProperties/cyptoTemp.properties",
                null, null);
        Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION,null,"Key Sucesfully submitted",
                "Key Submission for encryption done take a backup of the provided key!",Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

        }




    @FXML
    void OneFiledActionRelesed(){

        if (Field1.getLength()>16){
            Field1.clear();
            Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION,null,"Length of key must be 16",
                    "Re enter the key", Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }






    }

    private Image cross = new Image(String.valueOf(getClass().getClassLoader().getResource("src/sample/Drawables/Cross.png")));
    private Image tick = new Image(String.valueOf(getClass().getClassLoader().getResource("src/sample/Drawables/Tick.png")));


    public static boolean main= false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public void TwoFiledActionRelesed() {



        if (Fiedl2.getLength() > 16) {
            Field1.clear();
            Fiedl2.clear();
            Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION, null, "Length of key must be 16",
                    "Re enter the key", Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }


        if (Field1.getLength() < 16 || Fiedl2.getLength() < 16 || !Objects.equals(Fiedl2.getText(), Field1.getText())) {
            ImageID.setImage(cross);

        } else if ((Field1.getLength() == 16 || Fiedl2.getLength() == 16 || Objects.equals(Fiedl2.getText(), Field1.getText()))) {

            ImageID.setImage(tick);


        }
    }

}

