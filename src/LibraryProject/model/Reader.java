package LibraryProject.model;

import LibraryProject.util.BookStatus;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {

    private MemberRecord record; //COMPOSITION
    private List<Book> books = new ArrayList<>(); //KISININ ODUNC ALDIGI KITAPLAR

    public Reader(String name, MemberRecord record) {
        super(name);
        this.record = record;
    }

    public void purchaseBook(Book b) {
        System.out.println(getName() + " kitabı satın aldı: " + b.getTitle());
    }

    public boolean borrowBook(Book b) {
        if (!record.canBorrow()) {
            System.out.println("Limit dolu! (" + record.getNoBooksIssued() + "/" + record.getMaxBookLimit() + ")");
            return false;
        }
        if (b.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Kitap müsait değil: " + b.getTitle());
            return false;
        }
        books.add(b);
        record.incBookIssued();
        b.changeOwner(this);
        return true;
    }

    public boolean returnBook(Book b) {
        if (!books.remove(b)) {
            System.out.println("Bu kitap okuyucuda görünmüyor: " + b.getTitle());
            return false;
        }
        record.decBookIssued();
        b.updateStatus(BookStatus.AVAILABLE);
        System.out.println(getName() + " -> iade etti: " + b.getTitle());
        return true;
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println(getName() + " kullanıcısının ödünç aldığı kitap yok.");
        } else {
            System.out.println(getName() + " kullanıcısındaki kitaplar:");
            for (Book b : books) b.display();
        }
    }

    public boolean hasBook(Book book) {
        return books.contains(book);
    }

    public MemberRecord getRecord() {
        return record;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String whoYouAre() {
        return "Reader: " + getName();
    }
}
