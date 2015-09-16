package actors;

import java.awt.*;
import game.Map;

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

    public void update(Player player, Map map) {
        int x = getX();
        int y = getY();
        int direction = getDirection();
        Tile[][] tileMap = map.getTileMap();
        if (!isMoving()) {
            if (tileMap[x][y + 1] != null) {
                if (tileMap[x + (1 * direction)][y] == null && !(player.getStance() == 0 && (x + 1 * direction) == player.getX())) {
                    if (direction == -1) {
                        moveLeft();
                    } else if (direction == 1) {
                        moveRight();
                    }
                } else {
                    setDirection(direction * -1);
                }
            }
        }
        if (!isMoving() && tileMap[x][y + 1] == null) {
            moveDown();
        }
    }
}
