package sample.MainScreenWin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.AESEncryption;
import sample.PropertiesConfiguration.PropertyConfigs;
import sample.sAlearts;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NewUserCreation {

    @FXML
    private PasswordField PasswordF;

    @FXML
    private TextField UsernameF;

    @FXML
    private PasswordField RPasswordF;

    @FXML
    private AnchorPane Anchor;

    @FXML
    void EnterBUTTONAction() {
Action();
    }

    @FXML
    void CreateButtonAction() {
Action();
    }
    private static boolean main;
    File file ;

    private void Action()  {
if (login.privileged)
{
    file = new File("Resources/PropertiesMain/Beta.properties");
    System.out.println(file.getPath());
    UserCreation(file);
}


if (!login.privileged){

    file= new File("Resources/PropertiesMain/OtherUsr.properties");

    if (!file.exists()){
        try {
            if (Objects.equals(PasswordF.getText(), RPasswordF.getText()) && !UsernameF.getText().isEmpty() && !PasswordF.getText().isEmpty() &&
                    !RPasswordF.getText().isEmpty()) {
                file.createNewFile();
                PropertyConfigs.SetProperty("UC", AESEncryption.encrypt(String.valueOf(0), null,
                        true), file.getPath(), null, null);
            }
            else {
                Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,"Error while creating user",
                        "Possible mistakes - either passwords do not match\n or fields are empty",Modality.APPLICATION_MODAL);
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (file.exists()) {
        UserCreation(file);
    }
    System.out.println(file.getPath());

}

    }



    private void UserCreation(File file){
        i=0;
        main=false;



        if (Match(file))
        {

            Alert alert = sAlearts.aleart(Alert.AlertType.WARNING,null,"Username not available",
                    "username already exists please try with another username",Modality.APPLICATION_MODAL);
            alert.showAndWait();


        }
        else {


            if (Objects.equals(PasswordF.getText(), RPasswordF.getText()) && !UsernameF.getText().isEmpty() && !PasswordF.getText().isEmpty() &&
                    !RPasswordF.getText().isEmpty()) {

                String getCount = AESEncryption.decrypt(PropertyConfigs.getProperty("UC", file.getPath(),
                        null, null), null, true);
                int i = Integer.parseInt(getCount);
                i++;


                PropertyConfigs.SetProperty("AppUsr" + i + "", AESEncryption.encrypt(UsernameF.getText(), null, true)
                        , file.getPath(), null, null);
                PropertyConfigs.SetProperty("AppPass" + i + "", AESEncryption.encrypt(RPasswordF.getText(), null, true)
                        , file.getPath(), null, null);
                PropertyConfigs.SetProperty("UC", AESEncryption.encrypt(String.valueOf(i), null,
                        true), file.getPath(), null, null);
                Stage stage = (Stage) Anchor.getScene().getWindow();
                stage.setOnHiding(e -> {
                    Alert alert = sAlearts.aleart(Alert.AlertType.INFORMATION, null,
                            "User Sucessfully Created", "User created", Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                });
                stage.close();

            }  else if (UsernameF.getText().isEmpty() || PasswordF.getText().isEmpty() ||
                    RPasswordF.getText().isEmpty()) {
                Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null, " One or more Fields found empty",
                        "Please fill all the fields in order to create a new user", Modality.APPLICATION_MODAL);
                alert.showAndWait();
            } else if (!Objects.equals(PasswordF.getText(), RPasswordF.getText())) {
                Alert alert = sAlearts.aleart(Alert.AlertType.ERROR, null, " Passwords do not match",
                        "Re-enter the passwords", Modality.APPLICATION_MODAL);
                alert.showAndWait();
            }
        }
    }

    private static int i=0;

    private boolean Match(File file){

        int limit =Integer.parseInt(AESEncryption.decrypt(PropertyConfigs.getProperty("UC",file.getPath(),
                null,null),null,true));

        String getEncUser = PropertyConfigs.getProperty("AppUsr"+i+"",file.getPath(),
                null,null);

        String userEncField=AESEncryption.encrypt(UsernameF.getText(),null,true);

        System.out.println(userEncField +"User input");
        System.out.println(getEncUser+"  Other property");



        while (i<=limit){

            if (getEncUser.equals(userEncField)){
//                System.out.println("found");
//                System.out.println(userEncField +"User input internal");
//                System.out.println(getEncUser+"  Other property internal");
                main =true;

            }




//            System.out.println("working" +i);
            i++;
            this.Match(file);
        }


return main;
    }

}
