import LibraryProject.model.*;
import LibraryProject.service.Librarian;
import LibraryProject.service.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        Librarian librarian = new Librarian("Görevli", "1234");


        lib.newBook(new StudyBook("ST01", "Veri Yapıları", "Cem Yılmaz", 80.0, "1. Baskı"));
        lib.newBook(new StudyBook("ST02", "Algoritmalar", "Ayşe Demir", 90.0, "2. Baskı"));
        lib.newBook(new StudyBook("ST03", "Yapay Zeka", "Ali Veli", 120.0, "1. Baskı"));
        lib.newBook(new StudyBook("ST04", "Makine Öğrenimi", "Ahmet Kara", 110.0, "3. Baskı"));
        lib.newBook(new StudyBook("ST05", "Veritabanı", "Zeynep Yılmaz", 95.0, "1. Baskı"));

        lib.newBook(new Journal("JR01", "Bilim ve Teknik", "TÜBİTAK", 30.0, "Ocak 2023"));
        lib.newBook(new Journal("JR02", "Matematik Dünyası", "Prof. Aydın", 40.0, "Mart 2023"));
        lib.newBook(new Journal("JR03", "Fizik Notları", "Dr. Yıldız", 35.0, "Şubat 2023"));
        lib.newBook(new Journal("JR04", "Kimya Bülteni", "Kimya Derneği", 32.5, "Nisan 2023"));
        lib.newBook(new Journal("JR05", "Tıp Güncesi", "Dr. Cem", 38.0, "Mayıs 2023"));

        lib.newBook(new Magazine("MG01", "Popüler Bilim", "Bilim Dergisi", 25.0, "Haziran 2023"));
        lib.newBook(new Magazine("MG02", "Teknoloji Çağı", "TechWorld", 27.0, "Temmuz 2023"));
        lib.newBook(new Magazine("MG03", "Uzay ve Evren", "NASA", 29.0, "Ağustos 2023"));
        lib.newBook(new Magazine("MG04", "Doğa ve Yaşam", "GreenWorld", 26.5, "Eylül 2023"));
        lib.newBook(new Magazine("MG05", "Sanat ve Kültür", "ArtLife", 28.0, "Ekim 2023"));

        // Örnek okuyucular
        Reader r1 = new Student("S1", "Ayşe", "Adres1", "555-111");
        Reader r2 = new Faculty("F1", "Mehmet", "Adres2", "555-222");
        lib.addReader(r1);
        lib.addReader(r2);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- KÜTÜPHANE MENÜSÜ ---");
            System.out.println("1- Yeni Kitap Ekle");
            System.out.println("2- Kitapları Listele");
            System.out.println("3- Kitap Ara (id / isim / yazar)");
            System.out.println("4- Kategoriye Göre Listele");
            System.out.println("5- Yazara Göre Listele");
            System.out.println("6- Kitap Ödünç Ver");
            System.out.println("7- Kitap Geri Al");
            System.out.println("8- Kitap Sil");
            System.out.println("9- Okuyucu Kitapları Göster");
            System.out.println("0- Çıkış");
            System.out.print("Seçim: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Geçersiz seçim.");
                continue;
            }

            switch (ch) {
                case 1: // yeni kitap
                    System.out.print("Tür (1=Study, 2=Journal, 3=Magazine): ");
                    int t = Integer.parseInt(sc.nextLine());
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Ad: ");
                    String title = sc.nextLine();
                    System.out.print("Yazar: ");
                    String author = sc.nextLine();
                    System.out.print("Fiyat: ");
                    double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Baskı: ");
                    String ed = sc.nextLine();

                    Book book;
                    if (t == 1) book = new StudyBook(id, title, author, price, ed);
                    else if (t == 2) book = new Journal(id, title, author, price, ed);
                    else book = new Magazine(id, title, author, price, ed);

                    lib.newBook(book);
                    break;

                case 2: // liste
                    for (Book bk : lib.getBooks()) {
                        bk.display();
                    }
                    break;

                case 3: // esnek arama
                    System.out.print("Anahtar (id/isim/yazar): ");
                    String kw = sc.nextLine();
                    Book found = librarian.searchBook(lib, kw);
                    if (found == null) {
                        System.out.println("Bulunamadı.");
                    } else {
                        found.display();
                    }
                    break;

                case 4: // kategori
                    System.out.println("Kategori (1=Study,2=Journal,3=Magazine): ");
                    int kc = Integer.parseInt(sc.nextLine());
                    if (kc == 1) lib.listByCategory(StudyBook.class);
                    else if (kc == 2) lib.listByCategory(Journal.class);
                    else lib.listByCategory(Magazine.class);
                    break;

                case 5: // yazar
                    System.out.print("Yazar adı: ");
                    String an = sc.nextLine();
                    lib.listByAuthor(an);
                    break;

                case 6: // ödünç ver
                    System.out.print("Üye ID (S1, F1...): ");
                    String memId = sc.nextLine();
                    Reader rr = lib.getReader(memId);
                    if (rr == null) {
                        System.out.println("Üye yok.");
                        break;
                    }
                    System.out.print("Kitap ID: ");
                    String bid = sc.nextLine();
                    librarian.issueBook(lib, rr, bid);
                    break;

                case 7: // geri al
                    System.out.print("Üye ID: ");
                    memId = sc.nextLine();
                    rr = lib.getReader(memId);
                    if (rr == null) {
                        System.out.println("Üye yok.");
                        break;
                    }
                    System.out.print("Kitap ID: ");
                    bid = sc.nextLine();
                    librarian.returnBook(lib, rr, bid);
                    break;

                case 8: // sil
                    System.out.print("Silinecek kitap ID: ");
                    String delId = sc.nextLine();
                    lib.deleteBook(delId);
                    break;

                case 9: // okuyucu kitaplarını göster
                    System.out.print("Üye ID: ");
                    String rid = sc.nextLine();
                    Reader r = lib.getReader(rid);
                    if (r == null) System.out.println("Üye yok.");
                    else r.showBook();
                    break;

                case 0:
                    System.out.println("Çıkılıyor...");
                    return;

                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }
}