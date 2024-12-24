package laundry.system;

import javax.swing.*;
public class UserAccountDialog {

    public static void showUpdateProfileDialog(JFrame parentFrame) {
        String newUsername = JOptionPane.showInputDialog(parentFrame, 
                "Enter your new username:", 
                "Update Profile", 
                JOptionPane.PLAIN_MESSAGE);

        if (newUsername != null && !newUsername.trim().isEmpty()) {
            String newEmail = JOptionPane.showInputDialog(parentFrame, 
                    "Enter your new email:", 
                    "Update Profile", 
                    JOptionPane.PLAIN_MESSAGE);

            if (newEmail != null && !newEmail.trim().isEmpty()) {
                // Add logic to update the profile (e.g., save to the database)
                JOptionPane.showMessageDialog(parentFrame, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Email update canceled.");
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Username update canceled.");
        }
    }

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
                    // Add logic to change the password (e.g., save to the database)
                    JOptionPane.showMessageDialog(parentFrame, "Password changed successfully!");
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
