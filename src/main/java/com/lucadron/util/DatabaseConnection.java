package com.lucadron.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnection sınıfı, JDBC bağlantısını yönetir.
 *
 * - application.properties dosyasını okur
 * - Eğer environment variable tanımlıysa (DB_URL, DB_USER, DB_PASSWORD, DB_AUTOCOMMIT)
 *   config değerlerini override eder
 * - Tek bir Connection örneği döndürür
 *
 * ÖNEMLİ:
 * - Manuel kurulumda: application.properties kullanılır (localhost)
 * - Docker kurulumunda: env değişkenleri ile container içindeki PostgreSQL'e bağlanılır
 */
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

    /**
     * application.properties dosyasını classpath'ten yükler.
     */
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
            throw new RuntimeException("application.properties okunurken hata oluştu: " + e.getMessage(), e);
        }
    }

    /**
     * Ortam değişkenleri tanımlıysa config değerlerini override eder.
     *
     * Örneğin Docker ortamında:
     *  - DB_URL=jdbc:postgresql://postgres:5432/library_db
     *  - DB_USER=library_user
     *  - DB_PASSWORD=StrongPassword123!
     */
    private static void overrideWithEnvIfPresent() {
        // URL
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.isBlank()) {
            url = envUrl;
        }

        // Kullanıcı adı
        String envUser = System.getenv("DB_USER");
        if (envUser != null && !envUser.isBlank()) {
            user = envUser;
        }

        // Şifre
        String envPassword = System.getenv("DB_PASSWORD");
        if (envPassword != null && !envPassword.isBlank()) {
            password = envPassword;
        }

        // Auto-commit
        String envAutoCommit = System.getenv("DB_AUTOCOMMIT");
        if (envAutoCommit != null && !envAutoCommit.isBlank()) {
            autoCommit = Boolean.parseBoolean(envAutoCommit);
        }
    }

    /**
     * PostgreSQL bağlantısını oluşturur.
     */
    private static void createConnection() {
        try {
            if (url == null || user == null || password == null) {
                throw new IllegalStateException("Veritabanı bağlantı bilgileri eksik! (url/user/password)");
            }

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            // Burada log yazılabilir, ama minimum seviyede explanatory mesaj veriyoruz.
            throw new RuntimeException("Veritabanına bağlanılamadı: " + e.getMessage(), e);
        }
    }

    /**
     * Global Connection nesnesini döndürür.
     * Repository katmanı bu metodu kullanmalıdır.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Uygulama kapanırken bağlantıyı kapatır.
     */
    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Bağlantı kapatılırken hata oluştu: " + e.getMessage());
        }
    }
}
