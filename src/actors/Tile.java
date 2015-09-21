package actors;

import java.awt.*;

import utils.Sprite;
public class Tile extends Actor{
    //Sprite sprite = new Sprite("smiley.gif");
    int type;
    public Tile(int tileSize) {
        super(tileSize);
    }

    public Tile(int tileSize, int type) {
        super(tileSize);
        this.type = type;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        //g.fillRect(x, y, tileSize, tileSize);
        g.setColor(Color.green);
        g.drawRect(x, y, tileSize, tileSize);
        //sprite.draw(g, x, y);
    }
}
