package com.lucadron.service;

import com.lucadron.i18n.LanguageManager;
import com.lucadron.model.Book;
import com.lucadron.model.BorrowedBook;
import com.lucadron.model.Member;
import com.lucadron.repository.BookRepository;
import com.lucadron.repository.BorrowedBookRepository;
import com.lucadron.repository.MemberRepository;
import com.lucadron.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class LibraryService {

    private final BookRepository bookRepo = new BookRepository();
    private final MemberRepository memberRepo = new MemberRepository();
    private final BorrowedBookRepository borrowRepo = new BorrowedBookRepository();
    private final Connection connection = DatabaseConnection.getConnection();

    // ---------------------------
    // ÜYE EKLEME
    // ---------------------------
    public Member addMember(String name, String email) {
        validateMemberInput(name, email);
        Member member = new Member(name, email);
        return memberRepo.addMember(member);
    }

    // ---------------------------
    // KİTAP EKLEME (quantity ile)
    // ---------------------------
    public Book addBook(String title, String author, int year) {
        return addBook(title, author, year, 1);
    }

    public Book addBook(String title, String author, int year, int quantity) {
        validateBookInput(title, author, year, quantity);

        if (quantity < 1) {
            throw new RuntimeException(LanguageManager.get("error.book.quantity"));
        }

        Book book = new Book(title, author, year, quantity);
        return bookRepo.addBook(book);
    }

    // ---------------------------
    // KİTAP ÖDÜNÇ ALMA
    // ---------------------------
    public void borrowBook(int memberId, int bookId) {
        try {
            connection.setAutoCommit(false);

            Member member = memberRepo.getMemberById(memberId);
            if (member == null) {
                throw new RuntimeException(LanguageManager.format("error.member.notfound", memberId));
            }

            Book book = bookRepo.getBookById(bookId);
            if (book == null) {
                throw new RuntimeException(LanguageManager.format("error.book.notfound", bookId));
            }

            if (book.getQuantity() <= 0) {
                throw new RuntimeException(LanguageManager.get("error.book.noStock"));
            }

            List<BorrowedBook> borrowedList = borrowRepo.getBorrowedBooksByMemberId(memberId);
            if (borrowedList.size() >= 3) {
                throw new RuntimeException(LanguageManager.get("error.member.maxBooks"));
            }

            BorrowedBook record = new BorrowedBook(memberId, bookId, LocalDateTime.now());
            borrowRepo.addBorrowRecord(record);

            // quantity azalt
            bookRepo.updateQuantity(bookId, book.getQuantity() - 1);

            connection.commit();

        } catch (Exception e) {
            rollbackQuietly();
            throw new RuntimeException(LanguageManager.format("error.borrow.failed", e.getMessage()));
        }
    }

    // ---------------------------
    // KİTAP İADE
    // ---------------------------
    public void returnBook(int memberId, int bookId) {
        try {
            connection.setAutoCommit(false);

            Book book = bookRepo.getBookById(bookId);
            if (book == null) {
                throw new RuntimeException(LanguageManager.format("error.book.notfound", bookId));
            }

            // İade kaydı varsa sil
            borrowRepo.deleteBorrowRecord(memberId, bookId);

            // quantity artır
            bookRepo.updateQuantity(bookId, book.getQuantity() + 1);

            connection.commit();

        } catch (Exception e) {
            rollbackQuietly();
            throw new RuntimeException(LanguageManager.format("error.return.failed", e.getMessage()));
        }
    }

    // ---------------------------
    // ÜYEYE GÖRE ÖDÜNÇ LİSTELEME
    // ---------------------------
    public List<BorrowedBook> listBorrowedBooksByMember(int memberId) {
        List<BorrowedBook> list = borrowRepo.getBorrowedBooksByMemberId(memberId);

        for (BorrowedBook bb : list) {
            Member member = memberRepo.getMemberById(bb.getMemberId());
            if (member != null) bb.setMemberName(member.getName());

            Book book = bookRepo.getBookById(bb.getBookId());
            if (book != null) bb.setBookTitle(book.getTitle());
        }

        return list;
    }

    // ---------------------------
    // TÜM KİTAPLAR
    // ---------------------------
    public List<Book> listAllBooks() {
        return bookRepo.getAllBooks();
    }

    // ---------------------------
    // TÜM ÜYELER
    // ---------------------------
    public List<Member> listAllMembers() {
        List<Member> members = memberRepo.getAllMembers();

        if (members == null || members.isEmpty()) {
            throw new RuntimeException(LanguageManager.get("list.members.none"));
        }

        return members;
    }

    // ---------------------------
    // KİTAP ARAMA (partial search)
    // ---------------------------
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new RuntimeException(LanguageManager.get("error.search.empty"));
        }

        return bookRepo.searchBooks(keyword);
    }

    // ---------------------------
    // VALIDATION
    // ---------------------------
    private void validateMemberInput(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException(LanguageManager.get("error.validation.memberNameEmpty"));
        }
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException(LanguageManager.get("error.validation.memberEmailEmpty"));
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new RuntimeException(LanguageManager.get("error.validation.memberEmailFormat"));
        }
    }

    private void validateBookInput(String title, String author, int year, int quantity) {
        if (title == null || title.trim().isEmpty()) {
            throw new RuntimeException(LanguageManager.get("error.validation.bookTitleEmpty"));
        }
        if (author == null || author.trim().isEmpty()) {
            throw new RuntimeException(LanguageManager.get("error.validation.bookAuthorEmpty"));
        }
        if (year < 0 || year > LocalDateTime.now().getYear()) {
            throw new RuntimeException(LanguageManager.get("error.validation.bookYear"));
        }
        if (quantity < 1) {
            throw new RuntimeException(LanguageManager.get("error.book.quantity"));
        }
    }

    private void rollbackQuietly() {
        try { connection.rollback(); } catch (SQLException ignored) {}
    }
}
