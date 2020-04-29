package connectionutil;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectorUtil {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileReader("src/main/resources/application.properties"));
                connection = DriverManager.getConnection(
                        properties.getProperty("db.host"),
                        properties.getProperty("db.user"),
                        properties.getProperty("db.password"));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
