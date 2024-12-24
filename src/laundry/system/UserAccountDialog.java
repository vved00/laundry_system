package laundry.system;

import javax.swing.*;
import java.sql.*;

public class UserAccountDialog {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/laundry_manager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Update Profile Dialog
    public static void showUpdateProfileDialog(JFrame parentFrame) {
        String newUsername = JOptionPane.showInputDialog(parentFrame,
                "Enter your new username:",
                "Update Profile",
                JOptionPane.PLAIN_MESSAGE);

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            // Save to the database
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = connection.prepareStatement(
                         "UPDATE accounts SET username = ? WHERE account_id = ?")) {

                stmt.setString(1, newUsername);
                stmt.setInt(2, 1); // Assuming account_id = 1 for now
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Username updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Failed to update username.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parentFrame, "Database error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Username update canceled.");
        }
    }

    // Change Password Dialog
    public static void showChangePasswordDialog(JFrame parentFrame) {
        String currentPassword = JOptionPane.showInputDialog(parentFrame,
                "Enter your current password:",
                "Change Password",
                JOptionPane.PLAIN_MESSAGE);

        if (currentPassword != null && !currentPassword.trim().isEmpty()) {
            String newPassword = JOptionPane.showInputDialog(parentFrame,
                    "Enter your new password:",
                    "Change Password",
                    JOptionPane.PLAIN_MESSAGE);

            if (newPassword != null && !newPassword.trim().isEmpty()) {
                String confirmPassword = JOptionPane.showInputDialog(parentFrame,
                        "Confirm your new password:",
                        "Change Password",
                        JOptionPane.PLAIN_MESSAGE);

                if (newPassword.equals(confirmPassword)) {
                    // Save to the database
                    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         PreparedStatement stmt = connection.prepareStatement(
                                 "UPDATE accounts SET password = ? WHERE account_id = ? AND password = ?")) {

                        stmt.setString(1, newPassword); // Use proper hashing in real apps
                        stmt.setInt(2, 1); // Assuming account_id = 1 for now
                        stmt.setString(3, currentPassword);
                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(parentFrame, "Password changed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(parentFrame, "Invalid current password.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Database error: " + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Passwords do not match!");
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Password change canceled.");
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Password change canceled.");
        }
    }
}
