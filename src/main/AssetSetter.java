package main;

import java.awt.event.KeyEvent;

import entity.Ball;
import entity.Entity;
import entity.Goalkeeper;

public class AssetSetter {
	
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void init() {
		int tileSize = gp.tileSize;
		
		gp.player1.init(40 * tileSize, 33 * tileSize);
		gp.player2.init(50 * tileSize, 33 * tileSize);
		
		// Set key bindings for player actions
		gp.player1.setKey(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_SPACE);
		gp.player2.setKey(KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_H, KeyEvent.VK_SHIFT);
		
		// Set images
		gp.player1.getPlayer1Image("/player/boy");
		gp.player1.getPlayer1TackleImage("/player/playertackle");
		gp.player2.getPlayer2Image("/player2/P2_");
		gp.player2.getPlayer2TackleImage("/player2/P2_tackle");
		
		// Reset
		gp.escPressed = false;
		gp.spacePressed = false;
		
		gp.obj[0] = new Ball(gp);
		gp.obj[0].worldX = (int) (49.6 * gp.tileSize);
		gp.obj[0].worldY = 50 * gp.tileSize;
		
		gp.obj[1] = new Entity(gp);
		gp.obj[1].name = "Water";
		gp.obj[1].down1 = UtilityTool.getInstance().loadImage("/objects/water", gp.tileSize, gp.tileSize);
		gp.obj[1].setImage();
		gp.obj[1].collision = true;
		gp.obj[1].worldX = 55 * gp.tileSize;
		gp.obj[1].worldY = 37 * gp.tileSize;
//		System.out.println(gp.obj[1].direction + " " + gp.obj[1].spriteNum);
		
		gp.obj[2] = new Entity(gp);
		gp.obj[2].name = "Shoes";
		gp.obj[2].down1 = UtilityTool.getInstance().loadImage("/objects/shoes", gp.tileSize, gp.tileSize);
		gp.obj[2].setImage();
		gp.obj[2].collision = true;
		gp.obj[2].worldX = 56 * gp.tileSize;
		gp.obj[2].worldY = 36 * gp.tileSize;
		
		gp.goalkeeper[0] = new Goalkeeper(gp); //13=column, 8=row
		gp.goalkeeper[0].worldX = gp.tileSize*68;
		gp.goalkeeper[0].worldY = gp.tileSize*50;
		
		gp.goalkeeper[1] = new Goalkeeper(gp);
		gp.goalkeeper[1].worldX = gp.tileSize*32;
		gp.goalkeeper[1].worldY = gp.tileSize*50;
		
	}
}
