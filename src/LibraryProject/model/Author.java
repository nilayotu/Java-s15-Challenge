package LibraryProject.model;

import java.util.HashSet;
import java.util.Set;

public class Author extends Person {

    private Set<Book> books = new HashSet<>(); //aynı kitapların tekrar eklenememesi için hashset kullandım

    public Author(String name) {
        super(name);
    }

    @Override
    public String whoYouAre() {
        return "Author: " + getName();
    }

    public void newBook(Book b) {
        books.add(b);
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println(getName() + " yazarlı kitap yok.");
        } else {
            System.out.println(getName() + " yazarı kitapları:");
            for (Book b : books) {
                b.display();
            }
        }
    }
}
