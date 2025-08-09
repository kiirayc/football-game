package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Read tile data file
        InputStream is = getClass().getResourceAsStream("/maps/FileDataFinal.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Getting names and collision info from the file
        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the tile array based on the file size
        tile = new Tile[fileNames.size()];
        getTileImage();

        // Get the maxWorldRow and maxWorldCol
        is = getClass().getResourceAsStream("/maps/WorldMap100.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

            mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

            br.close();
        } catch (Exception e) {
            System.out.println("Exception");
        }

        loadMap("/maps/WorldMap100000.txt");
    }

    // load tile images
    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collision status
            if (collisionStatus.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }

            setup(i, fileName, collision);
        }
    }

    // set up tile properties
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = UtilityTool.getInstance();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // load map data
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
    	// Calculate the starting X and Y tile indices within the visible screen area
    	// startX and startY are never less than 0 to prevent negative indices when the player moves beyond the left edge of the world
	    int startX = Math.max(0, (gp.player1.worldX - gp.screenX) / gp.tileSize);
	    int startY = Math.max(0, (gp.player1.worldY - gp.screenY) / gp.tileSize);
	    // Determines the top-left tiles
	    
	    // Calculate the ending X and Y tile indices (an extra tile on each side to ensure smooth transition)
	    int endX = Math.min(gp.maxWorldCol, (gp.player1.worldX + gp.screenX + gp.tileSize) / gp.tileSize + 1);
	    int endY = Math.min(gp.maxWorldRow, (gp.player1.worldY + gp.screenY + gp.tileSize) / gp.tileSize + 1);
	    // Determines the bottom-right tiles 

	    // Iterate through the visible tiles
	    for (int worldCol = startX; worldCol < endX; worldCol++) {
	        for (int worldRow = startY; worldRow < endY; worldRow++) {
	        	
	            // Get the tile number at the current world position
	            int tileNum = mapTileNum[worldCol][worldRow];
	            
	            // Calculate the world and screen positions
	            int worldX = worldCol * gp.tileSize;
	            int worldY = worldRow * gp.tileSize;
	            // Takes account of the player’s position and the screen size
	            int screenX = worldX - gp.player1.worldX + gp.screenX;
	            int screenY = worldY - gp.player1.worldY + gp.screenY;
	            
	            // Draw the tile on the screen
	            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
        }
    }

    //A nested static class within the outer class.
    public static class Tile {
        public BufferedImage image;
        public boolean collision = false;
    }
}
