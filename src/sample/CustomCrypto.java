package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.PropertiesConfiguration.PropertyConfigs;
import java.io.File;
import java.io.IOException;

public class CustomCrypto {


  private static   File file = new File("Resources/SoftwareSystemProperties/cyptoTemp.properties");
public String cryptoEnc(String passwordRaw, String Path, String Key){
            if (PropertyConfigs.getProperty("Encryption","Resources/SecurityProperties/udDB.properties",
                    null,null).equals("true")){
                if(Path.isEmpty()||Path.equals(null)||Key.isEmpty()||Key.equals(null)){

                    return AESEncryption.encrypt(passwordRaw,null,true);



                }
                else if ((!Path.isEmpty()||!Path.equals(null))&&(!Key.equals(null)||!Key.isEmpty())){

                    String Upre;
                    Upre = AESEncryption.encrypt(passwordRaw,null,true);
                    PropertyConfigs.SetProperty(Key,Upre,Path,null,null);
                    return Upre;

                }

            }



   else if (PropertyConfigs.getProperty("Encryption","Resources/SecurityProperties/udDB.properties",
            null,null).equals("false")){
        if(Path.isEmpty()||Path.equals(null)){

            String getP=PropertyConfigs.getProperty("EncryptKey","Resources/PropertiesMain/Alpha.properties"
                    ,null,null);
            if (!getP.equals("null")){

                String  SavedKey = AESEncryption.decrypt(getP,null,true);
                return AESEncryption.encrypt(passwordRaw,SavedKey,false);
            }
            loadToAsk();

            return AESEncryption.encrypt(passwordRaw,CustomCryptoPrompt.main,false);


        }
        else if ((!Path.isEmpty()||!Path.equals(null))&&(!Key.equals(null)||Key.isEmpty())){
            String getP=PropertyConfigs.getProperty("EncryptKey","Resources/PropertiesMain/Alpha.properties"
                    ,null,null);
            if (!file.exists()) {
                if (!getP.equals("null")) {

                    String SavedKey = AESEncryption.decrypt(getP, null, true);
                    return AESEncryption.encrypt(passwordRaw, SavedKey, false);
                }
                loadToAsk();

                String Upre = AESEncryption.encrypt(passwordRaw, CustomCryptoPrompt.main, false);
                PropertyConfigs.SetProperty(Key, Upre, Path, null, null);
                return Upre;
            }
            else {
                String getPLocal=PropertyConfigs.getProperty("cryptoKey",file.getPath()
                        ,null,null);

                String SavedKey = AESEncryption.decrypt(getPLocal, null, true);

                return AESEncryption.encrypt(passwordRaw, SavedKey, false);

            }

        }

    }

return null;

        }


        public static String CryptoDec(String EncString, String PathToget, String Key){

            if (PropertyConfigs.getProperty("Encryption","Resources/SecurityProperties/udDB.properties",
                    null,null).equals("true")){
                if((PathToget.isEmpty()||PathToget.equals(null))&&(Key.isEmpty()||Key.equals(null))){

                    return AESEncryption.decrypt(EncString,null,true);

                }

              else if (!PathToget .isEmpty()||!PathToget.equals(null)||!Key.equals(null)||!Key.isEmpty()){

                    String getEnc = PropertyConfigs.getProperty(Key,PathToget,null,null);
                    return AESEncryption.decrypt(getEnc,null,true);

                }


            }

            else if (PropertyConfigs.getProperty("Encryption","Resources/SecurityProperties/udDB.properties",
                    null,null).equals("false")){

                if(PathToget.isEmpty()||PathToget.equals(null)){

                    String getP=PropertyConfigs.getProperty("EncryptKey","Resources/PropertiesMain/Alpha.properties"
                            ,null,null);
                    if (!getP.equals("null")){

                        String  SavedKey = AESEncryption.decrypt(getP,null,true);
                        return AESEncryption.decrypt(EncString,SavedKey,false);
                    }
                    loadToAsk();

                    return AESEncryption.decrypt(EncString,CustomCryptoPrompt.main,false);


                }
                else if ((!PathToget.isEmpty()||!PathToget.equals(null))&&(!Key.equals(null)||Key.isEmpty())){
                    String getP=PropertyConfigs.getProperty("EncryptKey","Resources/PropertiesMain/Alpha.properties"
                            ,null,null);

                    String passGet= PropertyConfigs.getProperty(Key,PathToget,null,null);

                    if (!file.exists()) {
                        if (!getP.equals("null")) {


                            String SavedKey = AESEncryption.decrypt(getP, null, true);
                            return AESEncryption.decrypt(passGet, SavedKey, false);
                        }
                        loadToAsk();
                        return AESEncryption.decrypt(passGet, CustomCryptoPrompt.main, false);
                    }
                    else {

                        String getPLocal=PropertyConfigs.getProperty("cryptoKey",file.getPath()
                                ,null,null);
                        String SavedKey = AESEncryption.decrypt(getPLocal, null, true);

                        return AESEncryption.decrypt(passGet, SavedKey, false);


                    }


                }


            }


    return null;

        }


    private static void loadToAsk(){


        try {
            AnchorPane anchorPane = FXMLLoader.load(sample.CustomCryptoPrompt.class.getResource("InitSetupWiz/ResourcesSetupWiz/CustomCrypto.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
