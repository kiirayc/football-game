package main;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class UtilityTool {
    // Singleton instance variable
    private static final UtilityTool instance = new UtilityTool();
    
    // Private constructor 
    private UtilityTool() {} 

    // Static method to retrieve the singleton instance
    public static UtilityTool getInstance() {
        return instance;
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
    	
        // Create a new BufferedImage with the desired width & height
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        
        // Create a Graphics2D object to draw on the new image
        Graphics2D g2 = scaledImage.createGraphics();

        // Create an AffineTransform for scaling
        AffineTransform at = AffineTransform.getScaleInstance((double) width / original.getWidth(),
                                                             (double) height / original.getHeight());

        // Apply the transformation 
        g2.transform(at);

        // Draw the original image onto the scaled image
        g2.drawImage(original, 0, 0, null);
        
        // Dispose of the Graphics2D object to release resources
        g2.dispose();
        return scaledImage;
    }

    public BufferedImage loadImage(String imagePath, int width, int height) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(imagePath + ".png");
            if (inputStream == null) {
                // Handle the case where the resource is not found
                throw new IOException("Resource not found: " + imagePath);
            }

            BufferedImage image = ImageIO.read(inputStream);
            return scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null when error happens 
        }
    }

}

