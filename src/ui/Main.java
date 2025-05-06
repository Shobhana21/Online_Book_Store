package ui;

import dao.BookDAO;
import dao.UserDAO;
import model.Book;
import model.User;
import dao.OrderDAO;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;

        System.out.println("📚 Welcome to Online Book Store (Console Version)");

        // 🔐 Pre-login menu: Register / Login
        while (loggedInUser == null) {
            System.out.println("\n🔑 1. Register");
            System.out.println("🔐 2. Login");
            System.out.println("❌ 3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    registerUserFlow(userDAO, scanner);
                    break;

                case 2:
                    System.out.print("Username(email): ");
                    String email = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    loggedInUser = userDAO.loginUser(email, password);

                    if (loggedInUser == null) {
                        System.out.println("❌ Invalid credentials. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("👋 Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("⚠️ Invalid option.");
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

    // 🧑‍💻 Registration flow
    private static void registerUserFlow(UserDAO userDAO, Scanner scanner) {
        System.out.println("\n📝 Register New User:");

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Role (admin/viewer): ");
        String role = scanner.nextLine();

        User newUser = new User(name, email, password, role);
        boolean isRegistered = userDAO.registerUser(newUser);

        if (isRegistered) {
            System.out.println("✅ Registration successful. You can now login.");
        } else {
            System.out.println("❌ Registration failed. Email might already be in use.");
        }
    }
}
