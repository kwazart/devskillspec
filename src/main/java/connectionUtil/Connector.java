package connectionUtil;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Connector {

    private static void setConnection(Connection connection) {
        Connector.connection = connection;
    }

    private static Connection connection;

    public static int searchMaxIndex(String table) {
        String query = "SELECT * FROM " + table + ";";
        List<List<String>> list = select(query);
        return list.size();
    }

    public static void startConnection() {
        if (getConnection() == null) {
            Properties properties = new Properties();
            Connection connection = null;
            try {
                properties.load(new FileReader("src/main/resources/application.properties"));
                connection = DriverManager.getConnection(
                        properties.getProperty("db.host"),
                        properties.getProperty("db.user"),
                        properties.getProperty("db.password"));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            Connector.setConnection(connection);
        }
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

    public static List<List<String>> select(String query) {
        List<List<String>> list = new ArrayList<List<String>>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            statement =  getConnection().createStatement();
            resultSet = statement.executeQuery(query);


            if (query.contains("SELECT * FROM developers")) {
                while (resultSet.next()) {
                    List<String> line = new ArrayList<String>();
                    line.add(String.valueOf((resultSet.getInt("id"))));
                    line.add(resultSet.getString("firstName"));
                    line.add(resultSet.getString("lastName"));
                    line.add(resultSet.getString("specialty"));
                    line.add(resultSet.getString("skills"));
                    line.add(resultSet.getString("status"));
                    list.add(line);
                }
            } else if (query.contains("SELECT * FROM skills")) {
                while (resultSet.next()) {
                    List<String> line = new ArrayList<String>();
                    line.add(String.valueOf(resultSet.getInt("id")));
                    line.add(resultSet.getString("skill_name"));
                    line.add(resultSet.getString("status"));
                    list.add(line);
                }
            } else if (query.contains("SELECT * FROM specialties")){
                while (resultSet.next()) {
                    List<String> line = new ArrayList<String>();
                    line.add(String.valueOf(resultSet.getInt("id")));
                    line.add(resultSet.getString("specialty_name"));
                    line.add(resultSet.getString("status"));
                    list.add(line);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void addOrUpdate(String line) {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate(line);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConnection() {
        return Connector.connection;
    }
}
