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

public class SpriteSheet {
    private String name;
    private BufferedImage image;
    private BufferedImage[][] sprite;

    public SpriteSheet(String name) {
        this.name = name;
        sprite = new BufferedImage[8][8];
        loadSpriteSheet(name);
        loadSprites();
    }
	public void loadSpriteSheet(String name) {

		BufferedImage sourceImage = null;

		try {
			URL url = this.getClass().getClassLoader().getResource("res/" + name); // "../resources/smiley.gif" doesnt work =(

			if (url == null) {
				System.out.println("Can't find ref: " + name);
                System.exit(0);
			}
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Failed to load: " + name);
            System.exit(0);
		}


		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

		// draw our source image into the accelerated image
		image.getGraphics().drawImage(sourceImage,0,0,null);
	}

    private void loadSprites() {
        for (int i = 0; i < 8;++i) {
            for (int j = 0; j < 8;++j) {
                sprite[i][j] = image.getSubimage(32 * i, 32 * j, 32, 32);
                if (sprite[i][j] == null) {
                    System.out.println("fitta också");
                    System.exit(0);
                } 
            }
        }
    }

    public BufferedImage getSprite(int x, int y) {
        if (sprite[x][y] == null) {
            System.out.println("Något gick fel lol");
            System.exit(0);
        } 
        return sprite[x][y];
    }
}
