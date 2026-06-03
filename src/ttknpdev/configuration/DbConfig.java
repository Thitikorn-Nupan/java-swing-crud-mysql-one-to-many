package ttknpdev.configuration;

import ttknpdev.log.CustomLog4j;

import java.sql.*;
import java.util.ResourceBundle;

public class DbConfig {

    //  we can use ResourceBundle class for accessing any properties file that you define
    private ResourceBundle resourceBundle;
    private Connection connect;
    private CustomLog4j customLog4j;

    public DbConfig() {
        customLog4j = new CustomLog4j(DbConfig.class);
        resourceBundle = ResourceBundle.getBundle("resources/info/db_info");
    }

    public Connection getConnect() {
        try {
            /**
             Class<?> loadDriver = Class.forName(resourceBundle.getString("MYSQL_DRIVER"));
             loadDriver.getClasses(); //  called Load Driver
             Can reduce !
             */
            Class.forName(resourceBundle.getString("MYSQL_DRIVER")); //  called Load Driver
            connect = DriverManager.getConnection(
                    resourceBundle.getString("MYSQL_URL"),
                    resourceBundle.getString("MYSQL_USERNAME"),
                    resourceBundle.getString("MYSQL_PASSWORD")
            ); // connect to database
            customLog4j.log4j.info("connected database");
            return connect;
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            throw new RuntimeException(sqlException.getCause());
        } catch (ClassNotFoundException classNotFoundException) {
            customLog4j.log4j.warn("ClassNotFoundException class has error : " + classNotFoundException.getMessage());
            throw new RuntimeException(classNotFoundException.getCause());
        }
    }

    public void closeConnect() {
        try {
            connect.close();
            customLog4j.log4j.info("closed database");
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            throw new RuntimeException(sqlException.getCause());
        }
    }

}
