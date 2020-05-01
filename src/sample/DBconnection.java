package sample;

import sample.PropertiesConfiguration.PropertyConfigs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {


    public static Connection DbConnection(String username, String password) throws  SQLException {

    String DBriver = PropertyConfigs.getProperty("Driver", "Resources/SoftwareSystemProperties/db.properties",
            null, null);

        String URL = PropertyConfigs.getProperty("ConnectionString", "Resources/SoftwareSystemProperties/db.properties",
                null, null);


    try {
        Class.forName(DBriver);
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    Connection connection;

        connection = DriverManager.getConnection(URL,username,password);



    if (!(connection ==null))
            return connection;
        return null;

}




}



