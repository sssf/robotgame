package world;

import actors.*;
import java.util.LinkedList;
import java.awt.Graphics2D;

public class World {
    private LinkedList<MoveableActor> actors = new LinkedList<MoveableActor>();
    private Tile[][] map;
    private int width;
    private int height;
    private static World instance = null;
    public static int PLAYER = 0;
    public static int ENEMY = 1;
    public static int SMART_ENEMY = 2;

    private World() {

    }

    public static World getInstance() {
        return instance;
    }

    public static World createWorld() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public void loadMap(int tileSize, String path) {
        //map = new Map(width, height, tileSize);
        map = utils.MapLoader.readMap(path);
        width = utils.MapLoader.getWidth();
        height = utils.MapLoader.getHeight();
    }
    //public void initWorld(int width, int height, int tileSize) {
        //map = new Map(width, height, tileSize);
        //map = new Map(utils.MapLoader.readMap());
    //}

    //public void setTile(int x, int y, Tile tile) {
        //map.setTile(x, y, tile);
    //}

    public Tile[][] getTileMap() {
        return map;
    }

    public MoveableActor getPlayer() {
        for (MoveableActor i : actors) {
            if (i instanceof Player) {
                return i;
            }
        }
        return null;
    }

    public void update() {
        for (MoveableActor i : actors) {
            i.updateState();
        }
        for (MoveableActor i : actors) {
            i.updatePosition();
        }
    }

    public void draw(Graphics2D g) {
        drawMap(g);
        for (MoveableActor i : actors) {
            if (i != null) {
                i.draw(g);
            }
        }
    }

    private void drawMap(Graphics2D g) {
        //System.out.println("Map: draw width = " + width + "height = "+ height);
        for (int i = 0; i < width;++i) {
            for (int j = 0; j < height; ++j) {
                if (map[i][j] != null) {
                    map[i][j].draw(g);
                }
            }
        }
    }
    public MoveableActor createActor(int x, int y, int tileSize, int type) {
        if (type == PLAYER) {
            actors.add(new Player(x, y, tileSize, map));
            return actors.getLast();
        }
        if (type == ENEMY) {
            actors.add(new Enemy(x, y, tileSize, map));
            return actors.getLast();
        }
        if (type == SMART_ENEMY) {
            actors.add(new SmartEnemy(x, y, tileSize, map));
            return actors.getLast();
        }
        else return null;
    }

    public LinkedList<MoveableActor> getActorList() {
        return this.actors;
    }
}
