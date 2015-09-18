package actors;

import java.awt.*;

public class Player extends MoveableActor{

    private int stance = 1; //0 = box_mode, 1 = normal, 2 = long legs.

    //Orkar inte med 3000 get/set till.
    public boolean buttonLeft = false;
    public boolean buttonRight = false;
    public boolean buttonUp = false;
    public boolean buttonDown = false;
    private int rotation = 0;
    private int maxRotation = 90;
    private int tippingLenght = 1;
    private int tippingHeight = 1;

    public Player(int x, int y, int tileSize, Tile[][] map) {
        super(tileSize, map);
        setX(x);
        setY(y);
        tileSize = tileSize;
        state = NONE;

        canClimb = true;
        canFall = true;
    }

    public int getStance() {
        return stance;
    }

    public void update() {
        super.update();
        if (state == GROW) {
            if (stance < 3) {
                stance++;
            }
            state = NONE;
        }
        if (state == SHRINK) {
            if (stance > 0) {
                stance--;
            }
            state = NONE;
        }
        setKeys();
        act();
    }
    private void updateRotation(Graphics2D g) {
        rotation += direction * 5;
        g.rotate(Math.toRadians(rotation), x + (direction == 1 ? tileSize : 0), y+tileSize);

        g.setColor(Color.blue);
        g.fillRect(x, y, tileSize, tileSize);
        g.fillRect(x, y-tileSize, tileSize, tileSize+h);
        g.fillRect(x, y-(tileSize*2), tileSize, tileSize+h);

        g.rotate(Math.toRadians(-rotation), x + (direction == 1 ? tileSize : 0), y+tileSize);
        if (rotation % maxRotation == 0) {
            rotation = 0;
            x += tippingLenght * tileSize * direction;
            y -= tippingHeight * tileSize;
            stance = 1;
            state = NONE;
        }

    }

    private void setKeys() {
        if (state == NONE) {
            if (buttonLeft) {
                this.direction = -1;
                state = MOVE;
            }
            if (buttonRight) {
                this.direction = 1;
                state = MOVE;
            }
            if (buttonUp) {
                buttonUp = false;
                state = GROW;
                //moveUp();
            }
            if (buttonDown) {
                buttonDown = false;
                state = SHRINK;
            }
        }
    }

    private void act() {
        switch (state) {
            case MOVE: move(); break;
            case GROW: break;
            case SHRINK: break;
        }
    }

    public void draw(Graphics2D g) {
        if (state == TIPPING) {
            updateRotation(g);
            return;
        }
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

    public void move() {
        int realX = getRealX();
        if (x % tileSize != 0)
            return;
        int x = getX();
        int y = getY();
        if (stance == 0) {
            state = NONE;
        }
        if (stance == 3) {
            if (wallCollision()) {
                state = NONE;
            } else if (map[getX() + (3 * direction)][getY()] != null) {
                state = TIPPING;
                maxRotation = 65;
                tippingLenght = 3;
                tippingHeight = 1;
            } else if (map[getX() + (3 * direction)][getY() - 1] != null) {
                state = TIPPING;
                maxRotation = 40;
                tippingLenght = 2;
                tippingHeight = 2;
            }
            else if (map[getX() + (2 * direction)][getY() - 2] != null) {
                state = TIPPING;
                maxRotation = 15;
                tippingLenght = 1;
                tippingHeight = 2;
            } else if (map[getX() + (2 * direction)][getY() - 1] != null) {
                state = TIPPING;
                maxRotation = 25;
                tippingLenght = 1;
                tippingHeight = 1;
            } else if (map[getX() + (2 * direction)][getY()] != null) {
                state = TIPPING;
                maxRotation = 40;
                tippingLenght = 2;
                tippingHeight = 1;
            } else {
                state = TIPPING;
                maxRotation = 90;
                tippingLenght = 3;
                tippingHeight = 0;
            }
        }
        if (stance == 1 && wallCollision()) {
            state = NONE;
        } else if (stance == 2 && map[x + (1 * direction)][y] != null && map[x + (1 * direction)][y - 1] == null && map[x][y + 1] != null) {
            moveUp();
            state = CLIMB;
            stance = 1;
        } else if (stance == 2 && map[x + (1 * direction)][y - 1] != null) {
            state = NONE;
        }
    }
}
