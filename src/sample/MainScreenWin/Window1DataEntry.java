package sample.MainScreenWin;

import com.github.sarxos.webcam.Webcam;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.AESEncryption;
import sample.CustomCrypto;
import sample.DBconnection;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.Transitions.Fade;
import sample.Transitions.Path;
import sample.Transitions.ScaleT;
import sample.sAlearts;

import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.control.CheckBox;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Window1DataEntry implements Initializable{

    @FXML
    private AnchorPane RightBP;

    private Webcam Selected=null;
    Webcam defaultweb =Webcam.getDefault();

    @FXML
    private TextField NameF;


    @FXML
    private TextArea TextAreaAddF;

    @FXML
    private Button SAveButton;

    @FXML
    TextField CompanyField;
    @FXML
    private ImageView ImageView;

    @FXML
    private Button CaptureButton;

    @FXML
    private ComboBox<WebCamInfo> ComboBox1;

    @FXML
    private TextField NationatilityBox;

    @FXML
    private Button OpenCamButton;

    @FXML
    private AnchorPane TopBp;

    @FXML
    TextField PurpposeF;

    @FXML
    private AnchorPane BottomBP;

    @FXML
    private TextField Meetf;

    @FXML
    private TextField SittingPlaceF;

    @FXML
    private AnchorPane LeftBP;

    @FXML
    private TextField PhoneNoF;

    @FXML
    private CheckBox TickIndian;

    @FXML
    private TextField RoomNoF;

    @FXML
    private Button SaveDataButton;

    static boolean main= true;
    private Image image;
    private BufferedImage bufferedImage;
    private boolean captured = false;
    private ObjectProperty<Image> imageObjectProperty=new SimpleObjectProperty<Image>();

    private static boolean TikBool= false;
    @FXML
    void OnTickAction() {
    if (!TikBool){
        TikBool=true;
        showAndFade(1,NationatilityBox);
        NationatilityBox.setText("Indian");
        NationatilityBox.setDisable(true);
        TickIndian.setSelected(true);

    }
    else if(TikBool){
        TikBool=false;
        showAndFade(1,NationatilityBox);
        NationatilityBox.clear();
        NationatilityBox.setDisable(false);
        TickIndian.setSelected(false);

    }

    }


    private void setSessionNullOnExit(){
//        PropertyConfigs.SetProperty("EncryptKey","null",
//                "Resources/PropertiesMain/Alpha.properties"
//                ,null,null);
//        PropertyConfigs.SetProperty("Rule","true","Resources/PropertiesMain/Alpha.properties",
//                null,null);
        Stage stage1 = (Stage) MAinPain.getScene().getWindow();
        stage1.setOnCloseRequest(e->{

            PropertyConfigs.SetProperty("EncryptKey","null",
                    "Resources/PropertiesMain/Alpha.properties"
                    ,null,null);
//            PropertyConfigs.SetProperty("Rule","true","Resources/PropertiesMain/Alpha.properties",
//                    null,null);
        });
    }

    @FXML
    private CheckBox VVIPTICKED;

    static boolean printstat = false;

    /*MenuButton Actions*/

    public void PrintLastSaved() throws IOException {

        setSessionNullOnExit();

        printstat = true;

//        File file = new File("Resources/PropertiesMain/Theta.properties");
//        if (!file.exists()){
//            file.createNewFile();
//            PropertyConfigs.SetProperty("DatePara","null",file.getPath(),null,null);
//            PropertyConfigs.SetProperty("Mode","DATE",file.getPath(),null,null);
//
//
//
//        }
//        String parameter =  PropertyConfigs.getProperty("DatePara",file.getPath(),null,null);
//
//
//
//        if (PropertyConfigs.getProperty("Mode",file.getPath(),null,null).equals("ID")){
//            PropertyConfigs.SetProperty("Mode","DATE",file.getPath(),null,null);
//        }
//
//     if (parameter.equals("null")||PropertyConfigs.getProperty("Mode",file.getPath(),null,null).equals("null")){
//
//          Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,"No entery is maden yet",null,
//                  Modality.APPLICATION_MODAL);
//
//
//          alert.showAndWait();
//
//        }
//        else  {

         AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("PrintLayout.fxml"));
         Stage stage = new Stage();
         stage.setScene(new Scene(anchorPane));
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.show();


//     }




    }

    public void MenuCloseOption() {

        System.exit(0);
        Platform.exit();

    }

    public void ViewTable() throws IOException {
        setSessionNullOnExit();
        printstat = false;
        BorderPane BP = FXMLLoader.load(getClass().getResource("ShowTable.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(BP);
        scene.getStylesheets().add("sample/CSS.css");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    static boolean auth=true;
    public void AddNewUser() throws IOException {
        auth =false;
        BorderPane anchorPane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(e-> setSessionNullOnExit());
        stage.setOnHiding(e->setSessionNullOnExit());
        stage.show();
    }

    public void CameraConfguration() throws IOException {

        TabPane tabPane =FXMLLoader.load(getClass().getResource("CameraConfiguration.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(tabPane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }




    private class WebCamInfo {

        String webCamName;
        int webCamIndex;

        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }
        @Override
        public String toString() {
            return webCamName;
        }
    }

    @FXML
    void OnSaveData() throws SQLException, IOException {

        setSessionNullOnExit();

 if (NameF.getText().length()>50){
     Megaal("Name Field","50",NameF);

 }

        else if (TextAreaAddF.getText().length()>300){
           Alert alert = sAlearts.aleart(Alert.AlertType.WARNING,null,
                   "Address field must not exceed 300 chars",null,Modality.APPLICATION_MODAL);
           alert.showAndWait();
            TextAreaAddF.clear();
        }

 else if (NationatilityBox.getText().length()>15){
     Megaal("Nationality","15",NationatilityBox);

 }
 else if (Meetf.getText().length()>20){
     Megaal("Whome to meet field","20",Meetf);

 }
 else if (SittingPlaceF.getText().length()>20){
     Megaal("Sitting field","20",SittingPlaceF);

 }
 else if (PurpposeF.getText().length()>20){
     Megaal("Purpose","20",PurpposeF);

 }
 else if (CompanyField.getText().length()>60){
     Megaal("Company field","60",CompanyField);

 }
 else if (PhoneNoF.getText().length()>15) {
     Megaal("PhoneNo field", "15", PhoneNoF);
 }
else if (RoomNoF.getText().length()>10){
         Megaal("RoomNo field","10",RoomNoF);

 }
 else if (ScientistGrade.getText().length()>40){
     Megaal("Grade field","40",ScientistGrade);

 }

 else if (!file.exists()&&check){
     Alert alert = new Alert(Alert.AlertType.ERROR,"Photo Not ready yet");
     alert.showAndWait();
 }

      else   if (!NameF.getText().isEmpty()&&!Meetf.getText().isEmpty()&&!SittingPlaceF.getText().isEmpty()
                &&!TextAreaAddF.getText().isEmpty()&&!NationatilityBox.getText().isEmpty()&&
                !CompanyField.getText().isEmpty()&&!ScientistGrade.getText().isEmpty()&&
                !PhoneNoF.getText().isEmpty()&&!PurpposeF.getText().isEmpty()&&!RoomNoF.getText().isEmpty())
      {

            String VisitorName = NameF.getText();
            String WhomeTOMeet = Meetf.getText();
            String SittingPlace = SittingPlaceF.getText();
            String Address = TextAreaAddF.getText();
            String nationality = NationatilityBox.getText();
            String Company = CompanyField.getText();
            String Grade = ScientistGrade.getText();
            String PhoneNo =PhoneNoF.getText();
            String purpose = PurpposeF.getText();
            String RoomNo = RoomNoF.getText();
            String Date = DateAndTime(true);
            String Time =  DateAndTime(false);
          FileInputStream inputStream = null;
            if (check) {
                inputStream = new FileInputStream(file);
            }
            String pass =customCrypto.CryptoDec(null,
                    "Resources/SecurityProperties/udDB.properties","PassWord");
            String User =customCrypto.CryptoDec(null,
                    "Resources/SecurityProperties/udDB.properties","userName");


                String tablename = PropertyConfigs.getProperty("TableName","Resources/PropertiesMain/Alpha.properties",
                        null,null);
                String schemaName =PropertyConfigs.getProperty("SchemaName","Resources/PropertiesMain/Alpha.properties",
                        null,null);

            Connection connection = DBconnection.DbConnection(User,pass);

            String user = AESEncryption.decrypt(PropertyConfigs.getProperty("CurrentUser",
                    "Resources/PropertiesMain/Beta.properties",null,null),null,true);


            PreparedStatement preparedStatement =connection.prepareStatement("Insert into  "+schemaName+"."+tablename+"  ( NAME, ADDRESS, NATIONALITY, PURPOSE, WHOM_TO_MEET, SCIENTIST_GRADE, SITTING_PLACE, ROOM_NO, DATE, TIME, COMPANY, PHONE_NO, Photo_BLOB, USER_RESEPTION) values ((?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?),(?))");
            preparedStatement.setString(1,VisitorName);
            preparedStatement.setString(2,Address);
            preparedStatement.setString(3,nationality);
            preparedStatement.setString(4,purpose);
            preparedStatement.setString(5,WhomeTOMeet);
            preparedStatement.setString(6,Grade);
            preparedStatement.setString(7,SittingPlace);
            preparedStatement.setString(8,RoomNo);
            preparedStatement.setString(9,Date);
            preparedStatement.setString(10,Time);
            preparedStatement.setString(11,Company);
            preparedStatement.setString(12,PhoneNo);
            if (check){
                preparedStatement.setBinaryStream(13,inputStream,(int) file.length());
            }
            else if (!check){
                try {
                    File ab = new File("src/sample/Drawables/1.jpg");
                    FileInputStream inputStream1 = new FileInputStream(ab);
                    preparedStatement.setBinaryStream(13,inputStream1,(int) ab.length());
                }

                catch (IOException e1) {
                    Alert alert= sAlearts.aleart(Alert.AlertType.ERROR,String.valueOf(e1),"Error while creating file",String.valueOf(e1),null);
                    alert.show();
                    e1.printStackTrace();
                }

            }
            preparedStatement.setString(14,user);
            preparedStatement.executeUpdate();

          File file1 = new File("Resources/PropertiesMain/Theta.properties");
          if (!file1.exists()){
              file1.createNewFile();
              PropertyConfigs.SetProperty("Mode","null",file1.getPath(),null,null);
          }

          PropertyConfigs.SetProperty("DatePara",Time,file1.getPath(),null,null);
          PropertyConfigs.SetProperty("TimePara",Date ,file1.getPath(),null,null);







          NameF.clear();
          Meetf.clear();
          SittingPlaceF.clear();
          TextAreaAddF.clear();
          NationatilityBox.clear();
          NationatilityBox.setDisable(false);
          TickIndian.setSelected(false);
           CompanyField.clear();
           ScientistGrade.clear();
          PhoneNoF.clear();
          PurpposeF.clear();
          RoomNoF.clear();
          inputStream.close();
          Files.deleteIfExists(file.toPath());

          //Tick config
          TikBool=false;

          System.out.println("SucessFulll");

            }

            else {
     Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,"Empty field",
             "All fields are required to be filled",Modality.APPLICATION_MODAL);
     alert.showAndWait();

 }


    }
    private CustomCrypto customCrypto = new CustomCrypto();
    @FXML
    TextField ScientistGrade;

    private void Megaal(String name,String limit, TextField textField){
        Alert alert = sAlearts.aleart(Alert.AlertType.WARNING,null,name+" length must not exceed "+limit,"",Modality.APPLICATION_MODAL
        );
        alert.showAndWait();
        textField.clear();


    }


    static String DateAndTime(boolean b) {

        if (b) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(calendar.getTime());
        }
        else if (!b){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            return simpleDateFormat.format(calendar.getTime());

        }
        return null;
    }

    public static boolean check = true;


@FXML
void OnVVIPACTION(){

    if (!check) check = true;
    else check = false;
}

    @FXML
    void OnSaveImage() {
if (file.exists()) {

Tran();
OpenCamButton.setDisable(false);
SaveDataButton.setDisable(false);
captured=true;

}
else {
    Alert alert = sAlearts.aleart(Alert.AlertType.CONFIRMATION, "null", "Image Not captured yet do you want to close",
            "Press yes to close and no to continue", Modality.APPLICATION_MODAL);
    alert.showAndWait();
    if (alert.getResult().equals(ButtonType.OK)) {
        Tran();
        OpenCamButton.setDisable(false);
        SaveDataButton.setDisable(false);
    }


}

    }
    String path= "src/sample/Drawables/1.jpg";
    static File file = new File("Resources/one.jpg");
    @FXML
    void OnActionImage() throws IOException {

        if (!captured){

            Thread thread = new Thread(new Runnable() {
         @Override
                 public void run() {
            BufferedImage image =Selected.getImage();
             try {
                 ImageIO.write(image,"jpg",file);

             } catch (IOException e) {
                 e.printStackTrace();
             }


         }
            });
            thread.start();



            captured=true;
            CaptureButton.setText("Re-Capture");
            SAveButton.setText("Save");
            showAndFade(1,SAveButton);
        }
        else {
            captured=false;
            Files.deleteIfExists(file.toPath());
            StartWebCamStream();
            CaptureButton.setText("Capture");
            showAndFade(1,SAveButton);
            SAveButton.setText("Close");
        }

    }


    @FXML
    void OnOpenCamera() throws IOException {
        CaptureButton.setText("Capture");
        OpenCamButton.setDisable(true);
        SaveDataButton.setDisable(true);
        if (captured) {
            captured=false;
            StartWebCamStream();
        }
//left
        PathTransition pathTransition = Path.pathLinear(1.0,LeftBP.getLayoutX()+100,82.0,-05.0,82.0,LeftBP);
        FadeTransition fadeTransition = Fade.fade(1.0,1.0,0.67,LeftBP,null);
        ParallelTransition LeftTran = new ParallelTransition(pathTransition,fadeTransition);
      //Top

        PathTransition pathTransition1 = Path.pathLinear(1.0,300.0,TopBp.getLayoutY()+45,300.0,-2.0,
                TopBp);
        FadeTransition fadeTransition1 = Fade.fade(1.0,1.0,0.67,TopBp,null);
        ParallelTransition TopAnimation = new ParallelTransition(pathTransition1,fadeTransition1);



        //Right
        PathTransition pathTransition3 = Path.pathLinear(1.0,RightBP.getLayoutX()-245,82.0,170.0,82.0,
                RightBP);
        FadeTransition fadeTransition2 = Fade.fade(1.0,1.0,0.6,RightBP,null);

        ParallelTransition RightPar = new ParallelTransition(pathTransition3,fadeTransition2);

//Bottom


        PathTransition pathTransition4 = Path.pathLinear(1.0,300.0,BottomBP.getLayoutY()-189.0,300.0,+130.0,
                BottomBP);
        FadeTransition fadeTransition4 = Fade.fade(1.0,1.0,0.67,BottomBP,null);
        ParallelTransition Bottton = new ParallelTransition(pathTransition4,fadeTransition4);
        ParallelTransition parallelTransition = new ParallelTransition(TopAnimation,LeftTran,RightPar,Bottton);
        parallelTransition.play();
        parallelTransition.setOnFinished(e->{

            VBOX.setDisable(false);
            ScaleTransition scaleTransition = ScaleT.scale(000.5,MAinPain.getCenter(),1.0,1.0,1.0);
            FadeTransition fadeTransition3 = Fade.fade(2.0,0.0,1.0,MAinPain.getCenter(),null);
            ParallelTransition pt = new ParallelTransition(scaleTransition,fadeTransition3);
            SequentialTransition sequentialTransition = new SequentialTransition(pt);
            sequentialTransition.play();

            if (isMain) {
                ComboBox1.setDisable(true);
                ComboBox1.setScaleX(0);
                ComboBox1.setScaleY(0);
                InitWebCam();
            }

        });



    }
    @FXML
    private BorderPane MAinPain;

    @FXML
    private VBox VBOX;
    private static  boolean isMain=true;


private static int width;
 private static int height;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!OtherUserFile.exists()){
            TodaysInvitaionMenuBar.setDisable(true);
        }





        CaptureButton.setDisable(true);
        showAndFade(0,MAinPain.getCenter());
        ScaleTransition scaleTransition = ScaleT.scale(.1,MAinPain.getCenter(),0.0,0.0,0.0);
        scaleTransition.play();
        VBOX.setDisable(true);
        PhoneNoF.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d")) {

                PhoneNoF.setText(newValue.replaceAll("[^\\d]",""));

            }
        });




        if (PropertyConfigs.getProperty("MultiKey","Resources/PropertiesMain/Alpha.properties",
                null,null).equals("true")){
            isMain=false;
            ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
            int counter=0;
            for(Webcam webcam: Webcam.getWebcams()){
                WebCamInfo webCamInfo = new WebCamInfo();
                webCamInfo.setWebCamIndex(counter);
                webCamInfo.setWebCamName(webcam.getName());
                options.add(webCamInfo);
                counter++;
            }
            ComboBox1.setItems(options);
            ComboBox1.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                initializeWebCam(newValue.getWebCamIndex());
                if (!Objects.equals(newValue,null)){
                    CaptureButton.setDisable(false);
                }

            }));




        }



    }
