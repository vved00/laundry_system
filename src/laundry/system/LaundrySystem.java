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

/**
 *
 * @author Admin
 */
public class LaundrySystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String URL = "jdbc:mysql://localhost:3306/laundry_manager?zeroDateTimeBehavior=CONVERT_TO_NULL";
        try (Connection connection = DriverManager.getConnection(URL, "root", "")) {
            System.out.println("Database connected successfully!");
            
            // Example: Call a method to retrieve data
            fetchData(connection);

        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
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
    
}
