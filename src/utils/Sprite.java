package utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.File;
	
	/**
	 * Utility method to handle resource loading failure
	 * 
	 * @param message The message to display on failure
	 */

public class Sprite {
	private Image image;
	
	public Sprite(String url) {
		loadSprite(url);
	}
	
	public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}
	
	public void draw(Graphics g,int x,int y) {
		g.drawImage(image,x,y,null);
	}

	public void loadSprite(String ref) {

		BufferedImage sourceImage = null;
		
		try {
			//image = ImageIO.read(new File("smiley.gif"));	
			//if (url == null) {
				//System.out.println("Can't find ref: "+ref);
			//}
			URL url = this.getClass().getClassLoader().getResource("res/smiley.gif"); // "../resources/smiley.gif" doesnt work =(
			
			if (url == null) {
				System.out.println("Can't find ref: "+ref);
                System.exit(0);
			}
			
			// use ImageIO to read the image in

			sourceImage = ImageIO.read(url);
             //use ImageIO to read the image in

			//sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Failed to load: "+ref);
            System.exit(0);
		}
		
		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		
		// draw our source image into the accelerated image
		image.getGraphics().drawImage(sourceImage,0,0,null);
	}
}
