package utils;

import java.awt.Graphics2D;
import actors.Player;

public class Camera {
    private int x;
    private int y;
    private int maxX;
    private int minX;
    private int maxY;
    private int gameWidth;
    private int gameHeight;
    private int minY;
    private Player player;

    public Camera(Player player, int gameWidth, int gameHeight, int minX, int maxX, int minY, int maxY) {
        this.player = player;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        this.y = player.getRealY();
        this.x = player.getRealX();
    }

    public void update(Graphics2D g) {
        int px = player.getRealX();
        int d = px - x;
        d /= 10;
        x += d;
        if (x < minX) {
            x = minX;
        } else if (x > maxX) {
            x = maxX;
        }

        int playerY = player.getRealY();

        int py = playerY;
        d = py - y;
        d /= 10;
        y += d;


        if (y < minY) {
            y = minY;
        } else if (y > maxY) {
            y = maxY;
        }

        g.translate(gameWidth / 2 -x, gameHeight / 2 -y);
    }
}
