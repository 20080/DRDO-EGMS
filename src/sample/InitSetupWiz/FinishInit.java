package sample.InitSetupWiz;


        import javafx.animation.FadeTransition;
        import javafx.animation.SequentialTransition;
        import javafx.application.Platform;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.layout.AnchorPane;
        import sample.Transitions.Fade;

        import java.io.File;
        import java.io.IOException;

public class FinishInit {

    @FXML
    private Button FinishButton;

    @FXML
    private AnchorPane ApLayout;

    @FXML
    void OnFinishAction() throws IOException {
        File file = new File("Resources/Stats.properties");
        if (!file.exists()){
            file.createNewFile();
        }
        FadeTransition fadeTransition = Fade.fade(1.0,1.0,0.0,ApLayout,null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e->{
//            BorderPane borderPane = null;
//            try {
//                borderPane = FXMLLoader.load(getClass().getResource(""));
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            ApLayout.getChildren().setAll(borderPane);
//
//            FadeTransition fadeTransition1 = Fade.fade(1.0,0.0,1.0,ApLayout,null);
//            SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition1);
//            sequentialTransition1.play();
        });

        System.exit(0);
        Platform.exit();

    }

}
