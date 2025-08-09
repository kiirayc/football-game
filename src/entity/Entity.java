package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    
	// Reference to the GamePanel
	GamePanel gp;
    
    // Images for player 1 (P1) and player 2 (P2) in different directions
    // Also, images for ball and tackle animations
    public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3;
    public BufferedImage P2_up1, P2_up2, P2_up3, P2_left1, P2_left2, P2_left3, P2_right1, P2_right2, P2_right3, P2_down1, P2_down2, P2_down3;
    public BufferedImage upball1, upball2, upball3, leftball1, leftball2, leftball3, rightball1, rightball2, rightball3, downball1,downball2, downball3;
    public BufferedImage P2_upball1, P2_upball2, P2_upball3, P2_leftball1, P2_leftball2, P2_leftball3, P2_rightball1, P2_rightball2, P2_rightball3, P2_downball1,P2_downball2, P2_downball3;
    public BufferedImage tackleUp1, tackleUp2, tackleDown1, tackleDown2, tackleLeft1, tackleLeft2, tackleRight1, tackleRight2;
    public BufferedImage P2_tackleUp1, P2_tackleUp2, P2_tackleDown1, P2_tackleDown2, P2_tackleLeft1, P2_tackleLeft2, P2_tackleRight1, P2_tackleRight2;
    public BufferedImage image, image2, image3;
    
    // Rectangles representing tackle and solid areas
    public Rectangle tackleArea = new Rectangle(0, 0, 0, 0);
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    
	public boolean collision = false;
	public boolean cameraWorking;	
	protected BufferedImage drawImage;
	
	public int worldX,worldY;
	public int width, height;
	public String direction = "down";
	public int spriteNum = 1;
	public boolean collisionOn = false;
	public boolean tackling = false;
	
	// Counters for animations and actions
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int standCounter = 0;
	
	//character attributes
	public int type; 
	public String name;
	public int speed;
    
    //item attributes
    public String description = "";
    
    // Constructor for the Entity class
    public Entity(GamePanel gp) {
    	this.gp = gp;
    	width = height = this.gp.tileSize;
    }
    
    public void setAction() {
    	
    }
    
    // Update method to handle entity logic and collisions
    public void update() {
    	
    	setAction();
    	
    	collisionOn = false;
    	gp.cChecker.checkTile(this);
    	gp.cChecker.checkObject(this, false);
    	gp.cChecker.checkEntity(this, gp.goalkeeper);
    	boolean contactPlayer = gp.cChecker.checkPlayer(this); //player 1,2 
    	
    	if(this.type == 2 && contactPlayer == true) {
    		
    	}
    	
    	// Sprite counter increases only when we're pressing keys
    	spriteCounter++;
    	if (spriteCounter > 4) {
    	    spriteNum = (spriteNum % 3) + 1; // Cycle spriteNum between 1, 2, and 3
    	    spriteCounter = 0;
    	}
                
		  //if collision is false, player can move
		  if(collisionOn == false) {
			  switch(direction) {
			  case "up": worldY -= speed; break;
			  case "down": worldY += speed; break;
			  case "left": worldX -= speed; break;
			  case "right": worldX += speed; break;
			  }
		  }
		  
		  collisionOn = false;
		  gp.cChecker.checkTile(this);	    
    }
    
    public void setImage() {
		drawImage = down1;
	}
    
    // Draw entity on the screen
    public void draw(Graphics2D g2) {
    	// Find the position of entity on the screen relative to the camera
    	int screenX = worldX - gp.cameraX + gp.screenX;
		int screenY = worldY - gp.cameraY + gp.screenY;
		// Check if the entity is within the visible area 
		if (screenX + width > 0 && screenX < gp.screenWidth && screenY + height > 0 && screenY < gp.screenHeight) {
		    g2.drawImage(drawImage, screenX, screenY, width, height, null);
		}
    }
    
}

