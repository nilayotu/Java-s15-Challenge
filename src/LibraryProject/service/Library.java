package LibraryProject.service;

import LibraryProject.model.Author;
import LibraryProject.model.Book;
import LibraryProject.model.Reader;
import LibraryProject.util.BookStatus;

import java.util.*;

public class Library {

        private Map<String, Book> books = new HashMap<>();
        private Map<String, Reader> readers = new HashMap<>();
        private Map<String, Set<String>> authorIndex = new HashMap<>();
        private List<Bill> bills = new ArrayList<>();

        public Collection<Book> getBooks() { //dışarıdan kütüphanedeki kitaplara erişmek için
            return books.values();
        }

        public Reader getReader(String memberId) { //ID'si bilinen okuyucuyu bulmak için
            return readers.get(memberId);
        }

        public void addReader(Reader reader) { //yeni bir okuyucuyu sisteme ekler
            String id = reader.getRecord().getMember();
            readers.put(id, reader);
        }

    public void newBook(Book book) {
        books.put(book.getBookId(), book);

        String author = book.getAuthor().getName();
        if (author == null || author.isBlank()) {
            System.out.println("Yazar bilgisi eksik. Kitap eklenemedi.");
            return;
        }
        String authorKey = author.toLowerCase().trim();
        Set<String> bookIds = authorIndex.get(authorKey);
        if (bookIds == null) {
            bookIds = new HashSet<>();
            authorIndex.put(authorKey, bookIds);
        }
        bookIds.add(book.getBookId());

        System.out.println("Eklendi: " + book.getTitle());
    }

        public void lendBook(Reader reader, String bookId) {
            Book book = books.get(bookId);
            if (book == null) {
                System.out.println("Kitap bulunamadı: " + bookId);
                return;
            }
            if (book.getStatus() != BookStatus.AVAILABLE) {
                System.out.println("Kitap şu an ödünç alınamaz.");
                return;
            }

            boolean hasBorrowed = reader.borrowBook(book);
            if (hasBorrowed) {
                System.out.println("Kitap verildi: " + book.getTitle());

                Bill bill = new Bill(book, reader);
                addBill(bill);

            }
        }

        public void takeBackBook(Reader reader, String bookId) {
            Book book = books.get(bookId);
            if (book == null) {
                System.out.println("Kitap bulunamadı: " + bookId);
                return;
            }

            boolean returned = reader.returnBook(book);  // başarıyla iade edildi mi kontrol et
            if (returned) {
                System.out.println("Kitap geri alındı: " + book.getTitle());

                for (Bill bill : bills) {
                    if (bill.getBook().equals(book) && !bill.isRefunded()) {
                        bill.markAsReturned();
                        System.out.println("Fatura güncellendi:");
                        bill.display();
                        break;
                    }
                }
            } else {
                System.out.println("Kitap uygun değil veya bu okuyucuda değil.");
            }
        }


    public Book findBookFlexible(String keyword) {
        Book byId = books.get(keyword);
        if (byId != null) {
            byId.display();
            return byId;
        }

        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(keyword)) {
                book.display();
                return book;
            }
        }

        Set<String> ids = authorIndex.get(keyword.toLowerCase());
        if (ids != null && !ids.isEmpty()) {
            String id = ids.iterator().next();
            Book book = books.get(id);
            if (book != null) {
                book.display();
                return book;
            }
        }

        System.out.println("Kitap bulunamadı.");
        return null;
    }

        public void listByCategory(Class<?> ctg) { // ? --> jenerik sınıf anlamına gelir, herhangi bir sınıf türünü alabilir
            System.out.println("Kategori: " + ctg.getSimpleName()); // simpleName paket ismi olmadan döndürür
            for (Book book : books.values()) {
                if (ctg.isInstance(book)) {
                    book.display();
                }
            }
        }

        public void listByAuthor(String authorName) {

            if (authorName == null) {
                System.out.println("Yazar adı boş olamaz.");
                return;
            }

            Set<String> ids = authorIndex.get(authorName.toLowerCase());
            if (ids == null || ids.isEmpty()) {
                System.out.println("Bu yazara ait kitap yok: " + authorName);
                return;
            }
            System.out.println(authorName + " isimli yazarın kitapları:");
            for (String id : ids) {
                books.get(id).display();
            }
        }

        public void deleteBook(String bookId) {
            Book removed = books.remove(bookId);
            if (removed != null) {
                String author = removed.getAuthor().getName();
                if (author != null && !author.isBlank()) {
                    String authorKey = author.toLowerCase().trim();
                    Set<String> s = authorIndex.get(authorKey);
                    if (s != null) {
                        s.remove(bookId);
                        if (s.isEmpty()) authorIndex.remove(authorKey);
                    }
                }
                System.out.println("Silindi: " + removed.getTitle());
            } else {
                System.out.println("Silinecek kitap bulunamadı.");
            }
            }

    public void updateBook(String bookId, String newTitle, String newAuthorName, double newPrice, String newEdition) {
        Book book = books.get(bookId);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(new Author(newAuthorName)); // ya da var olan bir Author nesnesi
            book.setPrice(newPrice);
            book.setEdition(newEdition);
            System.out.println("Kitap güncellendi.");
        } else {
            System.out.println("Kitap bulunamadı.");
        }
    }

    public Book findBookById(String bookId) {
        return books.get(bookId); // bookId key, books bir Map<String, Book> olmalı
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getAllBills() {
        return bills;
    }

    public Bill getBillFor(Book book) {
        for (Bill bill : bills) {
            if (bill.getAmount() > 0 && bill.getBook().equals(book) && !bill.isRefunded()) {
                return bill;
            }
        }
        return null;
    }
    }
