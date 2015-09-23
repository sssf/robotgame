package world;

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

    public Map(Tile[][] tiles) {
        width = 16;
        height = 15;
        this.tiles = tiles;
        for (Tile[] t1 : tiles) {
            for (Tile t2 : t1) {
                if (t2 != null) {
                    //System.out.printf("Tile map\n\tX:%d\n\tY:%d\n", t2.getX(), t2.getY());
                }
            }
        }
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

    public void draw(Graphics2D g) {
        //System.out.println("Map: draw width = " + width + "height = "+ height);
        for (int i = 0; i < width;++i) {
            for (int j = 0; j < height; ++j) {
                if (tiles[i][j] != null) {
                    tiles[i][j].draw(g);
                }
            }
        }
    }
}

