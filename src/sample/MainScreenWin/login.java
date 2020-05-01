package sample.MainScreenWin;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import sample.AESEncryption;
import sample.CustomCrypto;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class login implements Initializable {


    @FXML
    private Button NextButton2;

    @FXML
    private MenuBar MenuBarID;

    @FXML
    private Label SetupLabel2;

    @FXML
    private BorderPane BP2;

    @FXML
    private VBox LoginVBox;


    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PasswordField;

    private boolean done = false;

    CustomCrypto customCrypto;

    @FXML
    void OnPAsswordAction() {

        p = 1;
        i = 0;
        nextMainAction();

    }


    @FXML
    void Next2ButtonAction() {


        i = 0;
        p = 1;

        nextMainAction();


    }

    static boolean privileged = true;
    @FXML
    private MenuItem AdvancedPrivLogin;

    @FXML
    void OnAdvancedPrivLogin() throws IOException {
//
//        if (!Window1DataEntry.auth){
//            AdvancedPrivLogin.setDisable(true);
//        }
//if (Window1DataEntry.auth){
//            AdvancedPrivLogin.setDisable(false);
//}
        if (privileged) {
            Window1DataEntry.showAndFade(2, SetupLabel2);
            if (!file.exists()) {
                SetupLabel2.setText("Please provide DBA authentication");
            }
            if (file.exists()) {
                SetupLabel2.setText("Please Enter Your Credentials(Advanced login!)");
            }
            AdvancedPrivLogin.setText("Reception Login");
            privileged = false;
            Window1DataEntry.showAndFade(1, SetupLabel2);


        } else if (!privileged) {
            Window1DataEntry.showAndFade(2, SetupLabel2);
            SetupLabel2.setText("Press long in to proceed");
            AdvancedPrivLogin.setText("Privileged login");
            System.out.println("2");
            privileged = true;
            Window1DataEntry.showAndFade(1, SetupLabel2);
        }

    }

    File file = new File("Resources/PropertiesMain/OtherUsr.properties");
    private Connection connection;

    private static int i = 0;
    private static int p = 1;

    private File fileAction = new File("Resources/PropertiesMain/Beta.properties");

    void notExistsAction(){
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        try {
            connection = DBconnection.DbConnection(username, password);

        } catch (CommunicationsException e3){
            Error(1,e3);
        } catch (SQLException e1) {

            Error(0, e1);
        }

        try {
            connection.close();


            String pass =customCrypto.CryptoDec(null,
                    "Resources/SecurityProperties/udDB.properties","PassWord");
            String User =customCrypto.CryptoDec(null,
                    "Resources/SecurityProperties/udDB.properties","userName");

            if (Objects.equals(username,User)&&Objects.equals(pass,password)){

                System.out.println("yes");


                FadeTransition fadeT = Fade.fade(1.0, 1.0, 0.0, BP2, null);
                SequentialTransition sequentialTransition = new SequentialTransition(fadeT);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(e -> {
                    AnchorPane anchorPane= new AnchorPane();
                    try {
                        anchorPane = FXMLLoader.load(getClass().getResource("NewUserCreation.fxml"));


                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    BP2.getChildren().setAll(anchorPane);
                    FadeTransition fadeTransition = Fade.fade(1.0, null, 1.0, BP2, null);
                    SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition);
                    sequentialTransition1.play();
                });


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void nextMainAction()  {

        if (!privileged) {


            if (file.exists()) {

ContentMainAction(file,"RefernceDataEntry.fxml","RefernceDataEntry.fxml");
            }

            else if (!file.exists()) {
                notExistsAction();
            }

        }


        if (privileged) {

            ContentMainAction(fileAction,"Window1DataEntry.fxml","NewUserCreation.fxml");

        }
    }

    private void ContentMainAction(File file, String locationOnTrue, String locationOnFalse)  {
        String Enc = AESEncryption.encrypt(UsernameField.getText(), null, true);
        String PEnc = AESEncryption.encrypt(PasswordField.getText(), null, true);
        System.out.println(file.getPath());


//        String getEnc = null;
//        String getPEnc= null;

        String getEnc = PropertyConfigs.getProperty("AppUsr" + i + "", file.getPath(),
                null, null);
        String getPEnc = PropertyConfigs.getProperty("AppPass" + i + "", file.getPath(),
                null, null);
//        while (!Enc.equals(getEnc)&&!PEnc.equals(getPEnc)){
//
//            System.out.println(i);
//            i++;
//        }


        //Transitions
        PathTransition pt = Path.pathLinear(1.2, 300.0, 30.0, 300.0, 150., BottomHB);
        FadeTransition fadeTransition = Fade.fade(1.0, 1.0, 0.0, BottomHB, null);
        ParallelTransition parallelTransition = new ParallelTransition(pt, fadeTransition);
        SequentialTransition sequentialTransition = new SequentialTransition(parallelTransition);

        FadeTransition fadeTransition3 = Fade.fade(1.0, 1.0, 0.0, MenuBarID, null);
        //middle Cpane
        PathTransition pt1 = Path.pathLinear(1.2, 250.0, 150.0, 250.0, -150., LoginVBox);

        FadeTransition fadeTransition1 = Fade.fade(.70, 1.0, 0.0, LoginVBox, null);
        ParallelTransition parallelTransition1 = new ParallelTransition(pt1, fadeTransition1, fadeTransition3);
        SequentialTransition sequentialTransition1 = new SequentialTransition(parallelTransition1);
        ParallelTransition parallelTransition2 = new ParallelTransition(sequentialTransition, sequentialTransition1);

//        String prop = PropertyConfigs.getProperty("Rule", "Resources/PropertiesMain/Alpha.properties",
//                null, null);

        if (Enc.equals(getEnc) && PEnc.equals(getPEnc) && Window1DataEntry.auth) {
            done = true;
            parallelTransition2.play();
            PropertyConfigs.SetProperty("CurrentUser", Enc, file.getPath(),
                    null, null);
            parallelTransition2.setOnFinished(e -> {


                try {
                    BorderPane borderPane = FXMLLoader.load(getClass().getResource(locationOnTrue));
                    FadeTransition fadeTransition2 = Fade.fade(1.0, 0.0, 1.0, BP2, null);
                    SequentialTransition sequentialTransition2 = new SequentialTransition(fadeTransition2);
                    BP2.getChildren().setAll(borderPane);
                    sequentialTransition2.play();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            });


        } else if (Enc.equals(getEnc) && PEnc.equals(getPEnc) && !Window1DataEntry.auth) {


            done = true;
            parallelTransition2.play();
            parallelTransition2.setOnFinished(e -> {


                try {
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(locationOnFalse));
                    FadeTransition fadeTransition2 = Fade.fade(1.0, 0.0, 1.0, BP2, null);
                    SequentialTransition sequentialTransition2 = new SequentialTransition(fadeTransition2);
                    BP2.getChildren().setAll(anchorPane);
                    sequentialTransition2.play();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            });

        } else {


            int limit = Integer.parseInt(AESEncryption.decrypt(PropertyConfigs.getProperty("UC", "Resources/PropertiesMain/Beta.properties",
                    null, null), null, true));


            while (i <= limit) {
                i++;
//                System.out.println("Limit == "+limit);
//                System.out.println("P== "+p);
//                System.out.println("I== "+i);
                this.ContentMainAction(file, locationOnTrue, locationOnFalse);
            }

            p++;
//            System.out.println("P outt ==" +p);
            if (!done && p > limit) {
                Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null, "Error with password or username", "" +
                        "Please Enter Correct username and password", Modality.APPLICATION_MODAL);
                System.out.println("This Also");
                p = 0;
                alert.show();

            }

        }
//
//        else {
//            if (p == 1) {
//                Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null, "Error with password or username", "" +
//                        "Please Enter Correct username and password", Modality.APPLICATION_MODAL);
//                System.out.println("thisErroro");
//                alert.show();
//
//            }
//        }
    }


    @FXML
    private HBox BottomHB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PathTransition pathButton = Path.pathLinear(1.0, NextButton2.getLayoutX() + 50, 8.0,
                NextButton2.getLayoutX() + 8, 8.0, NextButton2);
        FadeTransition fadeButton = Fade.fade(1.0, 0.0, 1.0, NextButton2, null);
        ParallelTransition parallelTransition = new ParallelTransition(pathButton, fadeButton);

        //vbox
        PathTransition PathVbox = Path.pathLinear(1.0, LoginVBox.getLayoutX() - 50, 150.0,
                LoginVBox.getLayoutX() + 250, 150.0, LoginVBox);
        FadeTransition FadeVbox = Fade.fade(1.0, 0.0, 1.0, LoginVBox, null);
        ParallelTransition parallelTransitionVb = new ParallelTransition(PathVbox, FadeVbox, parallelTransition);
        parallelTransitionVb.play();

    }

    //todo fo sql exception




    private void Error(Integer o, Exception e) {

        if (o == 1) {
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null,
                    "Server is Down Please Start the DB server",
                    e.toString(),
                    Modality.APPLICATION_MODAL);
            alert.show();


        } else {

            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null,
                    "Provided Username And Password is bad Please Re-enter The Username and password",
                    e.toString(),
                    Modality.APPLICATION_MODAL);
            alert.show();

        }


    }
}
