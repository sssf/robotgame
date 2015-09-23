package utils;

import java.awt.Graphics2D;
import actors.Player;

public class Camera {
    private int x;
    private int y;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private Player player;

    public Camera(Player player, int minX, int maxX, int minY, int maxY) {
        this.player = player;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        y = player.getRealY();
        x = player.getRealX();
    }

    public void update(Graphics2D g) {
        this.x = player.getRealX();
        if (x < minX) {
            x = minX;
        } else if (x > maxX) {
            x = maxX;
        }

        int playerY = player.getRealY();
        if (Math.abs(playerY - y) > 8) {
            if (y < playerY) {
                y += 8;
            } else if (y > playerY) {
                y -= 8;
            }
            if (y < minY) {
                y = minY;
            } else if (y > maxY) {
                y = maxY;
            }
        }

        //System.out.println(x);
        g.translate(400-x, 300-y);
    }
}
