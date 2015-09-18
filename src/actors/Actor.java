package actors;

import java.awt.*;

public abstract class Actor {
    public int x, y, w, h;
    protected int tileSize;
    protected boolean hitbox;

    public Actor(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setX(int x) {
		this.x = tileSize * x;
	}

	public void setY(int y) {
		this.y = tileSize * y;
	}

    public int getX() {
        return (x / tileSize);
    }

    public int getY() {
        return (y / tileSize);
    }

    public void draw(Graphics2D g) {}
}
