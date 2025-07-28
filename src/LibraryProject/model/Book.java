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

    public Book(String bookID, String name, String authorName, double price, String edition) {
        this.bookId = bookID;
        this.name = name;
        this.author = new Author(authorName); // Author nesnesi oluşturuluyor
        this.price = price;
        this.edition = edition;
        this.status = BookStatus.AVAILABLE;
        this.dateOfPurchase = LocalDate.now();
        this.owner = null;
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

    public void setTitle(String title) {
        this.name = title;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void display() {
        System.out.println("[" + bookId + "] " + name + " / " + author +
                " | Fiyat: " + price +
                " | Durum: " + status +
                " | Sahip: " + (owner == null ? "Kütüphane" : owner.getName()) +
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

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", author=" + author +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", edition='" + edition + '\'' +
                ", dateOfPurchase=" + dateOfPurchase +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.getBookId().equals(book.getBookId());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getBookId());
    }
}
