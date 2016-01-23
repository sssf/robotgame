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
    private BufferedImage[] sprite;

    public SpriteSheet(String name) {
        this.name = name;
        sprite = new BufferedImage[64];
        loadSpriteSheet(name);
        loadSprites();
    }
	public void loadSpriteSheet(String name) {

		BufferedImage sourceImage = null;

		try {
			URL url = this.getClass().getClassLoader().getResource("res/" + name); // "../resources/smiley.gif" doesnt work =(

			if (url == null) {
				System.out.println("Can't find tilesheet: " + name);
                System.exit(0);
			}
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Failed to load tilesheet: " + name);
            System.exit(0);
		}


		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

		image.getGraphics().drawImage(sourceImage,0,0,null);
	}

    private void loadSprites() {
        int count = 0;
        for (int i = 0; i < 8;++i) {
            for (int j = 0; j < 8;++j) {
                sprite[count] = image.getSubimage(32 * i, 32 * j, 32, 32);
                count++;
            }
        }
    }

    public BufferedImage getSprite(int type) {
        return sprite[type];
    }
}
