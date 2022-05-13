package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE userstable (Id INT AUTO_INCREMENT PRIMARY KEY, Name VARCHAR(20), LastName VARCHAR(20), Age INT)");
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS userstable");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT userstable(Name, LastName, Age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
        }

    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM userstable WHERE Id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> res = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM userstable")) {
            while (rs.next()) {
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                byte age = rs.getByte(4);
                res.add(new User(name, lastName, age));
            }

        }
        return res;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM userstable");
        }
    }
}
