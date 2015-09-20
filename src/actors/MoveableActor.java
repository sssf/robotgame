package actors;
import java.util.LinkedList;

import world.World;
public abstract class MoveableActor extends Actor {
    protected int state;
    protected final static int NONE = 0;
    protected final static int MOVE = 1;
    protected final static int CLIMB = 2;
    protected final static int GROW = 3;
    protected final static int SHRINK = 4;
    protected final static int FALL = 5;
    protected final static int TIPPING = 6;
    protected static Tile[][] map;
    protected int stance = 0;

    protected boolean canFall;
    protected boolean canClimb;
	protected int direction = 1;

	public MoveableActor(int tileSize, Tile[][] map) {
		super(tileSize);
        this.map = map;
	}

    public void updateState() {

        if (!isOnGround() && state != CLIMB && !aboveActor() && x % tileSize == 0) {
            state = FALL;
        }

        if (state != FALL && actorCollision()) {
                direction *= -1;
                return;
        }

        if (state == FALL) {
            if (aboveActor()) {
                state = NONE;
                return;
            }
            fall();
        }
        if (state == MOVE || state == CLIMB) {
            move();
            if (state == MOVE || state == CLIMB) {
                //updatePosition();
            }
        }
    }
    public void updatePosition() {
        if (state != MOVE && state != CLIMB)
            return;
		int animationSpeed = 8;
		if (direction != 0) {
			x += animationSpeed * direction;
			if (x % tileSize == 0) {
                state = NONE;
			}
		}
    }
    public void move() {
        if (x % tileSize != 0) {
            return;
        }
        int x = getX();
        int y = getY();
        if (wallCollision() || actorCollision()) {
            state = NONE;
            setDirection(this.direction * -1);
        } else if (holeInFloor()) {
            if (playerInHole()) {

            } else if (!canFall) {
                setDirection(this.direction * -1);
            }
        } else if (map[x + (1 * direction)][y] != null && map[x + (1 * direction)][y - 1] == null) {}
    }

    protected boolean wallCollision() {
        return map[getX() + (1 * direction)][getY()] != null;
    }

    protected boolean actorCollision() {
        LinkedList<MoveableActor> actors = World.getInstance().getActorList();
        for (MoveableActor a : actors) {
            if ( a != this && (this.getRealX() + tileSize >= a.getRealX() && this.getRealX() <= a.getRealX() + tileSize && this.getY() == a.getY())) {
                return true;
            }
        }
        return false;
    }

    protected boolean holeInFloor() {
        return map[getX() + (1 * direction)][getY() + 1] == null && getRealX() % tileSize == 0;
    }

    protected boolean playerInHole() {//TODO: Generalize for all actor types
        Player player = (Player)World.getInstance().getPlayer();
        return (player.getX() == getX() + direction && player.getY() - player.getStance() == getY() + 1);
    }

    private void fall() {
        int animationSpeed = 16;
        y += animationSpeed;
        if (y % tileSize == 0 && isOnGround()) {
            state = NONE;
        }
    }

    public int getRealX() {
        return x;
    }
	public void setDirection(int direction) {
        this.direction = direction;
	}

    public void moveUp() {
        setY(getY() - 1);
    }

	public void moveDown() {
		setY(getY() + 1);
	}

	public void moveLeft() {
		direction = -1;
	}

	public void moveRight() {
		direction = 1;
	}

    protected  boolean isOnGround() {
        int x = getX();
        int y = getY();
        return (map[x][y + 1] != null);
    }

    private boolean aboveActor() {
        LinkedList<MoveableActor> actors = World.getInstance().getActorList();
        for (MoveableActor a : actors) {
            if (getRealX() + tileSize  > a.getRealX() && getRealX()  < a.getRealX() + tileSize && getY() + 1 == a.getY() - a.getStance()) {
                return true;
            }
        }
        return false;
    }
    public int getStance() {
        return stance;
    }
}
