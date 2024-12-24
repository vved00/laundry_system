package laundry.system;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class SystemPreferencesDialog {

    // Method to show system preferences dialog
    public static void showSystemPreferencesDialog(JFrame parentFrame) {
        // Options for the dialog
        Object[] options = {"Change Theme", "Set Language", "Enable Notifications", "Cancel"};

        // Show the dialog
        int choice = JOptionPane.showOptionDialog(parentFrame,
                "What would you like to configure?",
                "System Preferences",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[3]); // Default button is "Cancel"

        // Handle the user's choice
        switch (choice) {
            case 0:
                // Change Theme
                showChangeThemeDialog(parentFrame);
                break;
            case 1:
                // Set Language
                showSetLanguageDialog(parentFrame);
                break;
            case 2:
                // Enable Notifications
                showEnableNotificationsDialog(parentFrame);
                break;
            default:
                // User clicked "Cancel" or closed the dialog
                break;
        }
    }

    // Method to change theme
    private static void showChangeThemeDialog(JFrame parentFrame) {
        String[] themes = {"Light", "Dark"};
        String selectedTheme = (String) JOptionPane.showInputDialog(parentFrame,
                "Select a theme:",
                "Change Theme",
                JOptionPane.QUESTION_MESSAGE,
                null, themes, themes[0]);

        if (selectedTheme != null) {
            // Apply the selected theme
            if (selectedTheme.equals("Light")) {
                // Set Light theme (light background color)
                parentFrame.getContentPane().setBackground(Color.WHITE);
                JOptionPane.showMessageDialog(parentFrame, "Theme changed to Light!");
            } else if (selectedTheme.equals("Dark")) {
                // Set Dark theme (dark background color)
                parentFrame.getContentPane().setBackground(Color.DARK_GRAY);
                JOptionPane.showMessageDialog(parentFrame, "Theme changed to Dark!");
            }
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    // Method to set language
    private static void showSetLanguageDialog(JFrame parentFrame) {
        String[] languages = {"English", "Spanish"};
        String selectedLanguage = (String) JOptionPane.showInputDialog(parentFrame,
                "Select a language:",
                "Set Language",
                JOptionPane.QUESTION_MESSAGE,
                null, languages, languages[0]);

        if (selectedLanguage != null) {
            // Set language logic
            Locale selectedLocale = new Locale(selectedLanguage.equals("English") ? "en" : "es");
            // Set application language based on selectedLocale (this can be expanded with actual localization)
            JOptionPane.showMessageDialog(parentFrame, "Language set to " + selectedLanguage + "!");
        }
    }

    // Method to enable or disable notifications
    private static void showEnableNotificationsDialog(JFrame parentFrame) {
        int option = JOptionPane.showConfirmDialog(parentFrame,
                "Do you want to enable notifications?",
                "Enable Notifications",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Logic to enable notifications (e.g., store in preferences)
            // For now, we'll just show a message
            JOptionPane.showMessageDialog(parentFrame, "Notifications enabled!");
        } else {
            // Logic to disable notifications (e.g., store in preferences)
            JOptionPane.showMessageDialog(parentFrame, "Notifications disabled!");
        }
    }
}
