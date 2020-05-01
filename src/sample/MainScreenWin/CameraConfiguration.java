package sample.MainScreenWin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import sample.PropertiesConfiguration.PropertyConfigs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import sample.sAlearts;

public class CameraConfiguration implements Initializable {


    @FXML
    private TextField HeightBox;

    @FXML
    private RadioButton DefaultRadio;


    @FXML
    private TextField WidthBox;

    @FXML
    private RadioButton MultiRadio;


    File file = new File("Resources/PropertiesMain/CameraConfiguration.properties");

    @FXML
    void OnSetAction1StSet() throws IOException {


        if (!file.exists())
            file.createNewFile();

        double width = Double.parseDouble(WidthBox.getText());
        double height = Double.parseDouble(HeightBox.getText());


        if ((width/height)==320.0/240.0){

            int widthInt= Integer.parseInt(WidthBox.getText());
            int HeightInt= Integer.parseInt(HeightBox.getText());

            PropertyConfigs.SetProperty("Width", String.valueOf(widthInt),file.getPath(),null,null);
            PropertyConfigs.SetProperty("Height", String.valueOf(HeightInt),file.getPath(),null,null);

            Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION,null,"Resolution changed",
                    "Restart the software to apply the changes", Modality.APPLICATION_MODAL);
            alert.showAndWait();

        }
        else {
            Alert alert =sAlearts.aleart(Alert.AlertType.ERROR,null,"Non-standard format",
                    "Resolution must be in standard format like 320x240,640x480",Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }

    }



    @FXML
    void OnSetActionCameraMode() throws IOException {


        if(MultiRadio.isSelected()){
            PropertyConfigs.SetProperty("MultiKey","true",
                    "Resources/PropertiesMain/Alpha.properties",null,null);

            Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION,null,"Property changed",
                    "Restart the software to apply the changes", Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }

        else if (DefaultRadio.isSelected()){
            PropertyConfigs.SetProperty("MultiKey","false",
                    "Resources/PropertiesMain/Alpha.properties",null,null);
            Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION,null,"Property changed",
                    "Restart the software to apply the changes", Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WidthBox.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d")) {

                WidthBox.setText(newValue.replaceAll("[^\\d]",""));

            }
        });

        HeightBox.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d")) {

                HeightBox.setText(newValue.replaceAll("[^\\d]",""));

            }
        });

        DefaultRadio.setSelected(true);
    }
}
