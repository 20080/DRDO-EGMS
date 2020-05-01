package sample.InitSetupWiz;


import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.scene.layout.VBox;
import sample.Transitions.*;
import sample.sAlearts;

import java.util.ResourceBundle;

public class Initial1 implements Initializable {

    @FXML
    private BorderPane BP1;

    @FXML
    private Label WelLabel2;

    @FXML
    private Button NextButton1;

    @FXML
    private Label WelLabel1;

    @FXML
    private ImageView ImageID1;

    @FXML
    private Label SetupLabel;

    @FXML
    private VBox WelcomeVbox;



    @FXML
    void Next1ButtonAction() throws IOException {
        FadeTransition fadeTransition1 = Fade.fade(1.0,1.0,0.0,BP1,null);

        SequentialTransition sequentialTransitions = new SequentialTransition(fadeTransition1);
        sequentialTransitions.play();

        sequentialTransitions.setOnFinished(e->{
            try {

                    File file = new File("Resources/PropertiesMain/Alpha.properties");



                if(!file.exists()){
                    file.createNewFile();
                }

               BorderPane borderPane=FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Initial2.fxml"));
                BP1.getChildren().setAll(borderPane);

                FadeTransition fadeTransition2 = Fade.fade(1.0,0.0,1.0,BP1,null);
                SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition2);
                sequentialTransition.play();

            } catch (IOException e1) {
               Alert alert= sAlearts.aleart(Alert.AlertType.ERROR,String.valueOf(e1),"Error while creating file",String.valueOf(e1),null);
                alert.show();
                e1.printStackTrace();
            }

        });


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Button Next
        PathTransition pathButton = Path.pathLinear(1.0,NextButton1.getLayoutX()+50,8.0,
                NextButton1.getLayoutX(),8.0,NextButton1);
        FadeTransition fadeButton = Fade.fade(2.0,0.0,1.0,NextButton1,null);
        ParallelTransition parallelTransition = new ParallelTransition(pathButton,fadeButton);
        //Label 1

        PathTransition PathLab1 = Path.pathLinear(1.1,WelLabel1.getLayoutX()-100,0.0,
                WelcomeVbox.getLayoutX()+50,0.0,WelLabel1);
        FadeTransition FadeLab1= Fade.fade(2.0,0.0,1.0,WelLabel1,null);

        ParallelTransition parallelTransition1 = new ParallelTransition(PathLab1,FadeLab1,parallelTransition);

        //Label 2

        PathTransition PathLab2 = Path.pathLinear(1.1,WelLabel2.getLayoutX()-80,0.0,
                WelcomeVbox.getLayoutX()+142,0.0,WelLabel2);
        FadeTransition FadeLab2= Fade.fade(2.0,0.0,1.0,WelLabel2,null);

        FadeTransition fadeSetupLa= Fade.fade(2.0,.0,1.,SetupLabel,null);

        ParallelTransition parallelTransition2 = new ParallelTransition(PathLab2,FadeLab2,parallelTransition1,fadeSetupLa);
        parallelTransition2.play();
    }
}
