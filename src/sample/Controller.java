package sample;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Transitions.Fade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BorderPane Borderpane;

    private static boolean condition = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File file1 = new File("Resources/SoftwareSystemProperties/cyptoTemp.properties");
        try {
            Files.deleteIfExists(file1.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!condition) {
            try {
                AnimationScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //To Do
        }

    }



    public void AnimationScreen() throws IOException {
        condition = true;

        AnchorPane ap = null;
        try {
            ap = FXMLLoader.load(getClass().getResource("WelcomeScr/SplashScrF.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Borderpane.getChildren().setAll(ap);
            FadeTransition fadeIn = sample.Transitions.Fade.fade(3.0, 0.0, 1.0, ap, null);
            FadeTransition fadeout = sample.Transitions.Fade.fade(3.0, null, 0.0, ap, null);
            SequentialTransition sq = new SequentialTransition(fadeIn, fadeout);
            sq.play();
            sq.setOnFinished(e-> {
                BorderPane bp = null;
                File file = new File("Resources/Stats.properties");

                if (file.exists()) {
                    try {
                        bp= FXMLLoader.load(getClass().getResource("MainScreenWin/Login.fxml"));
                        Borderpane.getChildren().setAll(bp);
                        Borderpane.getChildren().setAll(bp);
                        FadeTransition fadeTransition = Fade.fade(1.0, 0.0, 1.0, bp, null);
                        SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
                        sequentialTransition.play();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {

                        bp = FXMLLoader.load(getClass().getResource("InitSetupWiz/ResourcesSetupWiz/Initial1.fxml"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Borderpane.getChildren().setAll(bp);
                    FadeTransition fadeTransition = Fade.fade(1.0, 0.0, 1.0, bp, null);
                    SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
                    sequentialTransition.play();
                }
            });


    }
}