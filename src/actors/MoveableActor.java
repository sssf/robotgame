package actors;

import world.World;
public abstract class MoveableActor extends Actor {
	//protected boolean isMovingLeft = false;
	//protected boolean isMovingRight = false;
    protected int state;
    protected final static int NONE = 0;
    protected final static int MOVE = 1;
    protected final static int CLIMB = 2;
    protected final static int GROW = 3;
    protected final static int SHRINK = 4;
    protected final static int FALL = 5;
    protected final static int TIPPING = 6;
    protected static Tile[][] map;

    protected boolean canFall;
    protected boolean canClimb;
	protected int direction = 1;

	public MoveableActor(int tileSize, Tile[][] map) {
		super(tileSize);
        this.map = map;
	}

    public void update() {
        if (!isOnGround() && state != CLIMB && !abovePlayer() && x % tileSize == 0) {
            state = FALL;
        }

        if (state == FALL) {
            fall();
        }
        if (state == MOVE || state == CLIMB) {
            move();
            if (state == MOVE || state == CLIMB)
                updatePosition();
        }
    }
    public void updatePosition() {
		int animationSpeed = 8;
		if (direction != 0) {
			x += animationSpeed * direction;
			if (x % tileSize == 0) {
                state = NONE;
			}
		}
    }
    public void move() {
        int x = getX();
        int y = getY();
        int direction = getDirection();
        if (wallCollision()) {
            if (canClimb) {
                state = NONE;
            }else {
                if (getRealX() % tileSize == 0) {
                state = NONE;
                setDirection(this.direction * -1);
                }
            }
        } else if (holeInFloor()) {
            if (playerInHole()) {

            } else if (!canFall) {
                setDirection(this.direction * -1);
            }
        } else if (map[x + (1 * direction)][y] != null && map[x + (1 * direction)][y - 1] == null) {
        } else if (map[x + (1 * direction)][y - 1] != null) {
            state = NONE;
        }
    }

    protected boolean wallCollision() {
        return map[getX() + (1 * direction)][getY()] != null;
    }

    protected boolean holeInFloor() {
        return map[getX() + (1 * direction)][getY() + 1] == null && getRealX() % tileSize == 0;
    }

    protected boolean playerInHole() {
        Player player = (Player)World.getInstance().getPlayer();
        return (player.getX() == getX() + direction && player.getY() == getY() + 1);
    }
    public int getDirection() {
        return direction;
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

    protected boolean abovePlayer() {
        Player player = (Player)World.getInstance().getPlayer();
        if (player == null) {
            return false;
        }
        return (player.getX() == getX() && player.getY() == getY() + 1);
    }

    protected  boolean isOnGround() {
        int x = getX();
        int y = getY();
        return (map[x][y + 1] != null);
    }
}
