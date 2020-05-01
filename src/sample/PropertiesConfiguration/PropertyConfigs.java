package sample.PropertiesConfiguration;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

import java.io.*;

/**
 * Created by Suraj on 4/28/2017.
 */
public class PropertyConfigs {
    private static int i;


    public static void SetProperty(String Key, String Value, String FileNameAndPath, String defaultkey[], String DefaultValue[])  {

        FileReader reader;
        FileWriter writer;
        PropertiesConfiguration configuration = new PropertiesConfiguration();

        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(configuration);

        File file= new File(FileNameAndPath);

        if(file.exists()){
            try {

                reader  = new FileReader(FileNameAndPath);
                layout.load(reader);
                reader.close();
            } catch (ConfigurationException | IOException e) {
                e.printStackTrace();
            }
            configuration.setProperty(Key, Value);
            try {
               writer  = new FileWriter(FileNameAndPath,false);
                layout.save(writer);
                writer.close();

            } catch (ConfigurationException | IOException e) {
                e.printStackTrace();
            }

        }

        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Configuration found");
            alert.setHeaderText("File Not Found");
            alert.setContentText("New Configuration file with default values will be created");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();

            try {
                file.createNewFile();
                i = defaultkey.length;
                for (int j = 0; j < i; j++) {
                    configuration.setProperty(defaultkey[j], DefaultValue[j]);
                }
                FileWriter writer1 = new FileWriter(FileNameAndPath,false);
                layout.save(writer1);
                writer1.close();

            } catch (IOException e1) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, e1.toString());
                alert1.setHeaderText("Error in Creating file");
                alert1.showAndWait();
                e1.printStackTrace();
            } catch (ConfigurationException e1) {
                e1.printStackTrace();
            }

        }


    }

private static String recive;
    public static String getProperty(String Key, String FileNameAndPath, String defaultkey[], String DefaultValue[]) {

        FileReader reader;
        FileWriter writer;
        PropertiesConfiguration configuration = new PropertiesConfiguration();

        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(configuration);

        File file = new File(FileNameAndPath);
        if (file.exists()) {
            try {
                reader  = new FileReader(FileNameAndPath);
                layout.load(reader);
            } catch (ConfigurationException | FileNotFoundException e) {
                e.printStackTrace();
            }
            recive = String.valueOf(configuration.getProperty(Key));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Configuration found");
            alert.setHeaderText("File Not Found");
            alert.setContentText("New Configuration file with default values will be created");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            try {
                file.createNewFile();
                i = defaultkey.length;
                for (int j = 0; j < i; j++) {
                    configuration.setProperty(defaultkey[j], DefaultValue[j]);
                }
                writer = new FileWriter(FileNameAndPath,false);
                layout.save(writer);
            } catch (IOException e1) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, e1.toString());
                alert1.setHeaderText("Error in Creating The file");
                alert1.showAndWait();
                e1.printStackTrace();
            } catch (ConfigurationException e1) {
                e1.printStackTrace();
            }

        }


            return recive;

        }

private static boolean result;
    public static boolean FustRunFile(String filename){

        File file = new File(filename);
        if (file.exists())
        {
            result=true;
        }
      else {
            result= false;

        }
return result;

    }


}
