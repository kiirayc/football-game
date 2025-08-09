package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import main.UtilityTool;

public class Player extends Entity implements KeyListener { 

	Entity enemy;
    public Ball ball;
    public boolean BallDribble = false;  // Indicates if the player is currently dribbling the ball
    public int ballIndex;  // Index of the ball in the game panel's object array
    public boolean gotItems = false; // Indicates if the player has collected items
    public int life;
    public final int maxLife = 10;  // Maximum life points for the player
    private int upKey, downKey, leftKey, rightKey, shootKey, tackleKey;
    private boolean pressUp, pressDown, pressLeft, pressRight, pressShoot, pressTackle;
    public boolean isMove = false;  // Indicates if the player is currently moving

	public Player(GamePanel gp) {
	    super(gp);
	    life = maxLife;
	    solidArea = new Rectangle();
	    solidArea.x = 16;
	    solidArea.y = 16;
	    solidAreaDefaultX = solidArea.x;
	    solidAreaDefaultY = solidArea.y;
	    solidArea.width = 36;
	    solidArea.height = 36;

	    tackleArea.width = 36;
	    tackleArea.height = 36;
	    setDefaultValues();
	}
	
	// Set the enemy for the player
	public void setEnemy(Entity enemy) {
		this.enemy = enemy;
	}
	
	// Set the initial position of the player
	public void init(int initialX, int initialY) {
	    worldX = initialX;
	    worldY = initialY;
	}

	// Added parameters for receiving input keys
	public void setKey(int upKey, int downKey, int leftKey, int rightKey, int shootKey, int tackleKey) {
		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.shootKey = shootKey;
		this.tackleKey = tackleKey;
	}
	
	// Set default values for the player
	public void setDefaultValues() {
		worldX = gp.tileSize * 58;
		worldY = gp.tileSize * 58;
		speed = 3;
		direction = "down";
		ball = null;
	}
	
	// Load player images for Player 1
	public void getPlayer1Image(String player1ImagePath) {
		UtilityTool tool = UtilityTool.getInstance();
		
		up1 = tool.loadImage(player1ImagePath + "back1", gp.tileSize, gp.tileSize);
        up2 = tool.loadImage(player1ImagePath + "back2", gp.tileSize, gp.tileSize);
        up3 = tool.loadImage(player1ImagePath + "back3", gp.tileSize, gp.tileSize);
		left1 = tool.loadImage(player1ImagePath + "left1", gp.tileSize, gp.tileSize);
        left2 = tool.loadImage(player1ImagePath + "left2", gp.tileSize, gp.tileSize);
        left3 = tool.loadImage(player1ImagePath + "left3", gp.tileSize, gp.tileSize);
        right1 = tool.loadImage(player1ImagePath + "right1", gp.tileSize, gp.tileSize);
		right2 = tool.loadImage(player1ImagePath + "right2", gp.tileSize, gp.tileSize);
		right3 = tool.loadImage(player1ImagePath + "right3", gp.tileSize, gp.tileSize);
		down1 = tool.loadImage(player1ImagePath + "front1", gp.tileSize, gp.tileSize);
		down2 = tool.loadImage(player1ImagePath + "front2", gp.tileSize, gp.tileSize);
		down3 = tool.loadImage(player1ImagePath + "front3", gp.tileSize, gp.tileSize);

		upball1 = tool.loadImage(player1ImagePath + "backball1", gp.tileSize, gp.tileSize);
		upball2 = tool.loadImage(player1ImagePath + "backball2", gp.tileSize, gp.tileSize);
		upball3 = tool.loadImage(player1ImagePath + "backball3", gp.tileSize, gp.tileSize);
		leftball1 = tool.loadImage(player1ImagePath + "leftball1", gp.tileSize, gp.tileSize);
		leftball2 = tool.loadImage(player1ImagePath + "leftball2", gp.tileSize, gp.tileSize);
		leftball3 = tool.loadImage(player1ImagePath + "leftball3", gp.tileSize, gp.tileSize);
		rightball1 = tool.loadImage(player1ImagePath + "rightball1", gp.tileSize, gp.tileSize);
		rightball2 = tool.loadImage(player1ImagePath + "rightball2", gp.tileSize, gp.tileSize);
		rightball3 = tool.loadImage(player1ImagePath + "rightball3", gp.tileSize, gp.tileSize);
		downball1 = tool.loadImage(player1ImagePath + "frontball1", gp.tileSize, gp.tileSize);
		downball2 = tool.loadImage(player1ImagePath + "frontball2", gp.tileSize, gp.tileSize);
		downball3 = tool.loadImage(player1ImagePath + "frontball3", gp.tileSize, gp.tileSize);

	}
	
