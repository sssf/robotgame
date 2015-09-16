package actors;

import java.awt.*;

public class Enemy extends MoveableActor {
    public Enemy(int x, int y, int tileSize) {
        super(tileSize);
        setX(x);
        setY(y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(x, y, tileSize, tileSize);
    }
}
