package io.github.alirostom1.bankc.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankc", "root", "123456789");
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection; 
    }
}
