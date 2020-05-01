package sample.InitSetupWiz;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.CustomCrypto;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class InitialOver implements Initializable{

    @FXML
    private Label DBLabel;

    @FXML
    private AnchorPane APE;

    CustomCrypto customCrypto;

    private static String CreateRefrenceQuery;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String pass =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","PassWord");
        String User =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","userName");

        Connection connection= null;
        try {
            connection = DBconnection.DbConnection(User,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            String tablename = PropertyConfigs.getProperty("TableName","Resources/PropertiesMain/Alpha.properties",
                    null,null);
            String schemaName =PropertyConfigs.getProperty("SchemaName","Resources/PropertiesMain/Alpha.properties",
                    null,null);
            String SchemaQuery ="CREATE SCHEMA "+schemaName+" ;";

            String CreateTableQuery =" CREATE TABLE "+schemaName+"."+tablename+" (VISITOR_ID int NOT NULL AUTO_INCREMENT,NAME VARCHAR (50), ADDRESS VARCHAR (300), NATIONALITY VARCHAR (15)," +
                    "PURPOSE VARCHAR (20), WHOM_TO_MEET VARCHAR (20), SCIENTIST_GRADE VARCHAR (60), SITTING_PLACE VARCHAR (20), ROOM_NO VARCHAR(10)," +
                    "DATE VARCHAR (20),TIME VARCHAR (20), COMPANY VARCHAR(60) ,PHONE_NO VARCHAR (15),PHOTO_BLOB LONGBLOB, USER_RESEPTION VARCHAR (50), PRIMARY KEY (VISITOR_ID))";

            String path ="Resources/PropertiesMain/Gamma.properties";

            if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                    PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){

                String RefTableName = PropertyConfigs.getProperty("ReferenceDBtable",path,null,null);

                CreateRefrenceQuery =" CREATE TABLE "+schemaName+"."+RefTableName+"(VISITOR_ID int NOT NULL AUTO_INCREMENT,NAME VARCHAR (50), ADDRESS VARCHAR (300)," +
                        "PURPOSE VARCHAR (20), WHOM_TO_MEET VARCHAR (20), SCIENTIST_GRADE VARCHAR (60), SITTING_PLACE VARCHAR (20)," +
                        "DATE VARCHAR (20),TIME VARCHAR (20), COMPANY VARCHAR(60) ,PHONE_NO VARCHAR (15),PHOTO_BLOB LONGBLOB, USER_SESSION VARCHAR (50), PRIMARY KEY (VISITOR_ID))";


            }

           else if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                    !PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){



                String RefTableName = PropertyConfigs.getProperty("ReferenceDBtable",path,null,null);
                String RefSchemaName=PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null);
                String CreateRefrenceSchema = "CREATE SCHEMA "+RefSchemaName+" ;";

                PreparedStatement statement =connection.prepareStatement(CreateRefrenceSchema);
                statement.execute();
                statement.close();

                CreateRefrenceQuery =" CREATE TABLE "+RefSchemaName+"."+RefTableName+" (VISITOR_ID int NOT NULL AUTO_INCREMENT,NAME VARCHAR (50), ADDRESS VARCHAR (300)," +
                        "PURPOSE VARCHAR (20), WHOM_TO_MEET VARCHAR (20), SCIENTIST_GRADE VARCHAR (60), SITTING_PLACE VARCHAR (20)," +
                        "DATE VARCHAR (20),TIME VARCHAR (20), COMPANY VARCHAR(60) ,PHONE_NO VARCHAR (15),PHOTO_BLOB LONGBLOB, USER_SESSION VARCHAR (50), PRIMARY KEY (VISITOR_ID))";


            }


            PreparedStatement preparedStatement = connection.prepareStatement(SchemaQuery);
            preparedStatement.execute();
            preparedStatement.close();
            PreparedStatement statement = connection.prepareStatement(CreateTableQuery);
            statement.execute();
            statement.close();

            PreparedStatement statement1 = connection.prepareStatement(CreateRefrenceQuery);
            statement1.execute();
            statement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }





        FadeTransition fadeTransitionIN = Fade.fade(0.8, 0.0, 1.0, DBLabel, null);
        FadeTransition fadeTransitionOUT = Fade.fade(0.8, null, 0.0, DBLabel, null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeTransitionIN, fadeTransitionOUT);
        sequentialTransition.setCycleCount(3);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e->{
            try {
                AnchorPane anchorPane = FXMLLoader.load(FinishInit.class.getResource("ResourcesSetupWiz/FinishInit.fxml"));
                APE.getChildren().setAll(anchorPane);
                FadeTransition fadeTransition1 = Fade.fade(1.0,0.0,1.0,APE,null);
                SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition1);
                sequentialTransition1.play();
            } catch (IOException e1) {
                e1.printStackTrace();
            }



        });



//
    }
}
