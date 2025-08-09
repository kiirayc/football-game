package main;

import javax.swing.JFrame;

// Main class responsible for starting the Mini Soccer Game
public class Main {
    public static void main(String[] args) {
        // Creating a JFrame (window) 
        JFrame window = new JFrame();
        
        // Setting up window properties
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation on exit
        window.setResizable(false); // Window is not resizable
        window.setTitle("Mini Soccer Game"); // Setting the title

        // Creating an instance of the GamePanel class 
        GamePanel gamePanel = new GamePanel();
        
        // Setting up the game 
        gamePanel.setupGame();

        // Adding the game panel to the window
        window.add(gamePanel);

        // Packing the window to fit the preferred size
        window.pack(); 

        // Centering the window on the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        // Starting the game thread 
        gamePanel.startGameThread();
    }
}