	// Load player images for Player 2
	public void getPlayer2Image (String player2ImagePath) {
		UtilityTool tool = UtilityTool.getInstance();
		
		P2_up1 = tool.loadImage(player2ImagePath + "back1", gp.tileSize, gp.tileSize);
		P2_up2 = tool.loadImage(player2ImagePath + "back2", gp.tileSize, gp.tileSize);
		P2_up3 = tool.loadImage(player2ImagePath + "back3", gp.tileSize, gp.tileSize);
		P2_left1 = tool.loadImage(player2ImagePath + "left1", gp.tileSize, gp.tileSize);
		P2_left2 = tool.loadImage(player2ImagePath + "left2", gp.tileSize, gp.tileSize);
		P2_left3 = tool.loadImage(player2ImagePath + "left3", gp.tileSize, gp.tileSize);
		P2_right1 = tool.loadImage(player2ImagePath + "right1", gp.tileSize, gp.tileSize);
		P2_right2 = tool.loadImage(player2ImagePath + "right2", gp.tileSize, gp.tileSize);
		P2_right3 = tool.loadImage(player2ImagePath + "right3", gp.tileSize, gp.tileSize);
		P2_down1 = tool.loadImage(player2ImagePath + "front1", gp.tileSize, gp.tileSize);
		P2_down2 = tool.loadImage(player2ImagePath + "front2", gp.tileSize, gp.tileSize);
		P2_down3 = tool.loadImage(player2ImagePath + "front3", gp.tileSize, gp.tileSize);

		// Load ball images for Player 2
		P2_upball1 = tool.loadImage(player2ImagePath + "backball1", gp.tileSize, gp.tileSize);
		P2_upball2 = tool.loadImage(player2ImagePath + "backball2", gp.tileSize, gp.tileSize);
		P2_upball3 = tool.loadImage(player2ImagePath + "backball3", gp.tileSize, gp.tileSize);
		P2_leftball1 = tool.loadImage(player2ImagePath + "leftball1", gp.tileSize, gp.tileSize);
		P2_leftball2 = tool.loadImage(player2ImagePath + "leftball2", gp.tileSize, gp.tileSize);
		P2_leftball3 = tool.loadImage(player2ImagePath + "leftball3", gp.tileSize, gp.tileSize);
		P2_rightball1 = tool.loadImage(player2ImagePath + "rightball1", gp.tileSize, gp.tileSize);
		P2_rightball2 = tool.loadImage(player2ImagePath + "rightball2", gp.tileSize, gp.tileSize);
		P2_rightball3 = tool.loadImage(player2ImagePath + "rightball3", gp.tileSize, gp.tileSize);
		P2_downball1 = tool.loadImage(player2ImagePath + "frontball1", gp.tileSize, gp.tileSize);
		P2_downball2 = tool.loadImage(player2ImagePath + "frontball2", gp.tileSize, gp.tileSize);
		P2_downball3 = tool.loadImage(player2ImagePath + "frontball3", gp.tileSize, gp.tileSize);
		
	}
	
