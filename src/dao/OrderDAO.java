package dao;

import utils.DBConnection;
import java.sql.*;

public class OrderDAO {

    public boolean placeOrder(int userId, int bookId, int quantity) {
        Connection conn = null;
        PreparedStatement checkBookStmt = null;
        PreparedStatement insertOrderStmt = null;
        PreparedStatement insertOrderItemStmt = null;
        PreparedStatement updateBookQtyStmt = null;

        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Check if book exists and has enough quantity
            String checkBookQuery = "SELECT price, quantity FROM books WHERE id = ?";
            checkBookStmt = conn.prepareStatement(checkBookQuery);
            checkBookStmt.setInt(1, bookId);
            rs = checkBookStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Book not found.");
                return false;
            }

            double price = rs.getDouble("price");
            int availableQty = rs.getInt("quantity");

            if (availableQty < quantity) {
                System.out.println("❌ Not enough stock available.");
                return false;
            }

            double totalAmount = price * quantity;

            // 2. Insert into orders
            String insertOrderQuery = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?)";
            insertOrderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
            insertOrderStmt.setInt(1, userId);
            insertOrderStmt.setDouble(2, totalAmount);
            insertOrderStmt.executeUpdate();

            rs = insertOrderStmt.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                throw new SQLException("Failed to get order ID.");
            }

            // 3. Insert into order_items
            String insertItemQuery = "INSERT INTO order_items (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
            insertOrderItemStmt = conn.prepareStatement(insertItemQuery);
            insertOrderItemStmt.setInt(1, orderId);
            insertOrderItemStmt.setInt(2, bookId);
            insertOrderItemStmt.setInt(3, quantity);
            insertOrderItemStmt.setDouble(4, price);
            insertOrderItemStmt.executeUpdate();

            // 4. Update book quantity
            String updateBookQuery = "UPDATE books SET quantity = quantity - ? WHERE id = ?";
            updateBookQtyStmt = conn.prepareStatement(updateBookQuery);
            updateBookQtyStmt.setInt(1, quantity);
            updateBookQtyStmt.setInt(2, bookId);
            updateBookQtyStmt.executeUpdate();

            conn.commit(); // All steps successful
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (rs != null) rs.close();
                if (checkBookStmt != null) checkBookStmt.close();
                if (insertOrderStmt != null) insertOrderStmt.close();
                if (insertOrderItemStmt != null) insertOrderItemStmt.close();
                if (updateBookQtyStmt != null) updateBookQtyStmt.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
