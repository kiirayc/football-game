package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveLoad {
	
    // Singleton instance variable
    private static SaveLoad instance = new SaveLoad();

    // Static method to retrieve the singleton instance
    public static SaveLoad getInstance() {
        return instance;
    }

    private SaveLoad() {}

    public void save(int idx, int P1_worldX, int P1_worldY, int P2_worldX, int P2_worldY, int P1_life, int P2_life, double playTime, 
    		int blueScore, int redScore) {
        // Print to console to indicate that the save function is working
        System.out.println("Save function working");

        // Define the URL, database username, and password
        String url = "jdbc:mysql://localhost:3306/GAME_DB"; 
        String id = "root";
        String pw = "GKeeper0303";

        try (Connection conn = DriverManager.getConnection(url, id, pw)) {
            // Create a connection to the database 

            String sql = "UPDATE TB_SAVE SET P1_WORLD_X=?, P1_WORLD_Y=?, P2_WORLD_X=?, P2_WORLD_Y=?, P1_LIFE=?, P2_LIFE=?, TIME=?, "
            		+ "BLUE_SCORE=?, RED_SCORE=? WHERE IDX = ?"; 

            
            // Create a PreparedStatement
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set the parameter values for the SQL 
            statement.setInt(1, P1_worldX);
            statement.setInt(2, P1_worldY); 
            statement.setInt(3, P2_worldX);
            statement.setInt(4, P2_worldY);
            statement.setInt(5, P1_life); 
            statement.setInt(6, P2_life); 
            statement.setDouble(7, playTime); 
            statement.setInt(8, blueScore); 
            statement.setInt(9, redScore); 
            statement.setInt(10, idx); // Set the index parameter

            // Execute the SQL statement to insert data 
            statement.executeUpdate();

        } catch (SQLException e) {
            // If an SQLException occurs, catch the exception and print an error message
            System.out.println("SaveLoad.save() Error: " + e.toString());
        }
    }

    //getinstance = Singleton Pattern
    
   
    public void load(int idx, GamePanel gp) {
        // Print load function is working
        System.out.println("Load function working");

        // Define the URL, database username, and password
        String url = "jdbc:mysql://localhost:3306/GAME_DB"; 
        String id = "root";
        String pw = "GKeeper0303";

        try (Connection conn = DriverManager.getConnection(url, id, pw)) {
            // Define the SQL statement to retrieve data from the database
            String sql = "SELECT * FROM TB_SAVE WHERE IDX=?"; 

            // Create a PreparedStatement
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set the parameter value for the SQL 
            statement.setInt(1, idx);

            // Execute the SQL statement to retrieve data 
            ResultSet resultSet = statement.executeQuery();

            // Process retrieved data
            while (resultSet.next()) {
                // Retrieve data from the ResultSet and store it in variables
                int P1_worldX = resultSet.getInt("P1_WORLD_X");
                int P1_worldY = resultSet.getInt("P1_WORLD_Y");
                int P2_worldX = resultSet.getInt("P2_WORLD_X");
                int P2_worldY = resultSet.getInt("P2_WORLD_Y");
                int P1_life = resultSet.getInt("P1_LIFE");
                int P2_life = resultSet.getInt("P2_LIFE");
                float time = resultSet.getFloat("TIME");
                int blueScore = resultSet.getInt("BLUE_SCORE");            
                int redScore = resultSet.getInt("RED_SCORE"); 
                
                // Load the values
                gp.player1.worldX = P1_worldX;
                gp.player1.worldY = P1_worldY;
                gp.player2.worldX = P2_worldX;
                gp.player2.worldY = P2_worldY;
                gp.player1.life = P1_life;
                gp.player2.life = P2_life;
                gp.ui.playTime = time;
                gp.ui.blueScore = blueScore;
                gp.ui.redScore = redScore;  
                
            }
        } catch (SQLException e) {
            System.out.println("SaveLoad.load() Error: " + e.toString());
        }
    }
}
