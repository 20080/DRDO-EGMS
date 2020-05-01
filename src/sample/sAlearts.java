package sample;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

import java.util.Objects;

public class sAlearts {

    public static Alert aleart(AlertType alertType, String message, String HeaderText, String Content, Modality modal){

        Alert alert= new Alert(alertType);
        if(!Objects.equals(HeaderText,null)){
            alert.setHeaderText(HeaderText);
        }
        if(!Objects.equals(Content,null)){
            alert.setContentText(Content);
        }
        if(!Objects.equals(modal,null)){
            alert.initModality(modal);
        }

        return alert;
    }

}
