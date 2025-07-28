package LibraryProject.service;

import LibraryProject.model.Book;
import LibraryProject.model.Reader;
import LibraryProject.util.BookStatus;

public class Librarian {

    private String name;
    private String password;

    public Librarian(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean verifyMember(Reader r) {
        return r != null;
    }

    public Book searchBook(Library lib, String keyword) {
        return lib.findBookFlexible(keyword);
    }

    public void issueBook(Library lib, Reader reader, String bookId) {
        Book book = lib.findBookById(bookId);

        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return;
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.println("Kitap zaten ödünç alınmış.");
            return;
        }

        boolean success = reader.borrowBook(book);
        if (success) {
            book.changeOwner(reader);
            book.updateStatus(BookStatus.BORROWED);

            Bill bill = new Bill(book, reader);
            lib.addBill(bill);

            System.out.println(reader.getName() + " -> ödünç aldı: " + book.getTitle());
            System.out.println("Kitap verildi: " + book.getTitle());
            bill.display(); // İsteğe bağlı
        } else {
            System.out.println("Okuyucu kitap limitine ulaştı.");
        }
    }

    public void returnBook(Library lib, Reader reader, String bookId) {
        Book book = lib.findBookById(bookId);

        if (book != null && book.getStatus() == BookStatus.BORROWED && reader.hasBook(book)) {
            reader.returnBook(book);                    // 1. okuyucudan kitabı al
            book.updateStatus(BookStatus.AVAILABLE);    // 2. kitap durumunu güncelle
            lib.takeBackBook(reader, bookId);           // 3. kitap kütüphaneye eklenir

            Bill bill = lib.getBillFor(book);
            if (bill != null) {
                bill.refund();
                System.out.println("Fatura güncellendi:");
                bill.display();
            } else {
                System.out.println("İade edilecek fatura bulunamadı.");
            }

            System.out.println(reader.getName() + " -> iade etti: " + book.getTitle());
            System.out.println("Kitap geri alındı: " + book.getTitle());

        } else {
            System.out.println("Kitap uygun değil veya bu okuyucuda değil.");
        }
    }

    public String getName() {
        return name;
    }

    public String whoYouAre() {
        return "Librarian: " + name;
    }

}
