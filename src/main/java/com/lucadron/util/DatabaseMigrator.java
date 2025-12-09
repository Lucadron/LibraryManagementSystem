package com.lucadron.util;

import org.flywaydb.core.Flyway;

public class DatabaseMigrator {

    public static void migrate() {
        try {
            String url = System.getenv("DB_URL");
            if (url == null || url.isBlank()) {
                url = "jdbc:postgresql://localhost:5432/library_db";
            }

            String user = System.getenv("DB_USER");
            if (user == null || user.isBlank()) {
                user = "library_user";
            }

            String password = System.getenv("DB_PASSWORD");
            if (password == null || password.isBlank()) {
                password = "StrongPassword123!";
            }

            System.out.println("🔄 Flyway migration starting...");
            System.out.println("   URL: " + url);

            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();

            System.out.println("Migration completed!.");

        } catch (Exception e) {
            System.err.println("Error occurred while migration: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Migration failed!", e);
        }
    }
}
