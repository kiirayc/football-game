package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.UtilityTool;

// Ball class extends Entity class
public class Ball extends Entity {
    // Reference to the user entity
    Entity user;
    
    // Time-related variables
    long presentTime, a;
    long updateTime = 1;
    
    // Last known position of the ball
    public int lastX;
    public int lastY;
    
    // Tile number and goal region boundaries
    public int tileNumber = -1;
    public int myGoalStartCol, myGoalEndCol, myGoalStartRow, myGoalEndRow;
    public int enemyGoalStartCol, enemyGoalEndCol, enemyGoalStartRow, enemyGoalEndRow;

    // Fields for different ball directions (images for animation)
    private BufferedImage base, up1, up2, down1, down2, left1, left2, right1, right2;

    // Constructor for the Ball class
    public Ball(GamePanel gp) {
        // Call the constructor of the superclass (Entity)
        super(gp);
        
        // Set the reference to the GamePanel
        this.gp = gp;
        
        // Initialize the name and speed of the ball
        name = "Ball";
        initSpeed();
        
        // Load the base image for the ball
        base = UtilityTool.getInstance().loadImage("/objects/ball", gp.tileSize, gp.tileSize);
        direction = "";
        collision = true;

        // Initialize fields related to goal regions
        myGoalStartCol = 29;
        myGoalEndCol = 30;
        myGoalStartRow = 49;
        myGoalEndRow = 52;
        
        enemyGoalStartCol = 70;
        enemyGoalEndCol = 71;
        enemyGoalStartRow = 49;
        enemyGoalEndRow = 52;

        // Initialize images for different directions
        getImage();
    }

    // Set the initial position and direction of the ball
    public void set(int worldX, int worldY, String direction, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.user = user;

        // Initialize the last position
        this.lastX = worldX;
        this.lastY = worldY;
    }

    // Initialize the ball's position and speed
    public void init() {
        System.out.println("Ball returned");
        this.worldX = (int) (49.6 * gp.tileSize);
        this.worldY = 50 * gp.tileSize;
        this.lastX = this.worldX;
        this.lastY = this.worldY;
        speed = 0;
    }

    // Update the ball's position and handle collisions
    public void update() {
        if (user != null) {
            // Update the ball's position based on its direction and speed
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }

            // Check for collisions with tiles and other entities
            gp.cChecker.checkTile(this);
            gp.cChecker.checkBallCollision(this);

            // Decrease the speed and handle sprite animation
            if (speed > 0) {
                speed--;
                spriteCounter++;
                if (spriteCounter > 12) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            } else {
                speed = 0;
            }
        }

        // Update the last known position of the ball
        lastX = worldX;
        lastY = worldY;
    }

    // Initialize the ball's speed
    public void initSpeed() {
        speed = 30;
    }

    // Draw the ball on the graphics context
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image;

        // Choose the image based on the ball's direction
        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
            default:
                image = base;
                break;
        }

        // Set the image to be drawn, call the superclass draw 
        drawImage = image;
        super.draw(g2);
    }

    // Load images depending on the ball's direction
    public void getImage() {
        UtilityTool tool = UtilityTool.getInstance();

        up1 = tool.loadImage("/objects/ball2", gp.tileSize, gp.tileSize);
        up2 = tool.loadImage("/objects/ball3", gp.tileSize, gp.tileSize);
        down1 = tool.loadImage("/objects/ball2", gp.tileSize, gp.tileSize);
        down2 = tool.loadImage("/objects/ball3", gp.tileSize, gp.tileSize);
        left1 = tool.loadImage("/objects/ball2", gp.tileSize, gp.tileSize);
        left2 = tool.loadImage("/objects/ball3", gp.tileSize, gp.tileSize);
        right1 = tool.loadImage("/objects/ball2", gp.tileSize, gp.tileSize);
        right2 = tool.loadImage("/objects/ball3", gp.tileSize, gp.tileSize);
    }
}
