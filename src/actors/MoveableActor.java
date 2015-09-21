package actors;
import java.util.LinkedList;

import world.World;
public abstract class MoveableActor extends Actor {
    protected int state;
    protected final static int NONE    = 0;
    protected final static int MOVE    = 1;
    protected final static int CLIMB   = 2;
    protected final static int GROW    = 3;
    protected final static int SHRINK  = 4;
    protected final static int FALL    = 5;
    protected final static int TIPPING = 6;
    protected static Tile[][] map;
    protected int stance = 0;

    protected boolean canFall;
    protected boolean canClimb;
	protected int direction = 1;
    protected int animation_thing = 0;

	public MoveableActor(int tileSize, Tile[][] map) {
		super(tileSize);
        this.map = map;
	}

    public void updateState() {
        animation_thing+= 1;
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
		int animationSpeed = tileSize / 4;
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
            if (actorInHole()) {

            } else if (!canFall) {
                state = NONE;
                setDirection(this.direction * -1);
            }
        }
    }

    protected boolean wallCollision() {
        return map[getX() + (1 * direction)][getY()] != null;
    }

    protected boolean actorCollision() {
        for (MoveableActor a : World.getInstance().getActorList()) {
            if ( a != this && (this.getRealX() + tileSize >= a.getRealX() && this.getRealX() <= a.getRealX() + tileSize && this.getY() == a.getY())) {
                return true;
            }
        }
        return false;
    }

    protected boolean holeInFloor() {
        return map[getX() + (1 * direction)][getY() + 1] == null && getRealX() % tileSize == 0;
    }

    protected boolean actorInHole() {
        for (MoveableActor actor : World.getInstance().getActorList())
        {
            if (actor.getX() == getX() + direction && actor.getY() - actor.getStance() == getY() + 1)
                return true;
        }
        return false;
    }

    protected void fall() {
        int animationSpeed = tileSize / 2;
        y += animationSpeed;
        if (y % tileSize == 0 && isOnGround()) {
            state = NONE;
        }
    }

	public void setDirection(int direction) {
        this.direction = direction;
	}

    public void moveUp() {
        setY(getY() - 1);
    }

    protected  boolean isOnGround() {
        int x = getX();
        int y = getY();
        return (map[x][y + 1] != null);
    }

    protected boolean aboveActor() {
        for (MoveableActor a : World.getInstance().getActorList()) {
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
