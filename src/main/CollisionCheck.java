package main;

import entity.Ball;
import entity.Entity;
import entity.Player;

//CollisionCheck class for handling collision detection
public class CollisionCheck {
	
	GamePanel gp;
	// Constructor initializing the GamePanel reference
	public CollisionCheck(GamePanel gp) {
		this.gp = gp;
	}
	
	// Method for checking tile collision
	public void checkTile(Entity entity) {
		
		int entityCenterWorldX = entity.worldX + gp.tileSize / 2;
		int entityCenterWorldY = entity.worldY + gp.tileSize / 2;
		
		switch(entity.direction) {
		case "up":
			entityCenterWorldY -= entity.speed;
			break;
		case "down":
			entityCenterWorldY += entity.speed;
			break;
		case "left":
			entityCenterWorldX -= entity.speed;
			break;
		case "right":
			entityCenterWorldX += entity.speed;
			break;
		}
		
		int entityLeftCol = (entityCenterWorldX - entity.solidArea.width / 2) / gp.tileSize;
		int entityRightCol = (entityCenterWorldX + entity.solidArea.width / 2) / gp.tileSize;
		int entityTopRow = (entityCenterWorldY - entity.solidArea.height / 2) / gp.tileSize;
		int entityBottomRow = (entityCenterWorldY + entity.solidArea.height / 2) / gp.tileSize;
		
		int entityCenterCol = entityCenterWorldX / gp.tileSize;
		int entityCenterRow = entityCenterWorldY / gp.tileSize;
		
		int tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; 
		int tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; 
		int tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
		int tileNum4 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; 
		int tileNum5 = gp.tileM.mapTileNum[entityCenterCol][entityCenterRow]; 
//		System.out.println(tileNum1 + " " + tileNum2 + " " + tileNum3 + " " + tileNum4);
		
		if(entity instanceof Ball) {
			
			((Ball) entity).tileNumber = tileNum5; //tileNumber not in Entity, if Ball class = call the method (casting) 
		}
		
		entity.collisionOn = entity.collisionOn ||
							gp.tileM.tile[tileNum1].collision ||
							gp.tileM.tile[tileNum2].collision ||
							gp.tileM.tile[tileNum3].collision ||
							gp.tileM.tile[tileNum4].collision ||
							gp.tileM.tile[tileNum5].collision;
				
	}
	
	// Method for checking ball collision
	public void checkBallCollision(Ball ball) {
	    
		// Calculate the center of the ball
		int ballCenterWorldX = ball.worldX + gp.tileSize / 2;
		int ballCenterWorldY = ball.worldY + gp.tileSize / 2;
		
	    // Update the center coordinates based on the ball's direction and speed
		switch(ball.direction) {
		case "up":
			ballCenterWorldY -= ball.speed;
			break;
		case "down":
			ballCenterWorldY += ball.speed;
			break;
		case "left":
			ballCenterWorldX -= ball.speed;
			break;
		case "right":
			ballCenterWorldX += ball.speed;
			break;
		}
		
	    // Calculate the column and row of tiles that the ball's solid area intersects with
		int entityLeftCol = (ballCenterWorldX - ball.solidArea.width / 2) / gp.tileSize;
		int entityRightCol = (ballCenterWorldX + ball.solidArea.width / 2) / gp.tileSize;
		int entityTopRow = (ballCenterWorldY - ball.solidArea.height / 2) / gp.tileSize;
		int entityBottomRow = (ballCenterWorldY + ball.solidArea.height / 2) / gp.tileSize;
		
		int entityCenterCol = ballCenterWorldX / gp.tileSize;
		int entityCenterRow = ballCenterWorldY / gp.tileSize;
		
	    // Iterate over tiles that the ball's solid area intersects with
		for (int[] tileIndices : new int[][]{{entityLeftCol, entityTopRow}, {entityRightCol, entityTopRow},
            	{entityLeftCol, entityBottomRow}, {entityRightCol, entityBottomRow}, {entityCenterCol, entityCenterRow}}) {
	        // Get the tile number for the current tile indices
			int tileNum = gp.tileM.mapTileNum[tileIndices[0]][tileIndices[1]];
			
	        // Check if the current tile contains the player's goal
	        if (containsTile(tileIndices, ball.myGoalStartCol, ball.myGoalEndCol, ball.myGoalStartRow, ball.myGoalEndRow)) {
	            ball.init();
	            gp.playSoundEffect(4);
	            gp.redIncrease();
	            
	            return; 
	        }
		}

	    for (int[] tileIndices : new int[][]{{entityLeftCol, entityTopRow}, {entityRightCol, entityTopRow},
	            {entityLeftCol, entityBottomRow}, {entityRightCol, entityBottomRow}, {entityCenterCol, entityCenterRow}}) {
	        int tileNum = gp.tileM.mapTileNum[tileIndices[0]][tileIndices[1]];
//	        System.out.println("Checking tile collision for tileNum: " + tileNum);
	        if (containsTile(tileIndices, ball.enemyGoalStartCol, ball.enemyGoalEndCol, ball.enemyGoalStartRow, ball.enemyGoalEndRow)) {
	            ball.init();
	            gp.playSoundEffect(4);
	            gp.blueIncrease();
	            return; 
	        }
	    }
	}
	
