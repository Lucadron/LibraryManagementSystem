package com.lucadron.repository;

import com.lucadron.model.Member;
import com.lucadron.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    private final Connection connection = DatabaseConnection.getConnection();

    public Member addMember(Member member) {
        String sql = "INSERT INTO members (name, email) VALUES (?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                member.setId(rs.getInt("id"));
            }

            return member;

        } catch (SQLException e) {
            throw new RuntimeException("Üye eklerken hata oluştu: " + e.getMessage());
        }
    }

    public Member getMemberById(int id) {
        String sql = "SELECT * FROM members WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToMember(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Üye bulunamadı (ID: " + id + "): " + e.getMessage());
        }
    }

    public List<Member> getAllMembers() {
        String sql = "SELECT * FROM members ORDER BY id";

        List<Member> members = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                members.add(mapToMember(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Üyeler listelenirken hata oluştu: " + e.getMessage());
        }

        return members;
    }

    private Member mapToMember(ResultSet rs) throws SQLException {
        return new Member(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email")
        );
    }
}
