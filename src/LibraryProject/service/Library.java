package LibraryProject.service;

import LibraryProject.model.Book;
import LibraryProject.model.Reader;
import LibraryProject.util.BookStatus;

import java.util.*;

public class Library {

        private Map<String, Book> books = new HashMap<>();
        private Map<String, Reader> readers = new HashMap<>();
        private Map<String, Set<String>> authorIndex = new HashMap<>();

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

        String author = book.getAuthor().getName();  // getName() metodunu Author'dan aldığını varsayıyorum
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
            }
        }

        public void takeBackBook(Reader reader, String bookId) {
            Book book = books.get(bookId);
            if (book == null) {
                System.out.println("Kitap bulunamadı: " + bookId);
                return;
            }
            reader.returnBook(book);
            System.out.println("Kitap geri alındı: " + book.getTitle());
        }

        public void showBook(String bookId) {  //bu metodu silip yalnızca flexible metodunu kullanabiliriz
            Book book = books.get(bookId);
            if (book == null) {
                System.out.println(bookId + "numaralı ID'ye sahip kitap yok.");
            } else {
                book.display();
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

        public void updateBookInfo(String bookId, Double newPrice, String newEdition) {
            Book b = books.get(bookId);
            if (b == null) {
                System.out.println("Kitap yok: " + bookId);
                return;
            }
            System.out.println("(Not) Fiyat/Baskı değiştirme için setter eklenmeli.");
        }
    }
