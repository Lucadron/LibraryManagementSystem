package com.lucadron;

import com.lucadron.controller.LibraryController;
import com.lucadron.i18n.LanguageManager;

public class Main {
    public static void main(String[] args) {
        LanguageManager.selectLanguageInteractive();

        LibraryController controller = new LibraryController();
        controller.start();
    }
}
