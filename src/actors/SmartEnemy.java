package actors;

import java.awt.*;
import world.Map;

public class SmartEnemy extends Enemy {

    public SmartEnemy(int x, int y, int tileSize, Tile[][] map) {
        super(x, y, tileSize, map);
        canFall = false;
        canClimb = false;
    }
    public void draw(Graphics2D g) {
        g.setColor(Color.orange);
        g.fillRect(x, y, tileSize, tileSize);
    }

    public void updateState() {
        this.state = MOVE;
        super.updateState();
    }
}
