package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = null;
    //private Statement statement = null;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " name VARCHAR(32) null, " +
                    " last_name VARCHAR(32) null, " +
                    " age TINYINT)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "DROP TABLE IF EXISTS USERS");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO USERS (name, last_name, age) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM USERS WHERE id = " + id)) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM USERS");

            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                list.add(new User(name, lastName, age));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;

    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM USERS");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
