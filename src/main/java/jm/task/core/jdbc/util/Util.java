package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection = null;

    public static Connection getConnection() {
        String hostName = "localhost";
        String dbName = "testdb";
        String userName = "root";
        String password = "testdb";
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
