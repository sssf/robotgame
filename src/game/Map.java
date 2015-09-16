package game;

import java.awt.*;

import actors.Tile;
public class Map {
    private Tile[][]tiles;
    int width, height;
    private int tileSize;

    public Map(int w, int h, int tileSize) {
        tiles = new Tile[w][h];
        this.tileSize = tileSize;
        width = w;
        height = h;
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
        tile.setX(x);
        tile.setY(y);
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public Tile[][] getTileMap() {
        return tiles;
    }

    public void drawTiles(Graphics g) {
        for (int i = 0; i < width;++i) {
            for (int j = 0; j < height; ++j) {
                if (tiles[i][j] != null) {
                    tiles[i][j].draw(g);
                }
            }
        }
    }
}

