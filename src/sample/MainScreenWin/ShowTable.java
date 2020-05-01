package sample.MainScreenWin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.AESEncryption;
import sample.CustomCrypto;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.sAlearts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowTable implements Initializable {

    @FXML
    private TableColumn<TableClass, String> AddressColumn;

    @FXML
    private TableColumn<TableClass, String> NationalityColumn;

    @FXML
    private TableColumn<TableClass, String> NameColumn;

    @FXML
    private TableColumn<TableClass, String> PhoneNoColumn;

    @FXML
    private TableView<TableClass> DetailsTable;

    @FXML
    private TableColumn<TableClass, String> CompanyColumn;

    @FXML
    private TableColumn<TableClass, String> PurposeColumn;

    @FXML
    private  TableColumn <TableClass, String > UserColumn;

    @FXML
    private MenuBar Menubartable;

    @FXML
    private TableColumn<TableClass, String> DateEntryColumn;

    @FXML
    private TableColumn<TableClass, Integer> VIDcolumn;

    @FXML
    private TableColumn<TableClass, String> TimentryColumn;

    CustomCrypto customCrypto;
   private Connection connection;

    private ObservableList<TableClass> TableArray = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      InitiateColumns();

        //Loading Database password and Username

        String pass =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","PassWord");
        String User =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","userName");

        // getting DATABASE Username And Password

        String tablename = PropertyConfigs.getProperty("TableName","Resources/PropertiesMain/Alpha.properties",
                null,null);
        String schemaName =PropertyConfigs.getProperty("SchemaName","Resources/PropertiesMain/Alpha.properties",
                null,null);

        //Getting connection Database

        String Query = "SELECT VISITOR_ID, NAME ,COMPANY, ADDRESS, PURPOSE, TIME, DATE, NATIONALITY, PHONE_NO, USER_RESEPTION FROM " +schemaName+"."+tablename+"  " ;


        try {
            connection = DBconnection.DbConnection(User,pass);
            ResultSet rs = connection.createStatement().executeQuery(Query);
            while (rs.next()){

                TableArray.addAll(new TableClass(rs.getInt(1),
                                    rs.getString(2),rs.getString(3),rs.getString(4),
                                    rs.getString(5),rs.getString(6),rs.getString(7),
                                    rs.getString(8),rs.getString(9),rs.getString(10)));

            }

            DetailsTable.getItems().addAll(TableArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }


   private void InitiateColumns(){
        FactorySetup(VIDcolumn,"id");
        FactorySetup(NameColumn,"name");
        FactorySetup(CompanyColumn,"company");
        FactorySetup(AddressColumn,"address");
        FactorySetup(PurposeColumn,"purpose");
        FactorySetup(TimentryColumn,"time");
        FactorySetup(DateEntryColumn,"date");
        FactorySetup(NationalityColumn,"nationality");
        FactorySetup(PhoneNoColumn,"phone");
        FactorySetup(UserColumn,"user");
   }

   @FXML
   void OnBlackList() throws IOException {

       TableClass tableClass = DetailsTable.getSelectionModel().getSelectedItem();
       if (Objects.isNull(tableClass)){
           Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,"First Select an entry from table view",null
                   ,Modality.APPLICATION_MODAL);
           alert.showAndWait();
       }
       else {
           String comp_detail = AESEncryption.encrypt(tableClass.getCompany(),null,true);
           File filem= new File("Resources/BlackListed.properties");
           if (!filem.exists()){
               filem.createNewFile();
               PropertyConfigs.SetProperty("CC", AESEncryption.encrypt(String.valueOf(1),null,
                       true),filem.getPath(),null,null);
           }

           String cc =  PropertyConfigs.getProperty("CC",filem.getPath(),null,null);

           String Dec = AESEncryption.decrypt(cc,null,true);

           int i = Integer.parseInt(Dec);

           i++;

           PropertyConfigs.SetProperty("Detail1 "+i,comp_detail,filem.getPath(),null,null);





       }


   }


    public static void FactorySetup(TableColumn tableColumn,String NameOfRef){
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(NameOfRef));
    }
    @FXML
    private TextField FieldId;

    @FXML
     void FilterListAction() {

        FilteredList<TableClass> filteredList = new FilteredList<TableClass>(TableArray,p->true);
        FieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(tableClass -> {

                String lowerCase =newValue.toLowerCase();
                int id =tableClass.getId();
                if (String.valueOf(id).contains(newValue))
                    return true;
                else if (tableClass.getName().toLowerCase().contains(lowerCase))
                    return true;
                else if (tableClass.getCompany().toLowerCase().contains(lowerCase))
                    return true;
                else if (tableClass.getAddress().toLowerCase().contains(lowerCase))
                    return true;
                else if (tableClass.getPurpose().toLowerCase().contains(lowerCase))
                    return true;
                else if (tableClass.getTime().toLowerCase().contains(newValue))
                    return true;
                else if (tableClass.getDate().toLowerCase().contains(newValue))
                    return true;
                else if (tableClass.getNationality().toLowerCase().contains(lowerCase))
                    return true;
                else if (tableClass.getPhone().toLowerCase().contains(newValue))
                    return true;
                else if (tableClass.getUser().toLowerCase().contains(lowerCase))
                    return true;

                return false;

            });

            SortedList<TableClass> sortedList = new SortedList<TableClass>(filteredList);
            sortedList.comparatorProperty().bind(DetailsTable.comparatorProperty());
            DetailsTable.setItems(sortedList);
        });


    }

    @FXML
    void PrintSelectMenu() throws IOException {

        File file = new File("Resources/PropertiesMain/Theta.properties");
        if (!file.exists()){
            file.createNewFile();
        }

            TableClass tableClass = DetailsTable.getSelectionModel().getSelectedItem();
        if (Objects.isNull(tableClass)){
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,"First Select an entry from table view",null
                    ,Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
        else {
            int detail = tableClass.getId();


            PropertyConfigs.SetProperty("Mode", "ID", file.getPath(), null, null);
            PropertyConfigs.SetProperty("Click", String.valueOf(detail), file.getPath(), null, null);
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("PrintLayout.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }

    }

}


