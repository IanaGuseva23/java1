package jm.task.core.jdbc.util;

import java.sql.*;

public final class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/pp1.1.3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root23";
    private static Connection connection;

    private Util() {
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено");
            }
        } catch (SQLException e) {
            System.out.println("Соединение с БД не установлено");
        }
        return connection;

    }
}
