package com.lucadron.model;

public class Book {

    private int id;
    private String title;
    private String author;
    private int year;
    private boolean isBorrowed;
    private int quantity;

    public Book() {
    }

    public Book(int id, String title, String author, int year, boolean isBorrowed, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isBorrowed = isBorrowed;
        this.quantity = quantity;
    }

    public Book(String title, String author, int year, int quantity) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isBorrowed = false;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", quantity=" + quantity +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