	// Load tackle images for Player 1
	public void getPlayer1TackleImage(String player1ImagePath) {
		UtilityTool tool = UtilityTool.getInstance();
	
		tackleUp1 = tool.loadImage(player1ImagePath + "up1", gp.tileSize, gp.tileSize * 2);
		tackleUp2 = tool.loadImage(player1ImagePath + "up2", gp.tileSize, gp.tileSize * 2);
		tackleDown1 = tool.loadImage(player1ImagePath + "down1", gp.tileSize, gp.tileSize * 2);
		tackleDown2 = tool.loadImage(player1ImagePath + "down2", gp.tileSize, gp.tileSize * 2);
		tackleLeft1 = tool.loadImage(player1ImagePath + "left1", gp.tileSize * 2, gp.tileSize);
		tackleLeft2 = tool.loadImage(player1ImagePath + "left2", gp.tileSize * 2, gp.tileSize);
		tackleRight1 = tool.loadImage(player1ImagePath + "right1", gp.tileSize * 2, gp.tileSize);
		tackleRight2 = tool.loadImage(player1ImagePath + "right2", gp.tileSize * 2, gp.tileSize);

	}
	
	// Load tackle images for Player 2
	public void getPlayer2TackleImage(String player2ImagePath) {
		UtilityTool tool = UtilityTool.getInstance();
		
		P2_tackleUp1 = tool.loadImage(player2ImagePath + "up1", gp.tileSize, gp.tileSize * 2);
		P2_tackleUp2 = tool.loadImage(player2ImagePath + "up2", gp.tileSize, gp.tileSize * 2);
		P2_tackleDown1 = tool.loadImage(player2ImagePath + "down1", gp.tileSize, gp.tileSize * 2);
		P2_tackleDown2 = tool.loadImage(player2ImagePath + "down2", gp.tileSize, gp.tileSize * 2);
		P2_tackleLeft1 = tool.loadImage(player2ImagePath + "left1", gp.tileSize * 2, gp.tileSize);
		P2_tackleLeft2 = tool.loadImage(player2ImagePath + "left2", gp.tileSize * 2, gp.tileSize);
		P2_tackleRight1 = tool.loadImage(player2ImagePath + "right1", gp.tileSize * 2, gp.tileSize);
		P2_tackleRight2 = tool.loadImage(player2ImagePath + "right2", gp.tileSize * 2, gp.tileSize);

	}
	
	// Update player state and handle movement
	public void update() {
        tackleStart();
        collisionOn = gp.cChecker.checkPlayerCollision(gp.player1, gp.player2);
        
	    if (tackling == true) {
	        tackling();
	    } else if (pressUp == true || pressDown == true || pressLeft == true || pressRight == true) {
	        if (pressUp == true) {
	            direction = "up";
	        } else if (pressDown == true) {
	            direction = "down";
	        } else if (pressLeft == true) {
	            direction = "left";
	        } else if (pressRight == true) {
	            direction = "right";
	        } else if (pressTackle == true) {
	            tackling = true;
	        }

	        
	        if (!collisionOn) {
	            collisionOn = false;
	            
	            gp.cChecker.checkTile(this);

	            int objIndex = gp.cChecker.checkObject(this, true);
	            pickUpObject(objIndex);
	            int goalkeeperIndex = gp.cChecker.checkEntity(this, gp.goalkeeper);
	            
	            if (collisionOn == false) {
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
	                
	                if (ball != null) {
	                	ball.set(worldX, worldY, direction, null);
	                }
	            }
	        }

	        collisionOn = false;
	        gp.cChecker.checkTile(this);
	        isMove = true;

	        spriteCounter++;
	        if (spriteCounter > 4) {
	            if (spriteNum == 1) {
	                spriteNum = 2;
	            } else if (spriteNum == 2) {
	                spriteNum = 3;
	            } else if (spriteNum == 3) {
	                spriteNum = 1;
	            }
	            spriteCounter = 0;
	            // sprite counter increases only when we're pressing keys
	        }
	    } else {
	        standCounter++;
	        if (standCounter == 1) {
	            spriteNum = 2;
	            standCounter = 0;
	        }
	    }

	    if (BallDribble == true) {
	        if (pressShoot == true) {
	            // set default coordinates, direction and user
	        	int movedX = worldX;
	        	int movedY = worldY;
	        	
	        	switch (direction) {
                case "up":
                    movedY -= gp.tileSize;
                    break;
                case "down":
                    movedY += gp.tileSize;
                    break;
                case "left":
                    movedX -= gp.tileSize;
                    break;
                case "right":
                    movedX += gp.tileSize;
                    break;
            }
	        	ball.initSpeed();
	        	ball.set(movedX, movedY, direction, this); // Set the ball's direction and user
	        	ball = null;
	            gp.playSoundEffect(3);
	            BallDribble = false;
	        }
	    }
	}
	
