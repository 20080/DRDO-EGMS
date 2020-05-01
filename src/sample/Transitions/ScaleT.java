package sample.Transitions;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Objects;


public class ScaleT {

    public static ScaleTransition scale(Double duration, Node node, Double setToX, Double setToY, Double setToZ){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(node);
        scaleTransition.setDuration(Duration.seconds(duration));
        if (!Objects.equals(setToX,null)){
        scaleTransition.setToX(setToX);
        }
        if (!Objects.equals(setToY,null)) {
            scaleTransition.setToY(setToY);
        }
        if(!Objects.equals(setToZ,null)) {
            scaleTransition.setToZ(setToZ);
        }

        return scaleTransition;
    }

}
