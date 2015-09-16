package actors;

public abstract class MoveableActor extends Actor {
	//protected boolean isMovingLeft = false;
	//protected boolean isMovingRight = false;
    protected boolean wantsToMove = false;
	protected boolean isMoving = false;
	protected int direction = 1;

	public MoveableActor(int tileSize) {
		super(tileSize);
	}

    public boolean wantsToMove() {
        return wantsToMove;
    }
    public void setWantsToMove(boolean b) {
        wantsToMove = b;
    }
	public boolean isMoving() {
		return isMoving;
	}

	public void move() {
		if (!isMoving)
			return;
		int animationSpeed = 8;
		if (direction != 0) {
			x += animationSpeed * direction;
			if (x % tileSize == 0) {
				isMoving = false;
                //wantsToMove = false;
			}
		}
	}

    public int getDirection() {
        return direction;
    }

	//public boolean isMovingLeft() {
		//return isMovingLeft;
	//}

	//public boolean isMovingRight() {
		//return isMovingRight;
	//}

	public void setDirection(int direction) {
        this.direction = direction;
        wantsToMove = true;
	}

    public void moveUp() {
        setY(getY() - 1);
    }

	public void moveDown() {
		setY(getY() + 1);
	}
	public void moveLeft() {
		direction = -1;
		//isMovingLeft = true;
		//isMovingRight = false;
		isMoving = true;
	}

	public void moveRight() {
		direction = 1;
		//isMovingRight = true;
		//isMovingLeft = false;
		isMoving = true;
	}
}
