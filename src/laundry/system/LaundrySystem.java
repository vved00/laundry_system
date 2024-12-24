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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Admin
 */
public class LaundrySystem {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/laundry_manager?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static Connection conn;
    
    public static void main(String[] args) {
        
        if (connect()) {
            System.out.println("Database connected successfully!");
        } else{
            System.err.println("Database connection failed: ");
        }
        new Laundry_Interface().setVisible(true);
        
    }
  
    // db connection
    public static boolean connect() {
        try {
            conn = DriverManager.getConnection(URL, "root", "");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
            return false;
        }
    }
    //add customer
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
    //edit customer
    public static boolean editCustomer(int customerId, String firstName, String lastName, String contactNumber) {
        String query = "UPDATE customer_log SET first_name = ?, last_name = ?, contact_number = ? WHERE customer_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, contactNumber);
            pst.setInt(4, customerId);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error editing customer", ex);
            return false;
        }
    }
    //delete customer
    public static boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM customer_log WHERE customer_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, customerId);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error deleting customer", ex);
            return false;
        }
    }
    //fetch customer data
    public static DefaultTableModel getCustomerTableModel() {
        String query = "SELECT customer_id, first_name, last_name, contact_number FROM customer_log";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Customer ID", "First Name", "Last Name", "Contact Number"}, 0);

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet resultSet = pst.executeQuery()) {

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("customer_id"));
                row.add(resultSet.getString("first_name"));
                row.add(resultSet.getString("last_name"));
                row.add(resultSet.getString("contact_number"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // For debugging
        }

        return tableModel;
    }
    // fetch laundry data
    public static DefaultTableModel getLaundryLog(){
        String query = "SELECT laundry_log.laundry_id, laundry_log.laundry_weight, laundry_log.laundry_received_time, " +
               "laundry_log.laundry_claimed_time, customer_log.customer_id, customer_log.first_name, " +
               "customer_log.last_name, customer_log.contact_number, laundry_status.status_name AS laundry_status, " +
               "services.service_name AS laundry_service, " +
               "services.price_per_kg FROM laundry_log " +
               "JOIN customer_log ON laundry_log.laundry_owner = customer_log.customer_id " +
               "JOIN services ON laundry_log.laundry_service = services.service_id " +
               "JOIN laundry_status ON laundry_log.laundry_status = laundry_status.status_id " +
               "WHERE laundry_log.laundry_status != 5";
        
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{
                "Laundry ID",  "Name", "Contact Number", "Weight (kg)",
                "Service Type", "Price" , "Received Time", "Laundry Status"
            }, 0);
        
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet resultSet = pst.executeQuery()) {
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("laundry_id"));
                String name = resultSet.getString("first_name").concat(" " + resultSet.getString("last_name"));
                row.add(name);
                row.add(resultSet.getString("contact_number"));
                int price_kg = resultSet.getInt("price_per_kg");
                float weight = resultSet.getInt("laundry_weight");
                row.add(weight);
                row.add(resultSet.getString("laundry_service"));
                row.add(price_kg * weight);
                row.add(resultSet.getString("laundry_received_time"));
                row.add(resultSet.getString("laundry_status"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // For debugging
        }
        
        return tableModel;
    }
    // add to queue
    public static boolean addQueue(int owner, float weight, int service, String date, int status){
        String query = "INSERT INTO laundry_log (laundry_weight, laundry_owner, laundry_service, laundry_received_time, laundry_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setFloat(1, weight);
            preparedStatement.setInt(2, owner);
            preparedStatement.setInt(3, service);
            preparedStatement.setString(4, date);
            preparedStatement.setInt(5, status);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if the insertion was successful
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error adding customer", ex);
            return false;
        }
    }
    // edit queue
    public static boolean editQueue(int laundry_id, int payment_status, int laundry_status, String dNt){
        String query = "UPDATE laundry_log SET laundry_claimed_time = ?, laundry_status = ?, payment_status = ? WHERE laundry_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)){
            pst.setString(1, dNt);
            pst.setInt(2, laundry_status);
            pst.setInt(3, payment_status);
            pst.setInt(4, laundry_id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error editing customer", ex);
            return false;
        }
    }

    // get service fee
    public static float fetchServiceFee(int service){
        String query = "SELECT * FROM services " + "WHERE services.service_id = ?";
        float price = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) { 
            preparedStatement.setInt(1, service); 
            try (ResultSet resultSet = preparedStatement.executeQuery()) { 
                if (resultSet.next()) { price = resultSet.getFloat("price_per_kg"); } 
            } 
        } catch (SQLException e) { 
            System.err.println("Error executing query: " + e.getMessage()); }
        return price;
    }

    // get current date and time
    public static String getDateNTime(){
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }
    // display laundry log
    public static DefaultTableModel laundryLog(){
        String query = "SELECT laundry_log.laundry_id, laundry_log.laundry_weight, laundry_log.laundry_received_time, " +
               "laundry_log.laundry_claimed_time, customer_log.customer_id, customer_log.first_name, " +
               "customer_log.last_name, customer_log.contact_number, laundry_status.status_name AS laundry_status, " +
               "services.service_name AS laundry_service, services.price_per_kg, " +
               "payment_status.payment_status_name " +
               "FROM laundry_log " +
               "JOIN customer_log ON laundry_log.laundry_owner = customer_log.customer_id " +
               "JOIN services ON laundry_log.laundry_service = services.service_id " +
               "JOIN payment_status ON laundry_log.payment_status = payment_status.payment_id " +
               "JOIN laundry_status ON laundry_log.laundry_status = laundry_status.status_id ";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{
                "No",  "Name", "Contact Number", "Weight (kg)",
                "Service Type", "Price" , "Payment Status", "Received Time", "Claimed Time", "Laundry Status"
            }, 0);
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet resultSet = pst.executeQuery()) {
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("laundry_id"));
                String name = resultSet.getString("first_name").concat(" " + resultSet.getString("last_name"));
                row.add(name);
                row.add(resultSet.getString("contact_number"));
                int price_kg = resultSet.getInt("price_per_kg");
                float weight = resultSet.getInt("laundry_weight");
                row.add(weight);
                row.add(resultSet.getString("laundry_service"));
                row.add(price_kg * weight);
                row.add(resultSet.getString("payment_status_name"));
                row.add(resultSet.getString("laundry_received_time"));
                row.add(resultSet.getString("laundry_claimed_time"));
                row.add(resultSet.getString("laundry_status"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // For debugging
        }
        
        return tableModel;
  
  
    // edit queue
    public static boolean editQueue(int laundry_id, int payment_status, int laundry_status, String dNt){
        String query = "UPDATE laundry_log SET laundry_claimed_time = ?, laundry_status = ?, payment_status = ? WHERE laundry_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)){
            pst.setString(1, dNt);
            pst.setInt(2, laundry_status);
            pst.setInt(3, payment_status);
            pst.setInt(4, laundry_id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error editing customer", ex);
            return false;
        }
    }
    
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, username); // Set the username
            pst.setString(2, password); // Set the password
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Returns true if a matching record is found
            }
        } catch (SQLException ex) {
            Logger.getLogger(LaundrySystem.class.getName()).log(Level.SEVERE, "Error validating login", ex);
            return false;
        }
    }
}
