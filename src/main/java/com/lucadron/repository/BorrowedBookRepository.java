package com.lucadron.repository;

import com.lucadron.model.BorrowedBook;
import com.lucadron.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookRepository {

    private final Connection connection = DatabaseConnection.getConnection();

    public BorrowedBook addBorrowRecord(BorrowedBook record) {
        String sql = "INSERT INTO borrowed_books (member_id, book_id, borrow_date) VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, record.getMemberId());
            stmt.setInt(2, record.getBookId());
            stmt.setTimestamp(3, Timestamp.valueOf(record.getBorrowDate()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                record.setId(rs.getInt("id"));
            }

            return record;

        } catch (SQLException e) {
            throw new RuntimeException("Ödünç kaydı eklenirken hata oluştu: " + e.getMessage());
        }
    }

    public List<BorrowedBook> getBorrowedBooksByMemberId(int memberId) {
        String sql = "SELECT * FROM borrowed_books WHERE member_id = ? ORDER BY borrow_date DESC";

        List<BorrowedBook> list = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapToBorrowedBook(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ödünç alınan kitaplar listelenirken hata oluştu: " + e.getMessage());
        }

        return list;
    }

    public boolean isBookBorrowed(int bookId) {
        String sql = "SELECT COUNT(*) FROM borrowed_books WHERE book_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Kitabın ödünç durumu kontrol edilirken hata oluştu: " + e.getMessage());
        }
    }

    public void deleteBorrowRecord(int memberId, int bookId) {
        String sql = "DELETE FROM borrowed_books WHERE member_id = ? AND book_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("İade işlemi başarısız oldu: " + e.getMessage());
        }
    }

    private BorrowedBook mapToBorrowedBook(ResultSet rs) throws SQLException {
        return new BorrowedBook(
                rs.getInt("id"),
                rs.getInt("member_id"),
                rs.getInt("book_id"),
                rs.getTimestamp("borrow_date").toLocalDateTime()
        );
    }
}
