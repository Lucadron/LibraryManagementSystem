package com.lucadron.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LanguageManager {

    public enum SupportedLanguage {
        EN, DE, TR
    }

    private static SupportedLanguage currentLanguage = SupportedLanguage.EN;
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);

    public static void selectLanguageInteractive() {
        System.out.println("Select language / Sprache auswaehlen / Dil seciniz:");
        System.out.println("1 - English");
        System.out.println("2 - Deutsch");
        System.out.println("3 - Turkce");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        switch (choice) {
            case "2" -> setLanguage(SupportedLanguage.DE);
            case "3" -> setLanguage(SupportedLanguage.TR);
            default -> setLanguage(SupportedLanguage.EN);
        }

        System.out.println(get("language.selected"));
    }

    public static void setLanguage(SupportedLanguage language) {
        currentLanguage = language;
        Locale locale = switch (language) {
            case EN -> Locale.ENGLISH;
            case DE -> Locale.GERMAN;
            case TR -> new Locale("tr");
        };
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static SupportedLanguage getCurrentLanguage() {
        return currentLanguage;
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static String format(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }
}
