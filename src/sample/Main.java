package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Entry Gate Management System");
//           Scene scene = new Scene(root);

        primaryStage.setScene(new Scene(root));
//        scene.getStylesheets().addAll("src/sample/CSS.css");
        File file = new File("Resources/Stats.properties");
        if (file.exists()) {

            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.show();
        }
        else {
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
