package com.lucadron.model;

import java.time.LocalDateTime;

public class BorrowedBook {

    private int id;
    private int memberId;
    private int bookId;
    private LocalDateTime borrowDate;

    private String memberName;
    private String bookTitle;

    public BorrowedBook() {
    }

    public BorrowedBook(int id, int memberId, int bookId, LocalDateTime borrowDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    public BorrowedBook(int memberId, int bookId, LocalDateTime borrowDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public String toString() {
        // ÖNEMLİ: Çıktıyı senin istediğin forma getiriyoruz:
        // BorrowedBook {id=6, memberId=4(Emre Gülşen), bookId=4(Suç ve Ceza), borrowDate=...}
        StringBuilder sb = new StringBuilder("BorrowedBook {");
        sb.append("id=").append(id);

        sb.append(", memberId=").append(memberId);
        if (memberName != null && !memberName.isBlank()) {
            sb.append(" (").append(memberName).append(")");
        }

        sb.append(", bookId=").append(bookId);
        if (bookTitle != null && !bookTitle.isBlank()) {
            sb.append(" (").append(bookTitle).append(")");
        }

        sb.append(", borrowDate=").append(borrowDate);
        sb.append('}');
        return sb.toString();
    }
}
