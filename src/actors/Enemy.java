package actors;

import java.awt.*;
import world.*;

public class Enemy extends MoveableActor {
    public Enemy(int x, int y, int tileSize, Tile[][] tileMap) {
        super(tileSize, tileMap);
        setX(x);
        setY(y);
        canFall = true;
        canClimb = false;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.green);
        g.fillRect(x, y, tileSize, tileSize);
    }

    public void updateState() {

        if (state == NONE) {
            state = MOVE;
        } 
        super.updateState();
    } 

}
