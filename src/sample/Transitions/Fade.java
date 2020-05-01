package sample.Transitions;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Objects;


public class Fade {



    public static FadeTransition fade(Double TimeInSeconds, Double fromValue, Double ToValue, Node node, Integer CycleCount){

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(TimeInSeconds));
        fadeTransition.setNode(node);
        if (!Objects.equals(fromValue,null)){

            fadeTransition.setFromValue(fromValue);
        }

        if (!Objects.equals(ToValue,null)){
            fadeTransition.setToValue(ToValue);
        }
        if(!Objects.equals(CycleCount,null)) {
            fadeTransition.setCycleCount(CycleCount);
            }


        return fadeTransition;
    }


}
