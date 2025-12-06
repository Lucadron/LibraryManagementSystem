package com.lucadron.service;

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

    public Member addMember(String name, String email) {
        validateMemberInput(name, email);

        Member member = new Member(name, email);
        return memberRepo.addMember(member);
    }

    public Book addBook(String title, String author, int year) {
        validateBookInput(title, author, year);

        Book book = new Book(title, author, year);
        return bookRepo.addBook(book);
    }

    public void borrowBook(int memberId, int bookId) {
        try {
            connection.setAutoCommit(false);

            Member member = memberRepo.getMemberById(memberId);
            if (member == null) {
                throw new RuntimeException("Üye bulunamadı: " + memberId);
            }

            Book book = bookRepo.getBookById(bookId);
            if (book == null) {
                throw new RuntimeException("Kitap bulunamadı: " + bookId);
            }

            if (book.isBorrowed()) {
                throw new RuntimeException("Kitap zaten ödünç alınmış: " + bookId);
            }

            List<BorrowedBook> borrowedList = borrowRepo.getBorrowedBooksByMemberId(memberId);
            if (borrowedList.size() >= 3) {
                throw new RuntimeException("Bu üye zaten maksimum 3 kitap ödünç almış.");
            }

            BorrowedBook record = new BorrowedBook(memberId, bookId, LocalDateTime.now());
            borrowRepo.addBorrowRecord(record);

            bookRepo.updateBorrowedStatus(bookId, true);

            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback başarısız: " + ex.getMessage());
            }
            throw new RuntimeException("Kitap ödünç alma başarısız: " + e.getMessage());
        }
    }

    public void returnBook(int memberId, int bookId) {
        try {
            connection.setAutoCommit(false);

            Book book = bookRepo.getBookById(bookId);
            if (book == null) {
                throw new RuntimeException("Kitap bulunamadı: " + bookId);
            }

            if (!book.isBorrowed()) {
                throw new RuntimeException("Bu kitap zaten kütüphanede, ödünç değil.");
            }

            borrowRepo.deleteBorrowRecord(memberId, bookId);

            bookRepo.updateBorrowedStatus(bookId, false);

            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback başarısız: " + ex.getMessage());
            }
            throw new RuntimeException("Kitap iade edilemedi: " + e.getMessage());
        }
    }

    public List<BorrowedBook> listBorrowedBooksByMember(int memberId) {
        return borrowRepo.getBorrowedBooksByMemberId(memberId);
    }

    public List<Book> listAllBooks() {
        return bookRepo.getAllBooks();
    }

    private void validateMemberInput(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Üye adı boş olamaz.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email boş olamaz.");
        }

        if (!email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Email formatı geçersiz.");
        }
    }

    private void validateBookInput(String title, String author, int year) {
        if (title == null || title.trim().isEmpty()) {
            throw new RuntimeException("Kitap adı boş olamaz.");
        }

        if (author == null || author.trim().isEmpty()) {
            throw new RuntimeException("Yazar adı boş olamaz.");
        }

        if (year < 0 || year > LocalDateTime.now().getYear()) {
            throw new RuntimeException("Kitap yılı geçerli değil.");
        }
    }
}
