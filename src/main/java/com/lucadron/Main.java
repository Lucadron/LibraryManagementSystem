package com.lucadron;

import com.lucadron.controller.LibraryController;
import com.lucadron.i18n.LanguageManager;
import com.lucadron.util.DatabaseMigrator;

public class Main {
    public static void main(String[] args) {

        System.out.println("App starting. Database checking...");
        DatabaseMigrator.migrate();

        LanguageManager.selectLanguageInteractive();

        LibraryController controller = new LibraryController();
        controller.start();
    }
}
