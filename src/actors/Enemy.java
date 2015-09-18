package actors;

import java.awt.*;
import world.*;

public class Enemy extends MoveableActor {
    public Enemy(int x, int y, int tileSize, Tile[][] tileMap) {
        super(tileSize, tileMap);
        setX(x);
        setY(y);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.orange);
        g.fillRect(x, y, tileSize, tileSize);
    }

    public void update() {
        super.update();

        if (state == NONE) {
            state = MOVE;
        } 
    } 

}
