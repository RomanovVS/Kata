package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() {
        String hostName = "localhost";
        String dbName = "testdb";
        String userName = "root";
        String password = "testdb";
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
