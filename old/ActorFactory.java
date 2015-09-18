package actors;
import java.util.LinkedList;
import java.awt.Graphics2D;

public class ActorFactory {
    LinkedList<MoveableActor> actors = new LinkedList<MoveableActor>();
    private static ActorFactory instance = null;

    private ActorFactory() {

    }

    public static ActorFactory getInstance() {
        if (instance == null) {
            instance = new ActorFactory();
        }
        return instance;
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
        for (MoveableActor i : actors) {
            i.draw(g);
        }
    }

    public MoveableActor createActor(int x, int y, int tileSize, Tile[][] map, int type) {
        if (type == 0) {
            actors.add(new Player(x, y, tileSize, map));
            return actors.getLast();
        }
        if (type == 1) {
            actors.add(new Enemy(x, y, tileSize, map));
            return actors.getLast();
        }
        if (type == 3) {
            actors.add(new SmartEnemy(x, y, tileSize, map));
            return actors.getLast();
        }
        else return null;
    }

    public LinkedList<MoveableActor> getActorList() {
        return this.actors;
    }
}
