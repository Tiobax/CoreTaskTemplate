package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user " +
            "(id BIGINT auto_increment PRIMARY KEY , " +
            "name VARCHAR(45) NOT NULL, " +
            "lastName VARCHAR(45) NOT NULL, " +
            "age TINYINT NOT NULL)";
    private final String SEVE_USER = "INSERT INTO user(name, lastName, age) VALUES (?, ?, ?)";
    private final String GET_ALL = "SELECT * FROM user";
    private final String REMOVE_USER = "DELETE FROM user WHERE id=?";
    private final String DROP_TABlE = "DROP TABLE IF EXISTS user";
    private final String CLEAN_TABLE = "TRUNCATE TABLE user";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
            getCommitRollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(DROP_TABlE)) {
            preparedStatement.executeUpdate();
            getCommitRollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(SEVE_USER)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.println("User с именем – " + name  + " добавлен в базу данных");
            getCommitRollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(REMOVE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            getCommitRollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(GET_ALL)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                list.add(user);
            }
            getCommitRollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
            getCommitRollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void getCommitRollback() {
        try {
            Util.getConnection().commit();
        } catch (SQLException ex1) {
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex2) {
                System.out.println("При попытке роллбэка произошла ошибка");
                ex2.printStackTrace();
            }
            System.out.println("При попытке комита произошла ошибка");
            ex1.printStackTrace();
        }
    }

}
