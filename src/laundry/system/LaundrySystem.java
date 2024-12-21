/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package laundry.system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class LaundrySystem {

    private static final String URL = "jdbc:mysql://localhost:3306/laundry_manager?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static Connection conn;
    
    public static void main(String[] args) {
        
        
        if (connect()) {
            System.out.println("Database connected successfully!");
            
            // Example: Call a method to retrieve data
            fetchData(conn);

        } else{
            System.err.println("Database connection failed: ");
        }
        
        new Laundry_Interface().setVisible(true);
    }

        
    private static void fetchData(Connection connection) {
        String query = "SELECT * FROM accounts";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                System.out.println("First Name: " + resultSet.getString("first_name"));
                System.out.println("Lat Name: " + resultSet.getString("last_name"));
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }
    
    private static boolean connect() {
        try {
            conn = DriverManager.getConnection(URL, "root", "");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
            return false;
        }
    }
    
    public static boolean addCustomer(String firstName, String lastName, String contactNumber) {
        String query = "INSERT INTO customer_log (first_name, last_name, contact_number) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, contactNumber);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Returns true if the insertion was successful
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error adding customer", ex);
            return false;
        }
    }
    
    
}
