package sample.InitSetupWiz;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Transitions.Fade;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Initial5 implements Initializable {



    @FXML
    private BorderPane BP2;


    @FXML
    private Label TestingLabel;


    @FXML
    void Next2ButtonAction() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
BP2.setBottom(null);

        FadeTransition fadeTransitionIN = Fade.fade(0.8, 0.0, 1.0, TestingLabel, null);
        FadeTransition fadeTransitionOUT = Fade.fade(0.8, null, 0.0, TestingLabel, null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeTransitionIN, fadeTransitionOUT);
        sequentialTransition.setCycleCount(3);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e -> {

            try {

                AnchorPane ap = FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Internals/AnchorVerify.fxml"));
                BP2.getChildren().setAll(ap);
                FadeTransition fd = Fade.fade(1.5,0.0,1.0,BP2,null);
                SequentialTransition sq= new SequentialTransition(fd);
                sq.play();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });


    }

}
