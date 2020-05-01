package sample.MainScreenWin;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import sample.AESEncryption;
import sample.CustomCrypto;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class RefernceDataEntry implements Initializable {
    @FXML
    private MenuItem NewUserMenuItem;
    @FXML
    private TextArea AddressField;

    @FXML
    private MenuItem CloseMenuitem;

    @FXML
    private MenuItem ManageEntriesMenuItem;

    @FXML
    private ImageView ShowLodedPhoto;

    @FXML
    private TextField NameField;

    @FXML
    private MenuItem AddPhotoMenuItem;

    @FXML
    private TextField CompanyField;

    @FXML
    private TextField PhonenoField;

    @FXML
    private DatePicker Datepicker;

    @FXML
    private TextField PurposeField;

    @FXML
    private Button SubmitButton;

    @FXML
    private TextField RoomNoField;


    CustomCrypto customCrypto;
    private String path ="Resources/PropertiesMain/Gamma.properties";
    private String schemaName =PropertyConfigs.getProperty("SchemaName","Resources/PropertiesMain/Alpha.properties",
            null,null);
    private String RefTableName = PropertyConfigs.getProperty("ReferenceDBtable",path,null,null);
    private String RefSchemaName=PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null);
    private PreparedStatement preparedStatement;
    private Connection connection;

    @FXML
    void OnSubmitButtonAction() throws SQLException {

        String pass =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","PassWord");
        String User =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","userName");


        String name = NameField.getText();
        String company = CompanyField.getText();
        String phone = PhonenoField.getText();
        String purpose =PurposeField.getText();
        String Date = String.valueOf(Datepicker.getValue());
        String Time = Window1DataEntry.DateAndTime(false);
        String address = AddressField.getText();
        String Room = RoomNoField.getText();
        String user = AESEncryption.decrypt(PropertyConfigs.getProperty("CurrentUser","Resources/PropertiesMain/OtherUsr.properties",
                null,null),null,true);

        connection = DBconnection.DbConnection(User,pass);
        if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")) {
             preparedStatement = connection.prepareStatement("INSERT INTO "+schemaName+"."+RefTableName+
                    "   (NAME, ADDRESS, PURPOSE,  WHOM_TO_MEET, SITTING_PLACE, DATE, TIME, COMPANY, PHONE_NO, " +
                     " USER_SESSION) VALUES ((?),(?),(?),(?),(?),(?),(?),(?),(?),(?))");
        }

        else if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                !PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){
             preparedStatement = connection.prepareStatement("INSERT INTO "+RefSchemaName+"."+RefTableName+
                    "  (NAME, ADDRESS, PURPOSE,  WHOM_TO_MEET, SITTING_PLACE, DATE, TIME, COMPANY, PHONE_NO, " +
                    " USER_SESSION) VALUES ((?),(?),(?),(?),(?),(?),(?),(?),(?),(?))");

        }
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,address);
        preparedStatement.setString(3,purpose);
        preparedStatement.setString(4,user);
        preparedStatement.setString(5,Room);
        preparedStatement.setString(6,Date);
        preparedStatement.setString(7,Time);
        preparedStatement.setString(8,company);
        preparedStatement.setString(9,phone);
        preparedStatement.setString(10,user);
        preparedStatement.executeUpdate();

         NameField.clear();
         CompanyField.clear();
         PhonenoField.clear();
         PurposeField.clear();
         AddressField.clear();
         RoomNoField.clear();



    }

    @FXML
    void OnCloseMenuItem() {
        Platform.exit();
        System.exit(0);

    }

    @FXML
    void OnAddPhotoMenuItem() {

    }

    @FXML
    void OnManageEntresAction() throws IOException {

        BorderPane borderPane = FXMLLoader.load(getClass().getResource("RefShowTable.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("sample/CSS.css");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();


    }

    @FXML
    void OnDatePicker(){




    }

    @FXML
    void NewUserAction() {

        FadeTransition fadeT = Fade.fade(1.0, 1.0, 0.0, BP, null);
        SequentialTransition sequentialTransition = new SequentialTransition(fadeT);
        sequentialTransition.play();
        sequentialTransition.setOnFinished(e -> {
            AnchorPane anchorPane= new AnchorPane();
            try {
                anchorPane = FXMLLoader.load(getClass().getResource("NewUserCreation.fxml"));


            } catch (IOException e1) {
                e1.printStackTrace();
            }
            BP.getChildren().setAll(anchorPane);
            FadeTransition fadeTransition = Fade.fade(1.0, null, 1.0, BP, null);
            SequentialTransition sequentialTransition1 = new SequentialTransition(fadeTransition);
            sequentialTransition1.play();
        });

    }

    @FXML
   private BorderPane BP;

    String user = AESEncryption.decrypt(PropertyConfigs.getProperty("CurrentUser","Resources/PropertiesMain/OtherUsr.properties",
            null,null),null,true);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    @FXML
    void Mouse(){
getFills();
    }

    void getFills(){

        String pass =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","PassWord");
        String User =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","userName");

        String Query=null;

        try {
            connection = DBconnection.DbConnection(User,pass);
            if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                    PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){

                Query="SELECT VISITOR_ID, NAME, COMPANY, ADDRESS, PURPOSE, DATE, PHONE_NO, SITTING_PLACE, USER_SESSION FROM  "+schemaName+"."
                        +RefTableName+" WHERE USER_SESSION = '"+user+"' ";

            }


            else if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                    !PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){

                Query = "SELECT VISITOR_ID, NAME, COMPANY, ADDRESS, PURPOSE, DATE, PHONE_NO, SITTING_PLACE, USER_SESSION FROM  " + RefSchemaName + "."
                        + RefTableName + "USER_SESSION = '"+user+"' ";
            }

            ResultSet resultSet = connection.createStatement().executeQuery(Query);

            LinkedList<String> list_name = new LinkedList<String>();
            LinkedList<String> Phone = new LinkedList<String>();
            LinkedList<String> Company = new LinkedList<String>();
            LinkedList<String> Purpose = new LinkedList<String>();
            LinkedList<String> Sitting = new LinkedList<String>();

            while (resultSet.next()){

                list_name.add(resultSet.getString(2));
                Phone.add(resultSet.getString(7));
                Company.add(resultSet.getString(3));
                Purpose.add(resultSet.getString(6));
                Sitting.add(resultSet.getString(8));

            }

            TextFields.bindAutoCompletion(NameField,list_name);
            TextFields.bindAutoCompletion(PhonenoField,Phone);
            TextFields.bindAutoCompletion(CompanyField,Company);
            TextFields.bindAutoCompletion(PurposeField,Purpose);
            TextFields.bindAutoCompletion(RoomNoField,Sitting);


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

}
