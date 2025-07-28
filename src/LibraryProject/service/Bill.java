package LibraryProject.service;

import LibraryProject.model.Book;
import LibraryProject.model.Reader;

import java.time.LocalDate;

public class Bill {

    private Book book;
    private Reader reader;
    private double amount;
    private LocalDate issueDate;
    private boolean isRefunded;
    private boolean isReturned;

    public Bill(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.amount = book.getPrice();
        this.issueDate = LocalDate.now();
        this.isRefunded = false;
    }

    public void markAsReturned() {
        this.isReturned = true;
    }

    public Book getBook() {
        return book;
    }

    public boolean isRefunded() {
        return isRefunded;
    }

    public double getAmount() {
        return isRefunded ? 0 : amount;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void refund() {
        this.isRefunded = true;
    }

    public void display() {
        System.out.println("--- FATURA ---");
        System.out.println("Kitap: " + book.getTitle());
        System.out.println("Okuyucu: " + reader.getName());
        System.out.println("Tarih: " + issueDate);
        System.out.println("Tutar: " + amount + " TL");
        System.out.println("İade Durumu: " + (isRefunded ? "İade Edildi" : "İade Edilmedi"));
        System.out.println("--------------");
    }

    @Override
    public String toString() {
        return "--- FATURA ---\n" +
                "Kitap: " + book.getTitle() + "\n" +
                "Okuyucu: " + reader.getName() + "\n" +
                "Tarih: " + issueDate + "\n" +
                "Tutar: " + amount + " TL\n" +
                "İade Durumu: " + (isReturned ? "İade Edildi" : "İade Edilmedi") + "\n" +
                "--------------\n";
    }
}