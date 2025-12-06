package com.lucadron.controller;

import com.lucadron.model.Book;
import com.lucadron.model.BorrowedBook;
import com.lucadron.model.Member;
import com.lucadron.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class LibraryController {

    private final LibraryService service = new LibraryService();
    private final Scanner scanner = new Scanner(System.in);

    // ANSI Color Codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";

    public void start() {
        while (true) {
            printMenu();

            System.out.print(CYAN + "\nSeçiminiz: " + RESET);
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> addBook();
                    case "2" -> addMember();
                    case "3" -> borrowBook();
                    case "4" -> returnBook();
                    case "5" -> listBorrowedByMember();
                    case "6" -> listAllBooks();
                    case "0" -> exitProgram();
                    default -> System.out.println(RED + "❌ Geçersiz seçim!" + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "❌ Hata: " + e.getMessage() + RESET);
            }
        }
    }

    private void printMenu() {
        System.out.println(YELLOW + "\n📚 KÜTÜPHANE YÖNETİM SİSTEMİ" + RESET);
        System.out.println("1️⃣  Kitap ekle");
        System.out.println("2️⃣  Üye ekle");
        System.out.println("3️⃣  Kitap ödünç al");
        System.out.println("4️⃣  Kitap iade et");
        System.out.println("5️⃣  Üyenin ödünç aldığı kitapları listele");
        System.out.println("6️⃣  Tüm kitapları listele");
        System.out.println("0️⃣  Çıkış yap");
    }

    //MENU
    private void addBook() {
        System.out.println("\n📘 Yeni kitap ekle:");

        System.out.print("Başlık: ");
        String title = scanner.nextLine();

        System.out.print("Yazar: ");
        String author = scanner.nextLine();

        System.out.print("Yıl: ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = service.addBook(title, author, year);

        System.out.println(GREEN + "✔ Kitap eklendi: " + RESET + book);
    }

    private void addMember() {
        System.out.println("\n👤 Yeni üye ekle:");

        System.out.print("İsim: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Member member = service.addMember(name, email);

        System.out.println(GREEN + "✔ Üye eklendi: " + RESET + member);
    }

    private void borrowBook() {
        System.out.println("\n📕 Kitap ödünç alma:");

        System.out.print("Üye ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        service.borrowBook(memberId, bookId);

        System.out.println(GREEN + "✔ Kitap ödünç alındı!" + RESET);
    }

    private void returnBook() {
        System.out.println("\n📗 Kitap iade et:");

        System.out.print("Üye ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        service.returnBook(memberId, bookId);

        System.out.println(GREEN + "✔ Kitap iade edildi!" + RESET);
    }

    private void listBorrowedByMember() {
        System.out.println("\n📙 Üyenin ödünç aldığı kitaplar:");

        System.out.print("Üye ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        List<BorrowedBook> list = service.listBorrowedBooksByMember(memberId);

        if (list.isEmpty()) {
            System.out.println(YELLOW + "⚠ Bu üyenin ödünç aldığı kitap yok." + RESET);
            return;
        }

        list.forEach(item -> System.out.println(CYAN + item + RESET));
    }

    private void listAllBooks() {
        System.out.println("\n📚 Tüm kitaplar:");

        List<Book> books = service.listAllBooks();
        books.forEach(book -> System.out.println(CYAN + book + RESET));
    }

    private void exitProgram() {
        System.out.println(GREEN + "\n👋 Programdan çıkılıyor..." + RESET);
        System.exit(0);
    }
}