private void getHeightWidth(){
    width = Integer.parseInt(PropertyConfigs.getProperty("Width","Resources/PropertiesMain/CameraConfiguration.properties",
            null,null));
    height = Integer.parseInt(PropertyConfigs.getProperty("Height","Resources/PropertiesMain/CameraConfiguration.properties",
            null,null));
}
    private void InitWebCam(){
        Task<Void> Initlizer = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                    CaptureButton.setDisable(false);
                getHeightWidth();
                if (Selected==null){
                    Selected =Webcam.getDefault();
                    Selected.setViewSize(new Dimension(width,height));
                    Selected.open();
                }



                else{
                    Stop();
                    Selected=Webcam.getDefault();
                    Selected.setViewSize(new Dimension(width,height));
                    Selected.open();
                }

                StartWebCamStream();
                return null;
            }
        };

        new Thread(Initlizer).start();
    }

    private void initializeWebCam(final int webCamIndex) {


        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
getHeightWidth();
                if (Selected == null) {
                    Selected = Webcam.getWebcams().get(webCamIndex);
                        Selected.setViewSize(new Dimension(width, height));
                        Selected.open();
                    }


                else {
                    Stop();
                    Selected = Webcam.getWebcams().get(webCamIndex);
                    Selected.setViewSize(new Dimension(width, height));
                    Selected.open();
                }
                StartWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntilizer).start();

    }

    private void StartWebCamStream(){

        captured=false;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (!captured){

                    if ((bufferedImage=Selected.getImage())!=null){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                image= SwingFXUtils.toFXImage(bufferedImage,null);
                                imageObjectProperty.set(image);

                            }
                        });

                        bufferedImage.flush();
                    }


                }

                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        ImageView.imageProperty().bind(imageObjectProperty);

    }

    private File OtherUserFile = new File("Resources/PropertiesMain/OtherUsr.properties");

    @FXML
    private MenuItem TodaysInvitaionMenuBar;
