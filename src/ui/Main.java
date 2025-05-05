package ui;

import dao.BookDAO;
import dao.UserDAO;
import model.Book;
import model.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;

        System.out.println("📚 Welcome to Online Book Store (Console Version)");
        System.out.println("🔐 Please log in to continue:");

        // 🔐 Login Loop
        while (loggedInUser == null) {
            System.out.print("Username(email): ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            loggedInUser = userDAO.loginUser(username, password);

            if (loggedInUser == null) {
                System.out.println("❌ Invalid credentials. Please try again.");
            }
        }

        System.out.println("✅ Welcome, " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")");

        // 📘 Menu Loop
        while (true) {
            System.out.println("\n📋 Menu:");
            System.out.println("1. View All Books");
            if (loggedInUser.getRole().equalsIgnoreCase("admin")) {
                System.out.println("2. Add Book");
            }
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
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

                case 2:
                    if (!loggedInUser.getRole().equalsIgnoreCase("admin")) {
                        System.out.println("❌ Access denied. Only admin can add books.");
                        break;
                    }

                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    Book newBook = new Book(title, author, price, quantity);
                    boolean isAdded = bookDAO.addBook(newBook);

                    if (isAdded) {
                        System.out.println("✅ Book added successfully!");
                    } else {
                        System.out.println("❌ Failed to add book.");
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