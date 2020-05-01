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
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;
import sample.Transitions.Path;
import sample.sAlearts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Initial3 implements Initializable {

    @FXML
    private BorderPane BP3;


    @FXML
    private TextField JDBCField;

    @FXML
    private ToggleGroup Toggle2;

    @FXML
    private Button NextButton3;

    @FXML
    private RadioButton YesRadio;

    @FXML
    private RadioButton NoRadio;


    private String key="ConnectionString";

    @FXML
    void Next3ButtonAction() throws IOException {

        File file = new File("Resources/SoftwareSystemProperties/db.properties");
        if(!file.exists()){
            file.createNewFile();
            PropertyConfigs.SetProperty("Driver","com.mysql.jdbc.Driver",file.getPath(),null,null);
        }


            if (JDBCField.getText().isEmpty()){
               Alert alert= sAlearts.aleart(Alert.AlertType.ERROR,null,"Cannot proceed with empty String","Please Enter The String", Modality.APPLICATION_MODAL);
                        alert.showAndWait();
            }

            else {
                if(Toggle2.getSelectedToggle()==YesRadio){
                    PropertyConfigs.SetProperty(key,String.valueOf(JDBCField.getText()),
                            "Resources/SoftwareSystemProperties/db.properties",
                            null,null);
                    launchnext();

                }
                else if (Toggle2.getSelectedToggle()==NoRadio){
                    PropertyConfigs.SetProperty(key,String.valueOf(JDBCField.getText()+"&useSSL=false"),
                            "Resources/SoftwareSystemProperties/db.properties",
                            null,null);
                    PropertyConfigs.SetProperty("Driver","com.mysql.jdbc.Driver","Resources/SoftwareSystemProperties/db.properties",
                            null,null);
                    launchnext();
            }


        }




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PathTransition pathButton = Path.pathLinear(1.0,NextButton3.getLayoutX()+50,8.0,
                NextButton3.getLayoutX(),8.0,NextButton3);
        FadeTransition fadeButton = Fade.fade(1.0,0.0,1.0,NextButton3,null);
        ParallelTransition parallelTransition = new ParallelTransition(pathButton,fadeButton);
        parallelTransition.play();


    }

    private void launchnext()  {

        FadeTransition fadeT = Fade.fade(1.0,1.0,0.0,BP3,null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeT);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e->{
            BorderPane borderPane = new BorderPane();
            try {
                borderPane= FXMLLoader.load(getClass().getResource("ResourcesSetupWiz/Initial4.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            BP3.getChildren().setAll(borderPane);
            FadeTransition fadeTransition = Fade.fade(1.0,null,1.0,BP3,null);
            SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition);
            sequentialTransition1.play();
        });



    }
}
