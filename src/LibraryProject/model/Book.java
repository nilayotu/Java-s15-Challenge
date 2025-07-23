package LibraryProject.model;

import LibraryProject.util.BookStatus;

import java.time.LocalDate;

public class Book {

    private String bookId;
    private Author author;
    private String name;
    private double price;
    private BookStatus status;
    private String edition;
    private LocalDate dateOfPurchase;
    private Reader owner;

    public Book(String bookId, Author author, String name, double price, BookStatus status, String edition, LocalDate dateOfPurchase, Reader owner) {
        this.bookId = bookId;
        this.author = author;
        this.name = name;
        this.price = price;
        this.status = BookStatus.AVAILABLE;
        this.edition = edition;
        this.dateOfPurchase = LocalDate.now();
        this.owner = null;
    }

    public Book(String bookID, String name, String author, double price, String edition) {
    }


    public String getBookId() {
        return bookId;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getEdition() {
        return edition;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public Reader getOwner() {
        return owner;
    }

    public void display() {
        System.out.println("[" + bookId + "] " + name + " / " + author +
                " | Fiyat: " + price +
                " | Durum: " + status +
                " | Sahip: " + (owner == null ? "Kütüphane" : owner.getTitle()) +
                " | Baskı: " + edition +
                " | Alım: " + dateOfPurchase);
    }

    public void changeOwner(Reader newOwner) {
        this.owner = newOwner;
        this.status = BookStatus.BORROWED;
    }

    public void updateStatus(BookStatus newStatus) {
        this.status = newStatus;
        if (newStatus == BookStatus.AVAILABLE) {
            this.owner = null; // geri gelmiş
        }
    }
}
