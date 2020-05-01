package sample.InitSetupWiz;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import sample.Transitions.Fade;
import sample.Transitions.Path;
import sample.sAlearts;
import sample.PropertiesConfiguration.PropertyConfigs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Initial2 implements Initializable {

    @FXML
    private Button NextButton2;

    @FXML
    private BorderPane BP2;

    @FXML
    private RadioButton YesRadio;

    @FXML
    private ToggleGroup Toggle1;

    @FXML
    private RadioButton NoRadio;

    private String key= "MultiKey";

    private File file = new File("Resources/PropertiesMain/CameraConfiguration.properties");

    @FXML
    void Next2ButtonAction() throws IOException {

        if (!file.exists())
            file.createNewFile();
        //setting resolution
        PropertyConfigs.SetProperty("Width", String.valueOf(320),file.getPath(),null,null);
        PropertyConfigs.SetProperty("Height", String.valueOf(240),file.getPath(),null,null);

        //setting password rule of opening windows
        PropertyConfigs.SetProperty("Rule","true","Resources/PropertiesMain/Alpha.properties",
                null,null);

        //setting desired option of the pain

        if (Toggle1.getSelectedToggle()==YesRadio){
            PropertyConfigs.SetProperty(key,"true",
                    "Resources/PropertiesMain/Alpha.properties",null,null);
            launchnext();

        }
        else if (Toggle1.getSelectedToggle()==NoRadio){
            PropertyConfigs.SetProperty(key,"false",
                    "Resources/PropertiesMain/Alpha.properties",null,null);
            launchnext();
        }

        else if (Toggle1.getSelectedToggle()==null){
           Alert alert = sAlearts.aleart(Alert.AlertType.CONFIRMATION,null,"Default Setting will be used","Do you want to continenew", Modality.APPLICATION_MODAL);
            alert.showAndWait();

           if(alert.getResult()==ButtonType.OK){
               PropertyConfigs.SetProperty(key,"true",
                       "Resources/PropertiesMain/Alpha.properties",null,null);
               launchnext();
           }
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PathTransition pathButton = Path.pathLinear(1.0,NextButton2.getLayoutX()+50,8.0,
                NextButton2.getLayoutX(),8.0,NextButton2);
        FadeTransition fadeButton = Fade.fade(1.0,0.0,1.0,NextButton2,null);
        ParallelTransition parallelTransition = new ParallelTransition(pathButton,fadeButton);
        parallelTransition.play();
    }

    private void launchnext()  {

        FadeTransition fadeT = Fade.fade(1.0,1.0,0.0,BP2,null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeT);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e->{
            BorderPane borderPane = new BorderPane();
            try {
                borderPane= FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Initial3.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            BP2.getChildren().setAll(borderPane);
            FadeTransition fadeTransition = Fade.fade(1.0,null,1.0,BP2,null);
            SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition);
            sequentialTransition1.play();
        });



    }

}
