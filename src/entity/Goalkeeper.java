package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.GamePanel;
import main.UtilityTool;

public class Goalkeeper extends Entity {
    
    GamePanel gp;
    List<BufferedImage> up;
    List<BufferedImage> down;
    List<BufferedImage> left;
    List<BufferedImage> right;

    // Constructor for Goalkeeper class
    public Goalkeeper(GamePanel gp) {
        super(gp); // Call the constructor of the superclass (Entity)
        this.gp = gp;
        
        type = 2;
        name = "Goalkeeper";
        speed = 1;
        
        // Set initial dimensions (solid area)
        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 16;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        up = new ArrayList<>();
        down = new ArrayList<>();
        left = new ArrayList<>();
        right = new ArrayList<>();
        getImage(); 
        
    }
    
    public void getImage() {
        UtilityTool tool = UtilityTool.getInstance();
        
        // Load images for "up" direction
        up.add(tool.loadImage("/goalkeeper/goalkeeperback1", gp.tileSize, gp.tileSize));
        up.add(tool.loadImage("/goalkeeper/goalkeeperback2", gp.tileSize, gp.tileSize));
        up.add(tool.loadImage("/goalkeeper/goalkeeperback3", gp.tileSize, gp.tileSize));
        
        // Load images for "down" direction
        down.add(tool.loadImage("/goalkeeper/goalkeeperfront1", gp.tileSize, gp.tileSize));
        down.add(tool.loadImage("/goalkeeper/goalkeeperfront2", gp.tileSize, gp.tileSize));
        down.add(tool.loadImage("/goalkeeper/goalkeeperfront3", gp.tileSize, gp.tileSize));
        
        // Load images for "left" direction
        left.add(tool.loadImage("/goalkeeper/goalkeeperleft1", gp.tileSize, gp.tileSize));
        left.add(tool.loadImage("/goalkeeper/goalkeeperleft2", gp.tileSize, gp.tileSize));
        left.add(tool.loadImage("/goalkeeper/goalkeeperleft3", gp.tileSize, gp.tileSize));
        
        // Load images for "right" direction
        right.add(tool.loadImage("/goalkeeper/goalkeeperright1", gp.tileSize, gp.tileSize));
        right.add(tool.loadImage("/goalkeeper/goalkeeperright2", gp.tileSize, gp.tileSize));
        right.add(tool.loadImage("/goalkeeper/goalkeeperright3", gp.tileSize, gp.tileSize));
        
    }
    
    // Set action method 
    public void setAction() {
        if (actionLockCounter == 0) {
            Random random = new Random();
            if (random.nextBoolean()) {
                direction = "up";
            } else {
                direction = "down";
            }
            
            // Choose a random value between 10 and 30 (exclusive) 
            actionLockCounter = random.nextInt(20) + 10;
            // actionLockCounter sets how long the goalkeeper will move in that direction
        } else {
            actionLockCounter--; 
        }
    }
    
    // Check if the goalkeeper collides with the ball
    public boolean checkGoalkeeperBall(Entity entity) {
                
        // Entity's solid area position
        this.solidArea.x = this.worldX;
        this.solidArea.y = this.worldY;

        // Object's solid area position
        entity.solidArea.x = entity.worldX;
        entity.solidArea.y = entity.worldY;
            
        if(this.solidArea.intersects(entity.solidArea)) {
            if(entity.collision == true) {
                ((Ball)entity).init();
                return true;
            }
        }
            
        this.solidArea.x = this.solidAreaDefaultX;
        this.solidArea.y = this.solidAreaDefaultY;
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        return false;
    }
    
    // Goakeeper update method
    @Override
    public void update() {
        super.update();
        checkGoalkeeperBall(gp.obj[0]);
    }

    // Goalkeeper draw method
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = up.get(spriteNum - 1);
                break;
            case "down":
                image = down.get(spriteNum - 1);
                break;
            case "left":
                image = left.get(spriteNum - 1);
                break;
            case "right":
                image = right.get(spriteNum - 1);
                break;
        }
        
        drawImage = image;        
        super.draw(g2);
    }
}
