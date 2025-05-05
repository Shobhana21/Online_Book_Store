package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private int quantity;

    // Default constructor
    public Book() {
    }

    // Parameterized constructor
    public Book(int id, String title, String author, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public Book(String title, String author, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // toString method (helpful for printing)
    @Override
    public String toString() {
        return "Book [ID=" + id + ", Title=" + title + ", Author=" + author +
                ", Price=" + price + ", Quantity=" + quantity + "]";
    }
}
