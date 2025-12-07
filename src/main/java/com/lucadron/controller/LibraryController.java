package com.lucadron.controller;

import com.lucadron.i18n.LanguageManager;
import com.lucadron.model.Book;
import com.lucadron.model.BorrowedBook;
import com.lucadron.model.Member;
import com.lucadron.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class LibraryController {

    private final LibraryService service = new LibraryService();
    private final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";

    public void start() {
        while (true) {
            printMenu();

            System.out.print(CYAN + "\n" + LanguageManager.get("menu.prompt.choice") + " " + RESET);
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
                    default -> System.out.println(RED + "X| " + LanguageManager.get("error.invalid.choice") + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "X| " + e.getMessage() + RESET);
            }
        }
    }

    private void printMenu() {
        System.out.println(YELLOW + "\n" + LanguageManager.get("menu.title") + RESET);
        System.out.println("1 - " + LanguageManager.get("menu.option.addBook"));
        System.out.println("2 - " + LanguageManager.get("menu.option.addMember"));
        System.out.println("3 - " + LanguageManager.get("menu.option.borrowBook"));
        System.out.println("4 - " + LanguageManager.get("menu.option.returnBook"));
        System.out.println("5 - " + LanguageManager.get("menu.option.listBorrowedByMember"));
        System.out.println("6 - " + LanguageManager.get("menu.option.listAllBooks"));
        System.out.println("0 - " + LanguageManager.get("menu.option.exit"));
    }

    private void addBook() {
        System.out.println("\n " + LanguageManager.get("book.add.header"));

        System.out.print(LanguageManager.get("book.prompt.title") + " ");
        String title = scanner.nextLine();

        System.out.print(LanguageManager.get("book.prompt.author") + " ");
        String author = scanner.nextLine();

        System.out.print(LanguageManager.get("book.prompt.year") + " ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = service.addBook(title, author, year);

        System.out.println(GREEN + "ok| " + LanguageManager.format("book.add.success", book) + RESET);
    }

    private void addMember() {
        System.out.println("\n " + LanguageManager.get("member.add.header"));

        System.out.print(LanguageManager.get("member.prompt.name") + " ");
        String name = scanner.nextLine();

        System.out.print(LanguageManager.get("member.prompt.email") + " ");
        String email = scanner.nextLine();

        Member member = service.addMember(name, email);

        System.out.println(GREEN + "ok| " + LanguageManager.format("member.add.success", member) + RESET);
    }

    private void borrowBook() {
        System.out.println("\n " + LanguageManager.get("borrow.header"));

        System.out.print(LanguageManager.get("borrow.prompt.memberId") + " ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print(LanguageManager.get("borrow.prompt.bookId") + " ");
        int bookId = Integer.parseInt(scanner.nextLine());

        service.borrowBook(memberId, bookId);

        System.out.println(GREEN + "ok| " + LanguageManager.get("borrow.success") + RESET);
    }

    private void returnBook() {
        System.out.println("\n " + LanguageManager.get("return.header"));

        System.out.print(LanguageManager.get("return.prompt.memberId") + " ");
        int memberId = Integer.parseInt(scanner.nextLine());

        System.out.print(LanguageManager.get("return.prompt.bookId") + " ");
        int bookId = Integer.parseInt(scanner.nextLine());

        service.returnBook(memberId, bookId);

        System.out.println(GREEN + "ok| " + LanguageManager.get("return.success") + RESET);
    }

    private void listBorrowedByMember() {
        System.out.println("\n " + LanguageManager.get("list.borrowed.header"));

        System.out.print(LanguageManager.get("borrow.prompt.memberId") + " ");
        int memberId = Integer.parseInt(scanner.nextLine());

        List<BorrowedBook> list = service.listBorrowedBooksByMember(memberId);

        if (list.isEmpty()) {
            System.out.println(YELLOW + "!| " + LanguageManager.get("list.borrowed.none") + RESET);
            return;
        }

        list.forEach(item -> System.out.println(CYAN + item + RESET));
    }

    private void listAllBooks() {
        System.out.println("\n " + LanguageManager.get("list.all.header"));

        List<Book> books = service.listAllBooks();
        books.forEach(book -> System.out.println(CYAN + book + RESET));
    }

    private void exitProgram() {
        System.out.println(GREEN + "\n" + LanguageManager.get("exit.message") + RESET);
        System.exit(0);
    }
}
