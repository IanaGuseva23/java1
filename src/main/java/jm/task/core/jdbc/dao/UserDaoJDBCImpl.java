package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE Users (Id INT PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(20), lastName VARCHAR(20), age INT)");
            connection.commit();
            System.out.println("Таблица была создана!");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
            connection.rollback();
        }
    }


    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            connection.commit();
            System.out.println("Таблица была удалена!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
            connection.rollback();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users (firstName, lastName, age) Values (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении!");
                connection.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE Id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с id – " + id + " удалён из базы данных!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении!");
                connection.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            resultSet.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе пользователей!");
            e.printStackTrace();
            connection.rollback();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users");
            connection.commit();
            System.out.println("Данные из таблицы удалены!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении данных!");
                connection.rollback();
            }
        }
    }
