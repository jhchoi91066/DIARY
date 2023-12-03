import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DarkMode {
    private static boolean isDarkMode = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an instance of Diary
                Diary diary = new Diary();

                // Create Dark Mode Button
                JButton darkModeButton = new JButton("Dark Mode");
                diary.p_north.add(darkModeButton); // Add Dark Mode Button to the panel

                // ActionListener for the dark mode button
                darkModeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        isDarkMode = !isDarkMode; // Toggle dark mode state
                        toggleDarkMode(diary, isDarkMode);
                    }
                });
            }
        });
    }

    // Method to toggle dark mode
    public static void toggleDarkMode(Diary diary, boolean isDarkMode) {
        Color calendarColor;
        Color backgroundColor;
        Color textColor;
    
        if (isDarkMode) {
            // Dark mode colors with improved readability
            backgroundColor = new Color(36, 36, 36); // Darker background
            textColor = Color.WHITE; // White text for contrast
        } else {
            // Original colors
            backgroundColor = Color.LIGHT_GRAY;
            textColor = Color.BLACK;
        }
    
        calendarColor = Color.white; // Date box color remains consistent
    
        // Change background color of the main panel
        diary.p_center.setBackground(backgroundColor);
    
        // Update other components' colors accordingly
        diary.lb_title.setForeground(textColor);
        diary.lb_title.setBackground(backgroundColor);
        diary.lb_title.setOpaque(true);
        diary.bt_prev.setBackground(backgroundColor);
        diary.bt_prev.setForeground(textColor);
        diary.bt_next.setBackground(backgroundColor);
        diary.bt_next.setForeground(textColor);
    
        // Set background color of the JFrame (the calendar)
        diary.getContentPane().setBackground(backgroundColor);
    
        // Set the color for days and date boxes
        for (DateBox dateBox : diary.dateBoxAr) {
            dateBox.setBackground(calendarColor);
            dateBox.color = calendarColor; // Update box color
            dateBox.repaint(); // Repaint the box
        }
    
        // Repaint the UI to reflect the changes
        diary.repaint();
    }
}
