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



    // ---------------- Getters & Setters ----------------

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
        return "BorrowedBook {" +
                "id=" + id +
                ", memberId=" + memberId + "(" + memberName + ")" +
                ", bookId=" + bookId + "(" + bookTitle + ")" +
                ", borrowDate=" + borrowDate +
                '}';
    }
}
