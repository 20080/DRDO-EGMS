package sample.MainScreenWin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import sample.CustomCrypto;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Suraj on 5/3/2017.
 */
public class PrintLayout implements Initializable{

private Connection connection;
CustomCrypto customCrypto;

    @FXML
    private Label PurposeL;

    @FXML
    private Label RoomNO;

    @FXML
    private Label DateL;

    @FXML
    private Label gradeL;

    @FXML
    private Label NameL;

    @FXML
    private Label SittingPlaceL;

    @FXML
    private Label NationalityL;

    @FXML
    private AnchorPane AnchorPane1;

    @FXML
    private Label ID;

    @FXML
    private ImageView ImagePhoto;

    @FXML
    private Label AddressL;

    @FXML
    private Label TimeL;

    @FXML
    private Label WhomeToMeetL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

ModeSel();


    }

    private ResultSet  rs;

    private void ModeSel(){


        //DecryptedPart
        String pass =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","PassWord");
        String User =customCrypto.CryptoDec(null,
                "Resources/SecurityProperties/udDB.properties","userName");

//table name and schema name
        String tablename = PropertyConfigs.getProperty("TableName","Resources/PropertiesMain/Alpha.properties",
                null,null);
        String schemaName =PropertyConfigs.getProperty("SchemaName","Resources/PropertiesMain/Alpha.properties",
                null,null);

        // Getting Selected ID
        File file = new File("Resources/PropertiesMain/Theta.properties");
        File fPhoto= new File("Resources/Photo.jpg");
        String id = PropertyConfigs.getProperty("Click",file.getPath(),null,null);
        String date = PropertyConfigs.getProperty("DatePara",file.getPath(),null,null);
        String timep = PropertyConfigs.getProperty("TimePara",file.getPath(),null,null);
//        String Mode = PropertyConfigs.getProperty("Mode",file.getPath(),null,null);


        try {

            connection= DBconnection.DbConnection(User,pass);

            if (!Window1DataEntry.printstat){
                String query="SELECT * FROM "+schemaName+"."+tablename+" WHERE VISITOR_ID =  "+id+" ";
              rs  = connection.createStatement().executeQuery(query);
            }

            else if (Window1DataEntry.printstat){
                String query="SELECT * FROM "+schemaName+"."+tablename+" WHERE TIME =  '"+date.toString()+"' AND DATE = '"+timep.toString()+"' ";
                rs  = connection.createStatement().executeQuery(query);
            }





            while (rs.next()){
                int idMode = rs.getInt(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                String nationality = rs.getString(4);
                String purpose = rs.getString(5);
                String tomeet = rs.getString(6);
                String grade = rs.getString(7);
                String SittingPlace = rs.getString(8);
                String roomno = rs.getString(9);
                String Date = rs.getString(10);
                String time = rs.getString(11);
                String company = rs.getString(12);
                String phoneno = rs.getString(13);
                InputStream inputStream = rs.getBinaryStream(14);

                OutputStream outputStream = new FileOutputStream(fPhoto);
                int bits =0;
                while ((bits=inputStream.read())>-1){

                    outputStream.write(bits);

                }
                Image image = new Image(fPhoto.toURI().toString());
            if (Window1DataEntry.printstat){
                ID.setText(String .valueOf(idMode));
            }
            else if (!Window1DataEntry.printstat){
                ID.setText(id);
            }

                NameL.setText(name);
                AddressL.setText(address);
                NationalityL.setText(nationality);
                PurposeL.setText(purpose);
                WhomeToMeetL.setText(tomeet);
                SittingPlaceL.setText(SittingPlace);
                RoomNO.setText(roomno);
                DateL.setText(Date);
                TimeL.setText(time);
                ImagePhoto.setImage(image);
                gradeL.setText(grade);

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button PrintButton;

    @FXML
    void PrintButtonAction(){


       PrintButton.setScaleX(0);
       PrintButton.setScaleY(0);





//
            printSetup(AnchorPane1,(Stage) PrintButton.getScene().getWindow());
//            printImage(PrintButton.getParent().getScene().getRoot());


    }

    private void printSetup(Node node, Stage stage){
        PrinterJob printerJob= PrinterJob.createPrinterJob();
        if (printerJob==null){
            return;
        }
        AnchorPane1.getTransforms().add(new Scale(0.75,0.75));

        boolean proceed = printerJob.showPrintDialog(stage);
        if(proceed){
            print(printerJob,node);
        }

    }

    private void print(PrinterJob job, Node node){

        boolean pr= job.printPage(node);
        if (pr)
            job.endJob();

    }



//
//    public void printImage(Node node) {
//
//        Printer printer = Printer.getDefaultPrinter();
//        PageLayout pageLayout = printer.getDefaultPageLayout();
//        System.out.println("PageLayout: " + pageLayout);
//
//        // Printable area
//        double pWidth = pageLayout.getPrintableWidth();
//        double pHeight = pageLayout.getPrintableHeight();
//        System.out.println("Printable area is " + pWidth + " width and "
//                + pHeight + " height.");
//
//        // Node's (Image) dimensions
//        double nWidth = node.getBoundsInParent().getWidth();
//        double nHeight = node.getBoundsInParent().getHeight();
//        System.out.println("Node's dimensions are " + nWidth + " width and "
//                + nHeight + " height");
//
//        // How much space is left? Or is the image to big?
//        double widthLeft = pWidth - nWidth;
//        double heightLeft = pHeight - nHeight;
//        System.out.println("Width left: " + widthLeft
//                + " height left: " + heightLeft);
//
//        // scale the image to fit the page in width, height or both
//        double scaleq = 0;
//
//        if (widthLeft < heightLeft) {
//            scaleq = pWidth / nWidth;
//        } else {
//            scaleq = pHeight / nHeight;
//        }
//
//        // preserve ratio (both values are the same)
//        node.getTransforms().add(new Scale(0.75,0.75));
//
//        // after scale you can check the size fit in the printable area
//        double newWidth = node.getBoundsInParent().getWidth();
//        double newHeight = node.getBoundsInParent().getHeight();
//        System.out.println("New Node's dimensions: " + newWidth
//                + " width " + newHeight + " height");
//
//        PrinterJob job = PrinterJob.createPrinterJob();
//        if (job != null) {
//            boolean success = job.printPage(node);
//            if (success) {
//                job.endJob();
//
//            }
//        }
//    }

}
