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
        createConnection();
    }

    private static void loadProperties() {
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties bulunamadı!");
            }

            Properties properties = new Properties();
            properties.load(input);

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            autoCommit = Boolean.parseBoolean(properties.getProperty("db.autocommit", "false"));

        } catch (IOException e) {
            throw new RuntimeException("application.properties okunurken hata oluştu: " + e.getMessage());
        }
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException("Veritabanına bağlanılamadı: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Bağlantı kapatılırken hata oluştu: " + e.getMessage());
        }
    }
}
