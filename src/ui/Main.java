package ui;

import dao.BookDAO;
import model.Book;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.println("📚 Welcome to Online Book Store (Console Version)");

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();

                    Book newBook = new Book(title, author, price, quantity);
                    boolean isAdded = bookDAO.addBook(newBook);

                    if (isAdded) {
                        System.out.println("✅ Book added successfully!");
                    } else {
                        System.out.println("❌ Failed to add book.");
                    }
                    break;

                case 2:
                    List<Book> books = bookDAO.getAllBooks();
                    if (books.isEmpty()) {
                        System.out.println("📭 No books available.");
                    } else {
                        System.out.println("\n📚 Book List:");
                        for (Book book : books) {
                            System.out.println(book);
                        }
                    }
                    break;

                case 3:
                    System.out.println("👋 Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }
}
