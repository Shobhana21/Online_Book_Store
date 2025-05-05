package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/online_book_store";
    private static final String USER = "root";
    private static final String PASSWORD = "Shobhana@123";

    // Always create a new connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load JDBC driver (optional in newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a new connection each time
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
        return conn;
    }

    // Close a given connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔒 Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // For testing
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connection test passed: Connected to the database!");
            DBConnection.closeConnection(conn);
        } else {
            System.out.println("❌ Connection test failed.");
        }
    }
}
