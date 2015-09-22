package actors;

import java.awt.*;
import java.util.LinkedList;
import world.*;
public class Player extends MoveableActor{


    //Orkar inte med 3000 get/set till.
    public boolean buttonLeft = false;
    public boolean buttonRight = false;
    public boolean buttonUp = false;
    public boolean buttonDown = false;
    private int rotation = 0;
    private int maxRotation = 90;
    private int tippingLength = 1;
    private int tippingHeight = 1;

    private int NORMAL = 0;
    private int TALL   = 1;
    private int TALLER = 2;

    public Player(int x, int y, int tileSize, Tile[][] map) {
        super(tileSize, map);
        setX(x);
        setY(y);
        tileSize = tileSize;
        state = NONE;

        canClimb = true;
        canFall = true;
    }

    public void updateState() {
        if (state == GROW) {
            if (stance < 2) {
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
        super.updateState();
    }
    private void updateRotation(Graphics2D g) {//TODO: Update x and y continuosly instead of just setting them at the end
        rotation += direction * 5;
        g.rotate(Math.toRadians(rotation), x + (direction == 1 ? tileSize : 0), y+tileSize);

            g.setColor(Color.white);
            g.fillRect(x, y, 2, tileSize);
            g.fillRect(x+30, y, 2, tileSize);
            g.fillRect(x, y-tileSize, 2, tileSize);
            g.fillRect(x+30, y-tileSize, 2, tileSize);
            //g.fillRect(x, y-tileSize, tileSize, tileSize);
            g.fillRect(x, y-(tileSize*2), tileSize, tileSize);
        //g.setColor(Color.blue);
        //g.fillRect(x, y, tileSize, tileSize);
        //g.fillRect(x, y-tileSize, tileSize, tileSize+h);
        //g.fillRect(x, y-(tileSize*2), tileSize, tileSize+h);

        g.rotate(Math.toRadians(-rotation), x + (direction == 1 ? tileSize : 0), y+tileSize);
        if (rotation % maxRotation == 0) {
            rotation = 0;
            x += tippingLength * tileSize * direction;
            y -= tippingHeight * tileSize;
            stance = 0;
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
                if (map[getX()][getY() - stance - 1] == null)
                    state = GROW;
                //moveUp();
            }
            if (buttonDown) {
                buttonDown = false;
                state = SHRINK;
            }
        }
    }

    public void draw(Graphics2D g) {
        if (state == TIPPING) {
            updateRotation(g);
            return;
        }
        if (stance == -1) {
            g.setColor(Color.red);
            g.fillRect(x, y, tileSize, tileSize);
            g.setColor(Color.green);
            g.drawRect(x, y, tileSize, tileSize);
        } else if (stance == 0){
            g.setColor(Color.white);
            g.fillRect(x, y, tileSize, tileSize);
        } else if (stance == 1) {
            int tmp = 0;
            //if (state == MOVE) {
            int tmp1 = 0;
            int tmp2 = 0;
            animation_thing %= 8;
               if (animation_thing < 4) {
                   tmp1 = 16;
                   tmp2 = 0;
               } else {
                   tmp1 = 0;
                   tmp2 = 16;
               }
            //}
            g.setColor(Color.white);
            g.fillRect(x, y, 2, tileSize-(tmp1));
            g.fillRect(x+30, y, 2, tileSize-(tmp2));
            g.fillRect(x, y-tileSize, tileSize, tileSize);
        } else if (stance == 2) {
            g.setColor(Color.white);
            g.fillRect(x, y, 2, tileSize);
            g.fillRect(x+30, y, 2, tileSize);
            g.fillRect(x, y-tileSize, 2, tileSize);
            g.fillRect(x+30, y-tileSize, 2, tileSize);
            //g.fillRect(x, y-tileSize, tileSize, tileSize);
            g.fillRect(x, y-(tileSize*2), tileSize, tileSize);
        }
    }

    private void setTipping(int state, int maxRotation, int tippingLength, int tippingHeight) {
        this.state = state;
        this.maxRotation = maxRotation;
        this.tippingLength = tippingLength;
        this.tippingHeight = tippingHeight;
    }

    public void move() {
        int realX = getRealX();
        if (x % tileSize != 0)
            return;
        int x = getX();
        int y = getY();
        if (stance == -1) {
            state = NONE;
        }
        if (stance == 2) {
            if (wallCollision()) {
                state = NONE;
            } else if (map[getX() + direction][getY() - 2] != null) {
                state = NONE;
            } else if (map[getX() + direction][getY() - 1] != null) {
                state = NONE;
            } else if (map[getX() + (2 * direction)][getY() - 2] != null) {
                setTipping(TIPPING, 20, 1, 2);
            } else if (map[getX() + (2 * direction)][getY() - 1] != null) {
                setTipping(TIPPING, 25, 1, 1);
            } else if (map[getX() + (2 * direction)][getY()] != null) {
                setTipping(TIPPING, 40, 2, 1);
            } else if (map[getX() + (3 * direction)][getY()] != null) {
                setTipping(TIPPING, 65, 3, 1);
            } else if (map[getX() + 3 * direction][getY() - 2] != null) {
                setTipping(TIPPING, 45, 2, 2);
            } else if (map[getX() + (3 * direction)][getY() - 1] != null) {
                setTipping(TIPPING, 40, 2, 2);
            } else {
                setTipping(TIPPING, 90, 3, 0);
            }
        }

        if (stance == 0 && wallCollision()) {
            state = NONE;
        } else if (stance == 1 && map[x + (1 * direction)][y] != null && map[x + (1 * direction)][y - 1] == null && (isOnGround() || aboveActor())) {
            moveUp();
            state = CLIMB;
            stance = 0;
        } else if (stance == 1 && map[x + (1 * direction)][y - 1] != null) {
            state = NONE;
        }
    }
}
