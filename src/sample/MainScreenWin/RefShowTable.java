package sample.MainScreenWin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import sample.AESEncryption;
import sample.CustomCrypto;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.sAlearts;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

public class RefShowTable implements Initializable{

    @FXML
    private TableColumn <Integer,RefrenceTableClass> IDColumn ;
    @FXML
    private TableColumn<String, RefrenceTableClass> AddressColumn;

    @FXML
    private TableColumn<String, RefrenceTableClass> UserColumn;

    @FXML
    private TableColumn<String, RefrenceTableClass> DateColumn;

    @FXML
    private TableView<RefrenceTableClass> TableMain;

    @FXML
    private TableColumn<String, RefrenceTableClass> NameColumn;

    @FXML
    private TextField FilterField;

    @FXML
    private TableColumn<String, RefrenceTableClass> CompanyColumn;

    @FXML
    private TableColumn<String, RefrenceTableClass> SittingPlaceColumn;

    @FXML
    private TableColumn<String, RefrenceTableClass> PurposeColumn;

    @FXML
    private TableColumn<String, RefrenceTableClass> PhoneNoColumn;

    ObservableList<RefrenceTableClass> RefTableArray = FXCollections.observableArrayList();

    private String path ="Resources/PropertiesMain/Gamma.properties";
    private String schemaName = PropertyConfigs.getProperty("SchemaName","Resources/PropertiesMain/Alpha.properties",
            null,null);
    private String RefTableName = PropertyConfigs.getProperty("ReferenceDBtable",path,null,null);
    private String RefSchemaName=PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null);
    private CustomCrypto customCrypto;
    private Connection connection;
    private ResultSet resultSet;
    private String Query;

    @FXML
    void OnFilterkyeRelesed() {

    }

    @FXML
    private MenuItem IDDelete;

    private void InitColumns(){
        ShowTable.FactorySetup(IDColumn,"id");
        ShowTable.FactorySetup(NameColumn,"name");
        ShowTable.FactorySetup(CompanyColumn,"company");
        ShowTable.FactorySetup(AddressColumn,"address");
        ShowTable.FactorySetup(PurposeColumn,"purpose");
        ShowTable.FactorySetup(DateColumn,"date");
        ShowTable.FactorySetup(PhoneNoColumn,"phone");
        ShowTable.FactorySetup(SittingPlaceColumn,"sitting_place");
        ShowTable.FactorySetup(UserColumn,"session");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (login.privileged){
            IDDelete.setDisable(true);
        }
        InitColumns();

        String user = AESEncryption.decrypt(PropertyConfigs.getProperty("CurrentUser","Resources/PropertiesMain/OtherUsr.properties",
                null,null),null,true);

        String pass =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","PassWord");
        String User =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","userName");

        if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){
            if (login.privileged){
                System.out.println("here");
                Query="SELECT VISITOR_ID, NAME, COMPANY, ADDRESS, PURPOSE, DATE, PHONE_NO, SITTING_PLACE, USER_SESSION FROM  "+schemaName+"."
                        +RefTableName+" ";
            }
            else if (!login.privileged){
                System.out.println("this");
                Query = "SELECT VISITOR_ID, NAME, COMPANY, ADDRESS, PURPOSE, DATE, PHONE_NO, SITTING_PLACE, USER_SESSION FROM  " + schemaName + "."
                        + RefTableName + " WHERE USER_SESSION =  '"+ user +"' ; ";
            }
        }


        else if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                !PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){

            if (login.privileged){
                Query="SELECT VISITOR_ID, NAME, COMPANY, ADDRESS, PURPOSE, DATE, PHONE_NO, SITTING_PLACE, USER_SESSION FROM  "+RefSchemaName+"."
                        +RefTableName+" ";
            }
            else if (!login.privileged) {
                Query = "SELECT VISITOR_ID, NAME, COMPANY, ADDRESS, PURPOSE, DATE, PHONE_NO, SITTING_PLACE, USER_SESSION FROM  " + RefSchemaName + "."
                        + RefTableName + " WHERE USER_SESSION =   '"+ user +"' ; ";
            }
        }
        try {
            connection = DBconnection.DbConnection(User,pass);
            resultSet = connection.createStatement().executeQuery(Query);

            while (resultSet.next()){

                RefTableArray.addAll(new RefrenceTableClass(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),
                        resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),
                        resultSet.getString(9)));

            }
            TableMain.getItems().addAll(RefTableArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

@FXML
    void OnDeleteMenuItem() throws SQLException {
        RefrenceTableClass rtc= TableMain.getSelectionModel().getSelectedItem();
    PreparedStatement ps = null;
        int id = rtc.getId();
    Alert alert = sAlearts.aleart(Alert.AlertType.CONFIRMATION,null,"Are you sure","Do you want to delete this entry", Modality.WINDOW_MODAL);
    alert.showAndWait();
    if (alert.getResult().equals(ButtonType.OK)){
        if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){
            ps = connection.prepareStatement("DELETE FROM "+schemaName+"."+RefTableName+" WHERE VISITOR_ID ="+id+" ");
        }
        else if (PropertyConfigs.getProperty("ReferenceDB",path,null,null).equals("true")&&
                !PropertyConfigs.getProperty("ReferenceDBSchema",path,null,null).equals("void")){

            ps = connection.prepareStatement("DELETE FROM "+RefSchemaName+"."+RefTableName+" WHERE VISITOR_ID ="+id+" ");
        }

        ps.execute();
        RefTableArray.clear();


    }
}
}
