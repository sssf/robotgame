package world;

import actors.*;
import java.util.LinkedList;
import java.awt.Graphics2D;

public class World {
    private LinkedList<MoveableActor> actors = new LinkedList<MoveableActor>();
    private Map map;
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

    public void initWorld(int width, int height, int tileSize) {
        map = new Map(width, height, tileSize);
    }

    public void setTile(int x, int y, Tile tile) {
        map.setTile(x, y, tile);
    }

    public Tile[][] getTileMap() {
        return map.getTileMap();
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
            i.update();
        }
    }

    public void draw(Graphics2D g) {
        map.draw(g);
        for (MoveableActor i : actors) {
            i.draw(g);
        }
    }

    public MoveableActor createActor(int x, int y, int tileSize, int type) {
        if (type == PLAYER) {
            actors.add(new Player(x, y, tileSize, map.getTileMap()));
            return actors.getLast();
        }
        if (type == ENEMY) {
            actors.add(new Enemy(x, y, tileSize, map.getTileMap()));
            return actors.getLast();
        }
        if (type == SMART_ENEMY) {
            actors.add(new SmartEnemy(x, y, tileSize, map.getTileMap()));
            return actors.getLast();
        }
        else return null;
    }

    public LinkedList<MoveableActor> getActorList() {
        return this.actors;
    }
}