	// Helper method to check if the tile is in the given range
	private boolean containsTile(int[] tileIndices, int startCol, int endCol, int startRow, int endRow) {
	    boolean result = tileIndices[0] >= startCol && tileIndices[0] <= endCol && tileIndices[1] >= startRow && tileIndices[1] <= endRow;
//	    System.out.println("Result: " + result);
	    return result;
	}
	
	// Method for checking object collision
	public int checkObject(Entity entity, boolean player) {
		int objIndex = 999;
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] != null && gp.obj[i] != gp.player1.ball && gp.obj[i] != gp.player2.ball) {
				
				//Get entity's solid area position
				entity.solidArea.x = entity.worldX;
				entity.solidArea.y = entity.worldY;

				//Get the object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX;
				gp.obj[i].solidArea.y = gp.obj[i].worldY;
				
				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break; 
				}
				
				if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if(gp.obj[i].collision == true) {
						entity.collisionOn = true;
					}
					if (player == true) {
						objIndex = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
 
			}
		}
		
		return objIndex;
	}
	
	// NPC or player collision
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				
				//Get entity's solid area position
				entity.solidArea.x = entity.worldX;
				entity.solidArea.y = entity.worldY;

				//Get the object's solid area position
				target[i].solidArea.x = target[i].worldX;
				target[i].solidArea.y = target[i].worldY;
				
				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity ) {
						entity.collisionOn = true;
						index = i;		
					}			
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
 
			}
		}
		
		return index;
	}

	// Method for checking player collision
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		//Get entity's solid area position
		entity.solidArea.x = entity.worldX;
		entity.solidArea.y = entity.worldY;

		//Get the object's solid area position
		gp.player1.solidArea.x = gp.player1.worldX;
		gp.player1.solidArea.y = gp.player1.worldY;
		
		switch(entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		
		if(entity.solidArea.intersects(gp.player1.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player1.solidArea.x = gp.player1.solidAreaDefaultX;
		gp.player1.solidArea.y = gp.player1.solidAreaDefaultY;
		
		return contactPlayer;
	}
	
	// Method for checking collision between two entities
	public boolean checkPlayerCollision(Entity entity1, Entity entity2) {
		
		// Get entity1's solid area position
		entity1.solidArea.x = entity1.worldX;
		entity1.solidArea.y = entity1.worldY;
		
		// Get entity2's solid area position
		entity2.solidArea.x = entity2.worldX;
		entity2.solidArea.y = entity2.worldY;
		
		switch (entity1.direction) {
		case "up": 
			entity1.solidArea.y -= entity1.speed;
			break;
		case "down": 
			entity1.solidArea.y += entity1.speed;
			break;
		case "left": 
			entity1.solidArea.x -= entity1.speed;
			break;
		case "right": 
			entity1.solidArea.x += entity1.speed;
			break;
		}
		
		switch (entity2.direction) {
		case "up": 
			entity2.solidArea.y -= entity2.speed;
			break;
		case "down": 
			entity2.solidArea.y += entity2.speed;
			break;
		case "left": 
			entity2.solidArea.x -= entity2.speed;
			break;
		case "right": 
			entity2.solidArea.x += entity2.speed;
			break;
		}
		
		boolean collision = entity1.solidArea.intersects(entity2.solidArea);
		
		if(entity1.tackling == true && ((Player)entity2).BallDribble == true) {
			if (collision) {
				((Player)entity1).dribble(((Player)entity2).ballIndex);
				gp.player1.life = 10;
		    	((Player)entity2).loseBall();
			}
		}
		else if(entity2.tackling == true && ((Player)entity1).BallDribble == true) {
			if(collision) {
				((Player)entity2).dribble(((Player)entity1).ballIndex);
				gp.player2.life = 10;
		    	((Player)entity1).loseBall();
			}
		}
		
		
		entity1.solidArea.x = entity1.solidAreaDefaultX;
		entity1.solidArea.y = entity1.solidAreaDefaultY;
		entity2.solidArea.x = entity2.solidAreaDefaultX;
		entity2.solidArea.y = entity2.solidAreaDefaultY;

		return collision;
    }
	
}
