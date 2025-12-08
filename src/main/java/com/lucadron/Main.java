package com.lucadron;

import com.lucadron.controller.LibraryController;
import com.lucadron.i18n.LanguageManager;
import com.lucadron.util.DatabaseMigrator;

public class Main {
    public static void main(String[] args) {

        // 1) Migration'ı uygulama başlamadan önce çalıştır
        System.out.println("🚀 Uygulama baslatiliyor. Veritabani kontrol ediliyor...");
        DatabaseMigrator.migrate();

        // 2) Dil seçimi
        LanguageManager.selectLanguageInteractive();

        // 3) Konsol uygulamasını başlat
        LibraryController controller = new LibraryController();
        controller.start();
    }
}
