package com.lucadron.util;

import org.flywaydb.core.Flyway;

/**
 * DatabaseMigrator sınıfı Flyway migration sürecini yönetir.
 *
 * Bu sınıf:
 *  - DB bağlantı bilgilerini DOĞRUDAN environment veya default değerlerden okur
 *  - DatabaseConnection sınıfını KESİNLİKLE kullanmaz
 *  - Flyway datasource'unu kendisi oluşturur
 */
public class DatabaseMigrator {

    /**
     * Flyway migration'larını çalıştırır.
     * Uygulama ayağa kalkmadan önce Main.main() içinde çağrılmalıdır.
     */
    public static void migrate() {
        try {
            // Docker ortamı için öncelikle environment'dan oku
            String url = System.getenv("DB_URL");
            if (url == null || url.isBlank()) {
                // Manuel local kullanım için fallback
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

            System.out.println("🔄 Flyway migration baslatiliyor...");
            System.out.println("   URL: " + url);

            // ÖNEMLİ: PostgreSQL desteği için flyway-database-postgresql dependency'si POM'da ekli
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();

            System.out.println("✅ Migration tamamlandi.");

        } catch (Exception e) {
            System.err.println("❌ Migration sirasinda hata olustu: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Migration basarisiz!", e);
        }
    }
}
