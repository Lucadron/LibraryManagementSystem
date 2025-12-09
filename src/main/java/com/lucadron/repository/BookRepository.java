package com.lucadron.repository;

import com.lucadron.model.Book;
import com.lucadron.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final Connection connection = DatabaseConnection.getConnection();

    public Book addBook(Book book) {
        String sql = "INSERT INTO books (title, author, year, is_borrowed) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setBoolean(4, book.isBorrowed());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                book.setId(rs.getInt("id"));
            }
            return book;

        } catch (SQLException e) {
            throw new RuntimeException("Kitap eklerken hata oluştu: " + e.getMessage());
        }
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToBook(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Kitap bulunamadı (ID: " + id + "): " + e.getMessage());
        }
    }

    public List<Book> searchBooksByTitleOrAuthor(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Arama ifadesi null olamaz.");
        }

        String trimmed = query.trim();
        if (trimmed.length() < 2) {
            // ÖNEMLİ: Çok kısa sorgularda veritabanına gitmiyoruz
            throw new IllegalArgumentException("Arama ifadesi en az 2 karakter olmalı.");
        }

        String sql = "SELECT id, title, author, year, is_borrowed " +
                "FROM books " +
                "WHERE LOWER(title) LIKE LOWER(?) OR LOWER(author) LIKE LOWER(?) " +
                "ORDER BY title ASC, author ASC";

        List<Book> results = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String pattern = "%" + trimmed + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Mevcut yardımcı metodu yeniden kullanıyoruz (DRY)
                    Book book = mapToBook(rs);
                    results.add(book);
                }
            }
        } catch (SQLException e) {
            // ÖNEMLİ: Burada swallow etmiyoruz, üst katmana anlamlı mesajla fırlatıyoruz
            throw new RuntimeException("Kitap araması sırasında hata oluştu: " + e.getMessage(), e);
        }

        return results;
    }

    public List<Book> searchBooksByTitle(String title) {
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?)";

        List<Book> results = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(mapToBook(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Kitap arama hatası: " + e.getMessage());
        }

        return results;
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books ORDER BY id";
        List<Book> books = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(mapToBook(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Kitaplar listelenirken hata oluştu: " + e.getMessage());
        }

        return books;
    }

    public void updateBorrowedStatus(int bookId, boolean isBorrowed) {
        String sql = "UPDATE books SET is_borrowed = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isBorrowed);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Kitap ödünç durumu güncellenemedi: " + e.getMessage());
        }
    }

    private Book mapToBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year"),
                rs.getBoolean("is_borrowed")
        );
    }
}
