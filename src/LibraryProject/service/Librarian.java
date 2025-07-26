package LibraryProject.service;

import LibraryProject.model.Book;
import LibraryProject.model.Reader;

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
        lib.lendBook(reader, bookId);
    }

    public void returnBook(Library lib, Reader reader, String bookId) {
        lib.takeBackBook(reader, bookId);
    }

    public String getName() {
        return name;
    }

    public String whoYouAre() {
        return "Librarian: " + name;
    }

}
