package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import entity.Entity;

public class UI {
	
	public final BufferedImage finger, coach;
	
	GamePanel gp;
	Graphics2D g2;
	Font joystixM;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int commandNum = 0;
	public int titleScreenState = 0; 
	public int redScore = 0;
	public int blueScore = 0;
	
	public double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
	
		try {
			InputStream is = getClass().getResourceAsStream("/font/joystix monospace.ttf");
			joystixM = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//create heart object
		Entity heart = new Entity(gp);
		heart.name = "Heart";
		heart.image = UtilityTool.getInstance().loadImage("/objects/heart", gp.tileSize, gp.tileSize);
		heart.image2 = UtilityTool.getInstance().loadImage("/objects/heart_half", gp.tileSize, gp.tileSize);
		heart.image3 = UtilityTool.getInstance().loadImage("/objects/heart_blank", gp.tileSize, gp.tileSize);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;

		// title screen
		UtilityTool tool = UtilityTool.getInstance();
		finger = tool.loadImage("/objects/finger", gp.tileSize, gp.tileSize);
		coach = tool.loadImage("/npc/coachfront2", gp.tileSize, gp.tileSize);

	}
	
	// display message on the screen
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;		
	}
	
	// draw UI components
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(joystixM);
		g2.setColor(Color.black);
		
		//Title State
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		//Play State
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawScoreBoard();
			drawScore();
			
			//time
			playTime +=(double)1/60;
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
			g2.setColor(Color.black);
			g2.drawString("TIME:"+dFormat.format(playTime), gp.tileSize/2, gp.screenHeight-gp.tileSize/2);
			
			//message
			if (messageOn == true) {
				
				g2.setFont(g2.getFont().deriveFont(25F)); //F for float
				g2.drawString(message, gp.tileSize/2, (int) (gp.tileSize*2.2));
				
				messageCounter++;
				
				//70FPS equal to 4 seconds
				if(messageCounter > 210)  {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
		//Pause State
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}

	}
	
	// draw player life indicators
	public void drawPlayerLife() {
	    int x1 = (int) (gp.tileSize / 2 + gp.tileSize*1.5); // Player 1 X
	    int x2 = gp.screenWidth - gp.tileSize / 2 - gp.tileSize * gp.player2.maxLife / 2; // Player 2 X
	    int y = gp.tileSize / 2; 

	    for (int i = 0; i < gp.player1.maxLife; i += 2) {
	        // Player 1
	    	if (i < gp.player1.life) {
	    	    if (gp.player1.life - i == 1) {
	    	    	g2.drawImage(heart_half, x1, y, null);
	    	    } else {
	    	    	g2.drawImage(heart_full, x1, y, null);
	    	    }
	    	} else {
	    		g2.drawImage(heart_blank, x1, y, null);
	    	}
	        x1 += gp.tileSize;

	        // Player 2
	        if (i < gp.player2.life) {
	    	    if (gp.player2.life - i == 1) {
	    	    	g2.drawImage(heart_half, x2, y, null);
	    	    } else {
	    	    	g2.drawImage(heart_full, x2, y, null);
	    	    }
	    	} else {
	    		g2.drawImage(heart_blank, x2, y, null);
	    	}
	        x2 += gp.tileSize;
	    }
	    
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		g2.setColor(Color.black);
		g2.drawString("P1",gp.tileSize/2, 63);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		g2.setColor(Color.black);
		g2.drawString("P2",gp.screenWidth - gp.tileSize*2 - gp.tileSize * gp.player2.maxLife / 2, 63);
	}
	
	public void drawScore() {
	    // Set font and color
	    g2.setColor(Color.BLACK);
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

	    // Define positions for red and blue scores
	    int red_x = 663;
	    int red_y = gp.tileSize / 2 + 38;
	    int blue_x = 607;
	    int blue_y = gp.tileSize / 2 + 38;

	    // Draw red and blue scores
	    g2.drawString(Integer.toString(redScore), red_x, red_y);
	    g2.drawString(Integer.toString(blueScore), blue_x, blue_y);
	}

	public void drawScoreBoard() {
	    // Define dimensions and position for the score board
	    int x = 432;
	    int y = gp.tileSize / 2;
	    int width = 432;
	    int height = 50;

	    // Set colors for the score board background and border
	    Color c = new Color(255, 255, 255, 80);
	    g2.setColor(c);
	    g2.fillRoundRect(x, y, width, height, 35, 35);

	    c = new Color(0, 0, 0, 210);
	    g2.setColor(c);
	    g2.setStroke(new BasicStroke(5));
	    g2.drawRoundRect(x, y, width, height, 25, 25);

	    // Draw separator lines
	    g2.setColor(new Color(0, 0, 0));
	    g2.fillRect(592, gp.tileSize / 2, 5, 50);
	    g2.fillRect(647, gp.tileSize / 2, 5, 50);
	    g2.fillRect(700, gp.tileSize / 2, 5, 50);

	    // Draw text labels
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));

	    // Blue label
	    String text = "Blue";
	    int x2 = 734;
	    int y2 = gp.tileSize / 2 + 35;
	    g2.setColor(new Color(0, 0, 139)); // Blue color
	    g2.drawString(text, x2, y2);

	    // Red label
	    String text2 = "Red";
	    int x3 = 474;
	    int y3 = gp.tileSize / 2 + 35;
	    g2.setColor(new Color(139, 0, 0)); // Red color
	    g2.drawString(text2, x3, y3);
	}

	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			g2.setColor(new Color(0,0,0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			//Title Name
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75));
			String text = "Mini Football Game";
			int x = getXforCenteredText(text);
			int y = (int) (gp.tileSize*3.5);
			
			//shadow
			g2.setColor(Color.DARK_GRAY);
			g2.drawString(text, x+5, y+5);
			
			//main color
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//character image
			x = gp.screenWidth/2;
			y += gp.tileSize*1.5;
			g2.drawImage(gp.player1.downball2, x, y, gp.tileSize*3, gp.tileSize*3, null);
			g2.drawImage(coach, x-150, y, gp.tileSize*3, gp.tileSize*3, null); 
					
			//menu
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44));
			
			text = "Start";
			x = getXforCenteredText(text);
			y += gp.tileSize*5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawImage(finger, x-60, y-40, gp.tileSize, gp.tileSize, null); 
			}
			
			text = "Continue";
			x = getXforCenteredText(text);
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawImage(finger, x-60, y-40, gp.tileSize, gp.tileSize, null); 
			}
			
			text = "Tutorial";
			x = getXforCenteredText(text);
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawImage(finger, x-60, y-40, gp.tileSize, gp.tileSize, null); 
			}
			
			text = "Exit";
			x = getXforCenteredText(text);
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawImage(finger, x-60, y-40, gp.tileSize, gp.tileSize, null); 
			}
		}
		
		else if (titleScreenState == 1) {
		    g2.setColor(Color.white);
		    g2.setFont(g2.getFont().deriveFont(35F));

		    String headingText = "Select the game you would like to continue!";
		    int headingX = getXforCenteredText(headingText);
		    int headingY = gp.tileSize * 2; // Slightly move down

		    g2.drawString(headingText, headingX, headingY);

		    String[] options = {
		        "Option 1",
		        "Option 2",
		        "Option 3",
		        "Option 4",
		        "Option 5"
		    };

		    int optionX = getXforCenteredText(options[0]);
		    int optionY = headingY + gp.tileSize + 20; 

		    int rectWidth = (int)(800 + 20); 
		    int rectHeight = gp.tileSize * 2;
		    int rectX = (gp.getWidth() - rectWidth) / 2;

		    int gapY = gp.tileSize / 2 + 5; // Increase gap slightly

		    g2.setStroke(new BasicStroke(3));

		    for (int i = 0; i < options.length; i++) {
		        String optionText = options[i];
		        int rectY = optionY - (rectHeight / 2) + 20; // Move down slightly

		        g2.setColor(Color.white);
		        g2.drawRect(rectX, rectY, rectWidth, rectHeight);

		        g2.setColor(Color.white);
		        g2.setFont(g2.getFont().deriveFont(25F));

		        int textX = rectX + gp.tileSize / 2 + 10; 
		        int textY = rectY + (rectHeight / 2) + (g2.getFontMetrics().getHeight() / 3);
		        g2.drawString(optionText, textX, textY);

		        if (commandNum == i) {
		            g2.drawImage(finger, rectX - 60, rectY + rectHeight / 2 - gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
		        }

		        optionY += rectHeight + gapY;
		    }
		}
		
		else if (titleScreenState == 2) {
			//tutorial screen
			g2.setColor(Color.white);
		    g2.setFont(g2.getFont().deriveFont(42F));

		    String titleText = "Follow these instructions!";
		    int x = getXforCenteredText(titleText);
		    int y = gp.tileSize * 2;
		    g2.drawString(titleText, x, y);

		    g2.setFont(g2.getFont().deriveFont(20F));
		    String instructionText = "1. Control the red player with WASD keys. \n"
		    		+ "2. For the red player, push F to shoot and SPACE to tackle. \n"
		    		+ "3. Control the blue player with IJKL keys. \n"
		    		+ "4. For the blue player, push H to shoot and SHIFT to tackle. \n"
		    		+ "5. Every time you pick up a ball, your stamina decreases. \n"
		    		+ "6. You cannot pick up a ball when your stamina is 0 or below. \n"
		    		+ "7. Stamina refills when you steal the ball. \n"
		    		+ "8. Push F1 to save the game in the desired slot. \n"
		            + "9. Push P to pause and ESC to exit. \n";

		    x -= 80;
		    y = (int) (gp.tileSize * 3.8);		    

		    for (String line : instructionText.split("\n")) {
		        g2.drawString(line, x, y);
		        y += gp.tileSize; 
		    }
		    
		    g2.setFont(g2.getFont().deriveFont(28F));
		    String text = "Home";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawImage(finger, x-60, y-40, gp.tileSize, gp.tileSize, null); 
			}
			
			text = "Start Game!";
			x = getXforCenteredText(text);
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawImage(finger, x-60, y-40, gp.tileSize, gp.tileSize, null); 
			}
		}
	}
	
	// draw pause screen
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	// handle saving game options
	public void saveOptions() {
		
	    String[] options = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(options.length, 1));

	    // Create and configure the options buttons
	    Font font = g2.getFont().deriveFont(15F);
	    for (int idx = 0; idx < options.length; idx++) {
	        JButton button = new JButton(options[idx]);
	        button.setFont(font);
	        button.setForeground(Color.black); 
	        button.setBackground(Color.white); 
	        button.setPreferredSize(new Dimension(300, 50)); // Adjust button size as needed
	        
	        final int temp = idx+1;
	        button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	//Save method called here
	        	    SaveLoad.getInstance().save(temp, gp.player1.worldX, gp.player1.worldY, gp.player2.worldX, gp.player2.worldY, 
	        	    		gp.player1.life, gp.player2.life, playTime, blueScore, redScore);
	        	    System.out.println(temp);
	        	    gp.ui.showMessage("Progress Saved!");
	            }
	        });

	        panel.add(button);
	    }

	    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE);
	    JDialog dialog = optionPane.createDialog("Select Where to Store Your Game Data");
	    dialog.setPreferredSize(new Dimension(700, 500)); // Adjust the size as needed
	    dialog.setVisible(true);
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	// calculate X coordinate for centered text
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}

}
