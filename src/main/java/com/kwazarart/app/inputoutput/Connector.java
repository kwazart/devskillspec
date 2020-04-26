package java.com.kwazarart.app.inputoutput;

import java.com.kwazarart.app.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {
    public static int searchMaxIndex(String table) {
        String query = "SELECT * FROM " + table + ";";
        List<List<String>> list = select(query);
        return list.size();
    }

    public static List<List<String>> select(String query) {
        List<List<String>> list = new ArrayList<List<String>>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    JdbcProperties.getURL(),
                    JdbcProperties.getUSERNAME(),
                    JdbcProperties.getPASSWORD());

            statement =  connection.createStatement();
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(
                    JdbcProperties.getURL(),
                    JdbcProperties.getUSERNAME(),
                    JdbcProperties.getPASSWORD());

            statement = connection.createStatement();
            statement.executeUpdate(line);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