	// Handle the tackling animation
	public void tackling() {

		spriteCounter++;

		if (spriteCounter <= 5) {spriteNum = 1;}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;

			// save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// adjust player's worldX/Y for the tackle area
			switch (direction) {
			case "up":
				worldY -= tackleArea.height;
				break;
			case "down":
				worldY += tackleArea.height;
				break;
			case "left":
				worldX -= tackleArea.width;
				break;
			case "right":
				worldX += tackleArea.width;
				break;
			}

			// tackle area becomes solid area
			solidArea.width = tackleArea.width;
			solidArea.height = tackleArea.height;

			// after checking collision, restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;

		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			tackling = false;
		}
	}
	
	// Pick up an object based on the index
	public void pickUpObject(int i) {

		if (i != 999) {
			String objectName = gp.obj[i].name;

			switch (objectName) {
			case "Water":
				gp.playSoundEffect(2);
				gp.obj[i] = null;
				if (this == gp.player1) {
	                gp.player1.life = maxLife;
	            } else if (this == gp.player2) {
	            	gp.player2.life = maxLife;
	            }				
				gp.ui.showMessage("You drank water! Now your stamina goes up.");
				break;
			case "Ball":
				if (!BallDribble) {
					if (this == gp.player1) {
						if (gp.player1.life > 0) {
							gp.player1.life -= 1;
//							System.out.println(gp.player1.life);
							dribble(i);
							gp.ui.showMessage("You got a ball! Your stamina decreases.");
						} else {
							gp.ui.showMessage("You have no stamina left to dribble...");
						}
		            } else if (this == gp.player2) {
		            	if (gp.player2.life > 0) {
							gp.player2.life -= 1;
//							System.out.println(gp.player2.life);
							dribble(i);
							gp.ui.showMessage("You got a ball! Your stamina decreases.");
						} else {
							gp.ui.showMessage("You have no stamina left to dribble...");
						}
		            }	
				}
				break;
			case "Shoes":
				gp.playSoundEffect(1);
				gp.obj[i] = null;
				speed += 1;
				gp.ui.showMessage("You got a pair of soccer shoes! You can run faster.");
				gotItems = true;
			}
		}
	}
	
	// Dribble the ball
	public void dribble(int i) {
		ball = (Ball)gp.obj[i];
		ballIndex = i;
		BallDribble = true;
	}
	
	// Lose possession of the ball
	public void loseBall() {
		ball = null;
		BallDribble = false;
	}
	
	// Start the tackling animation
	public void tackleStart() {
		if (pressTackle == true && !BallDribble) { // Check if BallDribble is false
	        tackling = true;
	    }	
	}
	
	// Set the player image for Player 1
	public void setPlayer1Image() {
		if(tackling) {
			switch (direction) {
            case "up":
                drawImage = spriteNum == 1 ? tackleUp1 : tackleUp2;
                break;
            case "down":
                drawImage = spriteNum == 1 ? tackleDown1 : tackleDown2;
                break;
            case "left":
                drawImage = spriteNum == 1 ? tackleLeft1 : tackleLeft2;
                break;
            case "right":
                drawImage = spriteNum == 1 ? tackleRight1 : tackleRight2;
                break;
			}
		}
		
		else {if (BallDribble) {
	        switch (direction) {
	            case "up":
	                drawImage = spriteNum == 1 ? upball1 : spriteNum == 2 ? upball2 : upball3;
	                break;
	            case "down":
	                drawImage = spriteNum == 1 ? downball1 : spriteNum == 2 ? downball2 : downball3;
	                break;
	            case "left":
	                drawImage = spriteNum == 1 ? leftball1 : spriteNum == 2 ? leftball2 : leftball3;
	                break;
	            case "right":
	                drawImage = spriteNum == 1 ? rightball1 : spriteNum == 2 ? rightball2 : rightball3;
	                break;
	        }
	    } else {
	        switch (direction) {
	            case "up":
	                drawImage = spriteNum == 1 ? up1 : spriteNum == 2 ? up2 : up3;
	                break;
	            case "down":
	                drawImage = spriteNum == 1 ? down1 : spriteNum == 2 ? down2 : down3;
	                break;
	            case "left":
	                drawImage = spriteNum == 1 ? left1 : spriteNum == 2 ? left2 : left3;
	                break;
	            case "right":
	                drawImage = spriteNum == 1 ? right1 : spriteNum == 2 ? right2 : right3;
	                break;
	        }
	    }}
	}
	
	// Set the player image for Player 2
	public void setPlayer2Image() {
		if (tackling) {
			switch (direction) {
	            case "up":
	                drawImage = spriteNum == 1 ? P2_tackleUp1 : P2_tackleUp2;
	                break;
	            case "down":
	                drawImage = spriteNum == 1 ? P2_tackleDown1 : P2_tackleDown2;
	                break;
	            case "left":
	                drawImage = spriteNum == 1 ? P2_tackleLeft1 : P2_tackleLeft2;
	                break;
	            case "right":
	                drawImage = spriteNum == 1 ? P2_tackleRight1 : P2_tackleRight2;
	                break;
	        }
		}
		else {if (BallDribble) {
	        switch (direction) {
	            case "up":
	                drawImage = spriteNum == 1 ? P2_upball1 : spriteNum == 2 ? P2_upball2 : P2_upball3;
	                break;
	            case "down":
	                drawImage = spriteNum == 1 ? P2_downball1 : spriteNum == 2 ? P2_downball2 : P2_downball3;
	                break;
	            case "left":
	                drawImage = spriteNum == 1 ? P2_leftball1 : spriteNum == 2 ? P2_leftball2 : P2_leftball3;
	                break;
	            case "right":
	                drawImage = spriteNum == 1 ? P2_rightball1 : spriteNum == 2 ? P2_rightball2 : P2_rightball3;
	                break;
	        }
	    } else {
	        switch (direction) {
	            case "up":
	                drawImage = spriteNum == 1 ? P2_up1 : spriteNum == 2 ? P2_up2 : P2_up3;
	                break;
	            case "down":
	                drawImage = spriteNum == 1 ? P2_down1 : spriteNum == 2 ? P2_down2 : P2_down3;
	                break;
	            case "left":
	                drawImage = spriteNum == 1 ? P2_left1 : spriteNum == 2 ? P2_left2 : P2_left3;
	                break;
	            case "right":
	                drawImage = spriteNum == 1 ? P2_right1 : spriteNum == 2 ? P2_right2 : P2_right3;
	                break;
	        }
	    }}
	}
	
	 // Draw the player on the screen
	@Override
	public void draw(Graphics2D g2) {
		if (tackling) {
			switch (direction) {
	            case "up":
	            	width = gp.tileSize;
	                height = gp.tileSize * 2;
	                break;
	            case "down":
	            	width = gp.tileSize;
	                height = gp.tileSize * 2;
	                break;
	            case "left":
	                width = gp.tileSize * 2;
	                height = gp.tileSize;
	                break;
	            case "right":
	            	width = gp.tileSize * 2;
	                height = gp.tileSize;
	                break;
	        }
		} else {
			width = height = gp.tileSize;
		}
		super.draw(g2);
    }
	
	@Override
	public void keyTyped(KeyEvent e) {
		// don't use this
	}
	
	// Handle key presses
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == this.upKey) {pressUp = true;}
		if (code == this.downKey) {pressDown = true;}
		if (code == this.leftKey) {pressLeft = true;}
		if (code == this.rightKey) {pressRight = true;}
		if (code == this.shootKey) {pressShoot = true;}
		if (code == this.tackleKey) {pressTackle = true;}
	}
	
	// Handle key releases
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == this.upKey) {pressUp = false;}
		if (code == this.downKey) {pressDown= false;}
		if (code == this.leftKey) {pressLeft = false;}
		if (code == this.rightKey) {pressRight = false;}
		if (code == this.shootKey) {pressShoot = false;}
		if (code == this.tackleKey) {pressTackle= false;}
	}
}
