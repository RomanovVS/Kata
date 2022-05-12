package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Statement statement;

    {
        try {
        Connection connection = Util.getConnection();
        statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE userstable (Id INT AUTO_INCREMENT PRIMARY KEY, Name VARCHAR(20), LastName VARCHAR(20), Age INT)");
    }

    public void dropUsersTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS userstable");

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        statement.executeUpdate("INSERT userstable(Name, LastName, Age) VALUES ('" + name + "' , '" + lastName + "', " + age + ")");
    }

    public void removeUserById(long id) throws SQLException {
        statement.executeUpdate("DELETE FROM userstable WHERE Id=" + id +";");
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> res = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM userstable");
        while (rs.next()) {
            String name = rs.getString(2);
            String lastName = rs.getString(3);
            byte age = rs.getByte(4);
            res.add(new User(name, lastName, age));
        }
        return res;
    }

    public void cleanUsersTable() throws SQLException {
        statement.executeUpdate("DELETE FROM userstable");
    }
}
