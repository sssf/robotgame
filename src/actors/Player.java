package actors;

import java.awt.*;
import game.Map;

public class Player extends MoveableActor{

    private int stance = 1; //0 = box_mode, 1 = normal, 2 = long legs.
    private int newStance = 1;
    private boolean canMove = true;

    //Orkar inte med 3000 get/set till.
    public boolean buttonLeft = false;
    public boolean buttonRight = false;
    public boolean buttonUp = false;
    public boolean buttonDown = false;

    private enum Action {
        NONE,
        MOVE,
        CLIMB,
        GROW,
        SHRINK,
        FALL
    }

    private enum Stance {
        BOX,
        NORMAL,
        STANDING
    }

    Action action;

    public Player(int x, int y, int tileSize) {
        super(tileSize);
        setX(x);
        setY(y);
        //h = tileSize;
        //direction = 0;
        tileSize = tileSize;
        action = action.NONE;
    }

    //public void setStance(int stance) {
        //if (stance < 0 || stance > 3 || !canMove) {
            //return;
        //}
        //this.newStance = stance;
        //if (newStance == 0 && this.stance == 1) {
            //this.stance = 0;
            //return;
        //} else if (newStance == 1 && this.stance == 0) {
            //this.stance = 1;
            //return;
        //}
        //if (newStance != this.stance) {
            //canMove = false;
        //}
    //}


    //public boolean canMove() {
        //return (!isMoving && canMove);
    //}

    public int getStance() {
        return stance;
    }

    public void update(Map map) {
        if (!isOnGround(map) && action != action.CLIMB && x % tileSize == 0) {
            action = action.FALL;
        }

        if (action == action.FALL) {
            fall(map);
        }

        if (action == action.MOVE || action == action.CLIMB) {
            updatePosition();
            if (action == action.MOVE) {
                return;
            }
        }

        if (action == action.GROW) {
            if (stance < 3) {
                stance++;
            }
            action = action.NONE;
        }

        if (action == action.SHRINK) {
            if (stance > 0) {
                stance--;
            }
            action = action.NONE;
        }

        if (action == action.NONE) {
            if (buttonLeft) {
                this.direction = -1;
                action = action.MOVE;
            }
            if (buttonRight) {
                this.direction = 1;
                action = action.MOVE;
            }
            if (buttonUp) {
                buttonUp = false;
                action = action.GROW;
                //moveUp();
            }
            if (buttonDown) {
                buttonDown = false;
                action = action.SHRINK;
            }
        }
        act(map);
    }
    private boolean isOnGround(Map map) {
        Tile[][] tileMap = map.getTileMap();
        int x = getX();
        int y = getY();
        return (tileMap[x][y + 1] != null);
    }
    private void act(Map map) {
        switch (action) {
            case MOVE: move(map); break;
            case GROW: break;
            case SHRINK: break;
        }
    }
    private void updatePosition() {
		int animationSpeed = 8;
		if (direction != 0) {
			x += animationSpeed * direction;
			if (x % tileSize == 0) {
                action = action.NONE;
			}
		}
    }

    private void fall(Map map) {
        int animationSpeed = 16;
        y += animationSpeed;
        if (y % tileSize == 0 && isOnGround(map)) {
            stance = 1;
            action = action.NONE;
        }
    }

    public void draw(Graphics g) {
        if (stance == 0) {
            g.setColor(Color.red);
            g.fillRect(x, y, tileSize, tileSize);
            g.setColor(Color.green);
            g.drawRect(x, y, tileSize, tileSize);
        } else if (stance == 1){
            g.setColor(Color.blue);
            g.fillRect(x, y, tileSize, tileSize+h);
        } else if (stance == 2) {
            g.setColor(Color.blue);
            g.fillRect(x, y, tileSize, tileSize);
            g.fillRect(x, y-tileSize, tileSize, tileSize+h);
        } else if (stance == 3) {
            g.setColor(Color.blue);
            g.fillRect(x, y, tileSize, tileSize);
            g.fillRect(x, y-tileSize, tileSize, tileSize+h);
            g.fillRect(x, y-(tileSize*2), tileSize, tileSize+h);

        }
    }

    public void move(Map map) {
        Tile[][] tileMap = map.getTileMap();
        int x = getX();
        int y = getY();
        if (stance == 0 || stance == 3) {
            action = action.NONE;
        }
        if (stance == 1 && tileMap[x + (1 * direction)][y] != null) {
            action = action.NONE;
        //}else if (stance == 2 && tileMap[x + (1 * direction)][y] == null && tileMap[x + (1 * direction)][y - 1] == null) {
        } else if (stance == 2 && tileMap[x + (1 * direction)][y] != null && tileMap[x + (1 * direction)][y - 1] == null) {
            //moveLeft();
            moveUp();
            action = action.CLIMB;
            stance = 1;
        } else if (stance == 2 && tileMap[x + (1 * direction)][y - 1] != null) {
            action = action.NONE;
        }
    }
}
