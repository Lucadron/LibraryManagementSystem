package com.lucadron.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection;
    private static String url;
    private static String user;
    private static String password;
    private static boolean autoCommit;

    static {
        loadProperties();
        overrideWithEnvIfPresent();
        createConnection();
    }

    private static void loadProperties() {
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties not found!");
            }

            Properties properties = new Properties();
            properties.load(input);

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            autoCommit = Boolean.parseBoolean(properties.getProperty("db.autocommit", "false"));

        } catch (IOException e) {
            throw new RuntimeException("application.properties failed to reading.: " + e.getMessage(), e);
        }
    }

    private static void overrideWithEnvIfPresent() {
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.isBlank()) {
            url = envUrl;
        }

        String envUser = System.getenv("DB_USER");
        if (envUser != null && !envUser.isBlank()) {
            user = envUser;
        }

        String envPassword = System.getenv("DB_PASSWORD");
        if (envPassword != null && !envPassword.isBlank()) {
            password = envPassword;
        }

        String envAutoCommit = System.getenv("DB_AUTOCOMMIT");
        if (envAutoCommit != null && !envAutoCommit.isBlank()) {
            autoCommit = Boolean.parseBoolean(envAutoCommit);
        }
    }

    private static void createConnection() {
        try {
            if (url == null || user == null || password == null) {
                throw new IllegalStateException("Database connection information is missing! (url/user/password)");
            }

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException("Can not connect to database: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while app closing: " + e.getMessage());
        }
    }
}