@FXML
void OnTodaysInvitation() throws IOException {



       if(OtherUserFile.exists()) {

            BorderPane borderPane = FXMLLoader.load(getClass().getResource("RefShowTable.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add("sample/CSS.css");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }


}



    private void Stop(){
        if (Selected != null) {
            Selected.close();
        }
    }


    public static void showAndFade(int i,Node node){
        if (i==0) {

            FadeTransition fadeTransition = Fade.fade(.1, 0.0, 0.0, node, null);
            SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
            sequentialTransition.play();
        }
        else if (i==1){
            FadeTransition fadeTransition = Fade.fade(1., 0.0, 1.0, node, null);
            SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
            sequentialTransition.play();

        }

        else if (i==2){
            FadeTransition fadeTransition = Fade.fade(1., 1.0, 0.0, node, null);
            SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition);
            sequentialTransition.play();
        }
    }
    private void Tran(){
        PathTransition pathTransition = Path.pathLinear(1.0, -5.0, 82.0, LeftBP.getLayoutX() + 100, 82.0, LeftBP);
        FadeTransition fadeTransition = Fade.fade(1.0, null, 1.0, LeftBP, null);
        ParallelTransition LeftTran = new ParallelTransition(pathTransition, fadeTransition);
        //Top

        PathTransition pathTransition1 = Path.pathLinear(1.0, 300.0, -2.0, 300.0, TopBp.getLayoutY() + 45,
                TopBp);
        FadeTransition fadeTransition1 = Fade.fade(1.0, null, 1.0, TopBp, null);
        ParallelTransition TopAnimation = new ParallelTransition(pathTransition1, fadeTransition1);


        //Right
        PathTransition pathTransition3 = Path.pathLinear(1.0, 170.0, 82.0, RightBP.getLayoutX() - 245, 82.0,
                RightBP);
        FadeTransition fadeTransition2 = Fade.fade(1.0, null, 1.0, RightBP, null);

        ParallelTransition RightPar = new ParallelTransition(pathTransition3, fadeTransition2);

//Bottom


        PathTransition pathTransition4 = Path.pathLinear(1.0, 300.0, +130.0, 300.0, BottomBP.getLayoutY()-189,
                BottomBP);
        FadeTransition fadeTransition4 = Fade.fade(1.0, null, 1.0, BottomBP, null);
        ParallelTransition Bottton = new ParallelTransition(pathTransition4, fadeTransition4);
        ParallelTransition parallelTransition = new ParallelTransition(TopAnimation, LeftTran, RightPar, Bottton);
        VBOX.setDisable(true);
        ScaleTransition scaleTransition = ScaleT.scale(000.5, MAinPain.getCenter(), 0.0, 0.0, 0.0);
        FadeTransition fadeTransition3 = Fade.fade(1.0, 1.0, 0.0, MAinPain.getCenter(), null);
        ParallelTransition pt = new ParallelTransition(scaleTransition, fadeTransition3);
        SequentialTransition sequentialTransition = new SequentialTransition(pt);
        ParallelTransition parallelTransition1 = new ParallelTransition(parallelTransition, sequentialTransition);
        parallelTransition1.play();
        parallelTransition1.setOnFinished(e -> captured = true);
    }

//    @FXML
//    private ToggleGroup Gen;


    //todo important

//    @FXML
//    void OnMsSelection(){
////        String Name = NameF.getText();
////        NameF.setText("Ms. "+Name);
//    }
//
//    @FXML
//    void OnMrSelection(){
////        String Name = NameF.getText();
////        NameF.setText("Mr. "+Name);
//    }

}
