package sample.Transitions;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Path {

    public static PathTransition pathLinear(Double duration, Double StartXpos,
                                            Double StartYpos, Double EndXpos, Double EndYpos ,Node node )

    {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(duration));
        pathTransition.setNode(node);
        pathTransition.setPath(new Line(StartXpos,StartYpos,EndXpos,EndYpos));

        return pathTransition;


    }

}
