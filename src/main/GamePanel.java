package main;

// GamePanel Class
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import entity.Ball;
import entity.Entity;
import entity.Player;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	Entity entity;
	// screen settings
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	public final int screenX;
	public final int screenY;
	
	// following coordinates (camera)
	public int cameraX;
	public int cameraY;

	public int tileSize = originalTileSize * scale; 
	public final int maxScreenCol = 27; 
	public final int maxScreenRow = 17; 
	public final int screenWidth = tileSize * maxScreenCol; 
	public final int screenHeight = tileSize * maxScreenRow; 

	// world settings
	public int maxWorldCol;
	public int maxWorldRow;

	// FPS (Frame Per Second)
	public int FPS = 60; // Default frame rate

	// Key control variables
	private final int pauseKey, escKey, saveKey, enterKey, spaceKey;
	public boolean escPressed, spacePressed;

	// debug
	boolean checkDrawTime = false;

	TileManager tileM = new TileManager(this);
	Sound music = new Sound();
	Sound soundEffect = new Sound();
	public CollisionCheck cChecker = new CollisionCheck(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread; 

	// entity and object
	public Player player1 = new Player(this);
	public Player player2 = new Player(this);
	public Ball ball = new Ball(this);
	public boolean collisionOn = false;
	
	public Entity obj[] = new Entity[10]; // 10 spots replace game
	public Entity goalkeeper[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();

	// game state
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;

	// Constructor
	public GamePanel() {
		entity = new Entity(this);
		
 	    // Set the panel size and background color
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
	    // Add key listeners for handling input events
		this.addKeyListener(this); // Add the KeyListener object that implements input handling
		this.addKeyListener(player1); // Add a KeyListener for player 1 input
		this.addKeyListener(player2); // Add a KeyListener for player 2 input
		this.setFocusable(true); // Ensures that GamePanel receives keyboard inputs
		
		// Calculate the center of the screen
		screenX = screenWidth / 2 - (tileSize / 2);
		screenY = screenHeight / 2 - (tileSize / 2);
		
		// Set initial camera position
		cameraX = tileSize * 58; 
		cameraY = tileSize * 58; 

		// Define keys 
		pauseKey = KeyEvent.VK_P;
		escKey = KeyEvent.VK_ESCAPE;
		saveKey = KeyEvent.VK_F1;
		enterKey = KeyEvent.VK_ENTER;
		spaceKey = KeyEvent.VK_SPACE;
		
		// Set player enemies
		player1.setEnemy(player2);
		player2.setEnemy(player1);
	}
	
	// Initialise 
	public void setupGame() {

		aSetter.init();
		playMusic(0);
		gameState = titleState;

	}

	// Start game thread
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// Game loop
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--; // reset
				drawCount++;
			}

			if (timer >= 1000000000) {
//				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0; // maintain 60FPS
			}
		}
	}
	
	// Update game logic 
	public void update() {
		
        double playTime = ui.playTime;
		if (gameState == playState) {

			// player
			player1.update();
			player2.update();
						
			// Objects
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].update();
				}
			}
			
			// Adjust camera position
			cameraX = player1.worldX;
			cameraY = player1.worldY;
			
			if (player1.isMove || player2.isMove) {
				spacePressed = false;
				
				player1.isMove = false;
				player2.isMove = false;
			}
			
			// Update goalkeepers
			for (int i = 0; i < goalkeeper.length; i++) {
				if (goalkeeper[i] != null) {
					goalkeeper[i].update();
				}
			}
									
		}
		if (gameState == pauseState) {
			// nothing
		}
	}

	// Render the game
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// debug
		long drawStart = 0;
		if (checkDrawTime == true) {
			drawStart = System.nanoTime();
		}

		// title screen
		if (gameState == titleState) {
			ui.draw(g2); // draw tiles
		}

		// others
		else {
			// tile
			tileM.draw(g2); // draw tile first then player
			
			// add entities to the list
			player1.setPlayer1Image();
			entityList.add(player1);
			player2.setPlayer2Image();
			entityList.add(player2);

			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null && obj[i] != player1.ball && obj[i] != player2.ball) {
					entityList.add(obj[i]);
				}
			}

			for (int i = 0; i < goalkeeper.length; i++) {
				if (goalkeeper[i] != null) {
					entityList.add(goalkeeper[i]);
				}
			}
			
			// Sort entities by Y position 
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}

			});

			// draw entities
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			// empty entity list
			entityList.clear();

			// UI
			ui.draw(g2);
		}

		g2.dispose();
	}

	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSoundEffect(int i) {

		soundEffect.setFile(i);
		soundEffect.play();
	}
	
	public void blueIncrease() {
		ui.blueScore ++;
	}
	
	public void redIncrease() {
		ui.redScore ++;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// don't use this
	}

	// key pressing events
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// title state
		if (gameState == titleState) {
			if (ui.titleScreenState == 0) {
				if (code == KeyEvent.VK_W) {
					ui.commandNum--;
					if (ui.commandNum < 0) {
						ui.commandNum = 3;
					}
				}
				if (code == KeyEvent.VK_S) {
					ui.commandNum++;
					if (ui.commandNum > 3) {
						ui.commandNum = 0;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					if (ui.commandNum == 0) {
						gameState = playState;
						stopMusic();
					}
					if (ui.commandNum == 1) {
						ui.titleScreenState = 1;
						stopMusic();
					}
					if (ui.commandNum == 2) {
						ui.titleScreenState = 2;
					}
					if (ui.commandNum == 3) {
						System.exit(0);
					}
				}
			}

			else if (ui.titleScreenState == 1) {

				if (code == KeyEvent.VK_W) {
					ui.commandNum--;
					if (ui.commandNum < 0) {
						ui.commandNum = 4;
					}
				}
				if (code == KeyEvent.VK_S) {
					ui.commandNum++;
					if (ui.commandNum > 4) {
						ui.commandNum = 0;
					}
				}
				// call the load method from the databasae
				if (code == KeyEvent.VK_ENTER) {
					if (ui.commandNum == 0) {
						gameState = playState;
						SaveLoad.getInstance().load(1, this);
					}
					if (ui.commandNum == 1) {
						gameState = playState;
						SaveLoad.getInstance().load(2, this);
					}
					if (ui.commandNum == 2) {
						gameState = playState;
						SaveLoad.getInstance().load(3, this);

					}
					if (ui.commandNum == 3) {
						gameState = playState;
						SaveLoad.getInstance().load(4, this);

					}
					if (ui.commandNum == 4) {
						gameState = playState;
						SaveLoad.getInstance().load(5, this);
					}
 				}
				
			}
			
			else if (ui.titleScreenState == 2) {

			    if (code == KeyEvent.VK_W) {
			        ui.commandNum--;
			        if (ui.commandNum < 1) {
			            ui.commandNum = 2;
			        }
			    }
			    if (code == KeyEvent.VK_S) {
			        ui.commandNum++;
			        if (ui.commandNum > 2) {
			            ui.commandNum = 1;
			        }
			    }

			    if (code == KeyEvent.VK_ENTER) {
			    	
			    	if (ui.commandNum == 1) {
		                ui.titleScreenState = 0;
		            }
		            if (ui.commandNum == 2) {
		            	gameState = playState;
		            }
			       
			    }
			}
		}

		// play state
		else if (gameState == playState) {
			if (code == pauseKey) {gameState = pauseState;}
			if (code == escKey) {escPressed = true;}
			if (code == saveKey) {ui.saveOptions();}
            if(code == spaceKey) {spacePressed = true;}
            if (escPressed == true) {
            	System.exit(0);
            }
		}

		// pause state
		else if (gameState == pauseState) {
			if (code == pauseKey) {gameState = playState;}
		}

	}

	// key releasing events
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (gameState == playState || gameState == pauseState || gameState == pauseState) {
	        if(code == spaceKey) {spacePressed = true;}
		}
		
	}
}
