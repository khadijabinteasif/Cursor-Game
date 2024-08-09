/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cursorgame;

/**
 *
 * @author hp
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CursorGame extends JFrame {
    private boolean cursorControlled = false;
    private JLabel messageLabel;
    private JLabel titleLabel;

    public CursorGame() {
        setTitle("Cursor Game");
        setSize(800, 600); // Reduced size of JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a panel to hold the title and message labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Add title label
        titleLabel = new JLabel("Cursor Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Add message label
        messageLabel = new JLabel("Click anywhere to start the game!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(messageLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Add mouse listener to the frame
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!cursorControlled) {
                    // If cursor is not controlled yet, start controlling it
                    cursorControlled = true;
                    messageLabel.setText(""); // Clear the message
                    setCursor(new Cursor(Cursor.MOVE_CURSOR)); // Change cursor to move cursor

                    // Start a thread to move the cursor randomly
                    Thread cursorThread = new Thread(() -> {
                        while (cursorControlled) {
                            int x = (int) (Math.random() * getWidth()); // Generate random x coordinate
                            int y = (int) (Math.random() * getHeight()); // Generate random y coordinate
                            Point point = new Point(x, y); // Create a point with random coordinates
                            try {
                                Robot robot = new Robot(); // Create a Robot object to control mouse
                                robot.mouseMove((int) point.getX(), (int) point.getY()); // Move mouse to the random point
                                Thread.sleep(50); // Adjust the speed of cursor movement
                            } catch (AWTException | InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    cursorThread.start(); // Start the cursor movement thread
                } else {
                    // If cursor is already controlled, stop controlling it
                    cursorControlled = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Change cursor back to default
                    showEndGameDialog("Player won! Game Over"); // Show end game options
                }
            }
        });
    }

    private void showEndGameDialog(String message) {
        // Show dialog with options to exit or play again
        int option = JOptionPane.showOptionDialog(
            this,
            message,
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Play Again", "Exit"},
            "Play Again"
        );

        if (option == JOptionPane.YES_OPTION) {
            // Restart the game
            cursorControlled = false;
            messageLabel.setText("Click anywhere to start the game!");
        } else {
            // Exit the game
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // Create and display the game window
        SwingUtilities.invokeLater(() -> {
            CursorGame game = new CursorGame();
            game.setVisible(true);
        });
    }
}

