package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import sample.PropertiesConfiguration.PropertyConfigs;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class AESEncryption {

    static String main= PropertyConfigs.getProperty("Encryption","Resources/SecurityProperties/udDB.properties",
            null,null);

    private static String MainKey= "My1@Be&t%Se2uri9";
//    static String Ivpara = "My1@Bes7@5ce2#gh";

    public static String encrypt(String Password,String key,boolean status){

        if (Objects.equals(key,null)&&status){

            key=MainKey;

        }

        String encrypted= new String();

        Key key1 = new SecretKeySpec(key.getBytes(),"AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,key1);
            encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(Password.getBytes()));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return encrypted;

    }

    public static String decrypt(String EncryptedPassword,String key,boolean status){


        if (Objects.equals(key,null)&&status){

            key=MainKey;

        }

        String decrypted= null;

        Key key1 = new SecretKeySpec(key.getBytes(),"AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key1);
            byte[] dec= cipher.doFinal(Base64.getDecoder().decode(EncryptedPassword));
            decrypted= new String(dec);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Alert alert = sAlearts.aleart(Alert.AlertType.ERROR,null,"Invalid Key Length","Program is exiting"
            , Modality.APPLICATION_MODAL);
            alert.showAndWait();
            PropertyConfigs.SetProperty("EncryptKey","null",
                    "Resources/PropertiesMain/Alpha.properties"
                    ,null,null);
            System.exit(0);
            Platform.exit();

            e.printStackTrace();
        }
        return decrypted;

    }

}
