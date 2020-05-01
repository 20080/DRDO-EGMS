package sample.InitSetupWiz;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import sample.AESEncryption;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;
import sample.Transitions.Path;
import sample.sAlearts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AnchorVerify implements Initializable{


    @FXML
    private Label VerficationLabel;

    @FXML
    private TextField Username1;


    @FXML
    private PasswordField Password1F;

    @FXML
    private PasswordField Password2f;

    @FXML
    private AnchorPane AnchorPanee;

    @FXML
    private VBox VboxId;

//    @FXML
//    private Button CreateButton;

    @FXML
    void OnCreateAction() throws IOException {

        if (Username1.getText().isEmpty()||Password1F.getText().isEmpty()||Password2f.getText().isEmpty()){
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,
                    "Please fill all fields","Fields cannot be left empty",Modality.APPLICATION_MODAL);
                    alert.show();
        }

        else if (!Password1F.getText().equals(Password2f.getText())){
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,
                    "Verify your password","Passwords do not match",Modality.APPLICATION_MODAL);
            alert.show();
        }
else if (Password1F.getText().equals(Password2f.getText())){
            File file = new File("Resources/PropertiesMain/Beta.properties");
            if (!file.exists())
                file.createNewFile();

            FadeTransition fd1= Fade.fade(.5,1.0,0.0,VboxId,null);
            PathTransition ph= Path.pathLinear(2.,VboxId.getLayoutX()+780,200.0,
                    VboxId.getLayoutX()+50,200.0,VboxId);
            ParallelTransition pt= new ParallelTransition(fd1,ph);

            SequentialTransition sqq= new SequentialTransition(pt);
            sqq.play();
            PropertyConfigs.SetProperty("AppUsr1",AESEncryption.encrypt(Username1.getText(),null,
                    true),"Resources/PropertiesMain/Beta.properties",null,null);
            PropertyConfigs.SetProperty("AppPass1",AESEncryption.encrypt(Password1F.getText(),null,
                    true),"Resources/PropertiesMain/Beta.properties",null,null);
            PropertyConfigs.SetProperty("UC",AESEncryption.encrypt(String.valueOf(1),null,
                    true),"Resources/PropertiesMain/Beta.properties",null,null);


            sqq.setOnFinished(e->{
                try {
                BorderPane ap= FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Initial6Final.fxml"));
                    AnchorPanee.getChildren().setAll(ap);


                    FadeTransition f = Fade.fade(0.9,0.0,1.0,AnchorPanee,null);

                    SequentialTransition sequentialTransition = new SequentialTransition(f);

                    sequentialTransition.play();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }




            });
        }



    }

     private String  EncryptedName;
     private String  EncryptedPassword;
     private String  DecryptedName;
     private String  DecryptedPassword;
     private String  tempKey;
     private String Pathandname="Resources/SecurityProperties/udDB.properties";
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VboxId.setLayoutX(VboxId.getLayoutX() - 350);


        EncryptedName = PropertyConfigs.getProperty("userName", Pathandname, null, null);
        EncryptedPassword = PropertyConfigs.getProperty("PassWord", Pathandname, null, null);
        FadeTransition fd = Fade.fade(1.5, 1.0, 0.0, VerficationLabel, null);
        SequentialTransition sq = new SequentialTransition(fd);
        sq.play();
        sq.setOnFinished(e -> {

            // for internal key


            if (PropertyConfigs.getProperty("Encryption", "Resources/SecurityProperties/udDB.properties"
                    , null, null).equals("true")) {
                DecryptedName = AESEncryption.decrypt(EncryptedName, null, true);
                DecryptedPassword = AESEncryption.decrypt(EncryptedPassword, null, true);
                Connection connection = null;
                try {
                    connection = DBconnection.DbConnection(DecryptedName, DecryptedPassword);

                } catch (CommunicationsException e3) {
                    Error(1, e3);

                } catch (SQLException e1) {

                    Error(0, e1);
                }


            }

            //If External Key Used

            else if (PropertyConfigs.getProperty("Encryption", "Resources/SecurityProperties/udDB.properties"
                    , null, null).equals("false")) {
                tempKey = PropertyConfigs.getProperty("cryptoKey", "Resources/SoftwareSystemProperties/cyptoTemp.properties",
                        null, null);
                String keydec = AESEncryption.decrypt(tempKey,null,true);

                DecryptedName = AESEncryption.decrypt(EncryptedName, keydec, false);
                DecryptedPassword = AESEncryption.decrypt(EncryptedPassword, keydec, false);
                Connection connection2 = null;
                try {
                    connection2 = DBconnection.DbConnection(DecryptedName, DecryptedPassword);

                } catch (CommunicationsException e3){
                        Error(1,e3);
                } catch (SQLException e1) {

                    Error(0,e1);
                }


            }




          ///VBox Fade In

            FadeTransition fd1 = Fade.fade(1.0, 0.0, 1.0, VboxId, null);
            PathTransition ph = Path.pathLinear(.5, VboxId.getLayoutX() + 50, 200.0,
                    VboxId.getLayoutX() + 780, 200.0, VboxId);
            ParallelTransition pt = new ParallelTransition(fd1, ph);

            SequentialTransition sqq = new SequentialTransition(pt);
            sqq.play();
        });

    }




    public void OnCreateAction2() {

    }



    private void Error(Integer o, Exception e) {

        if (o == 1) {
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null,
                    "Server is Down Please Start the DB server",
                    e.toString(),
                    Modality.APPLICATION_MODAL);
            alert.show();

                load("5");

        } else {

            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null,
                    "Provided Username And Password is bad Please Re-enter The Username and password",
                    e.toString(),
                    Modality.APPLICATION_MODAL);
            alert.show();
            load("4");


        }
    }

     private void load(String Stage) {
        try {
            BorderPane borderPane = FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Initial"+Stage+".fxml"));
            AnchorPanee.getChildren().setAll(borderPane);

        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


}


