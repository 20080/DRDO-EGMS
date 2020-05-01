package sample.WelcomeScr;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import sample.Transitions.Fade;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScr implements Initializable{


    @FXML
    private ImageView Imageview;

    @FXML
    private Label FXLABLE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        File file = new File(String.valueOf(getClass().getClassLoader().getResource("src/sample/Drawables/1.jpg")));
//        File file = new File("src/sample/Drawables/1.jpg");
//        File file = new File(getClass().getClassLoader().getResource("src/sample/Drawables/1.jpg").getFile());


        //Fixed image

        Image image = new Image(String.valueOf(getClass().getClassLoader().getResource("src/sample/Drawables/1.jpg")));

//        Image image = new Image(String.valueOf(getClass().getClassLoader().getResource("src/sample/Drawables/1.jpg")));
        Imageview.setImage(image);

        FadeTransition fadeTransitionIN = Fade.fade(0.5,0.0,1.0,FXLABLE,null);
        FadeTransition fadeTransitionOUT= Fade.fade(0.5,null,0.0,FXLABLE,null);
        SequentialTransition sequentialTransition= new SequentialTransition(fadeTransitionIN,fadeTransitionOUT);
        sequentialTransition.setCycleCount(8);
        sequentialTransition.play();
    }
}
