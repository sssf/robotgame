package game;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import actors.Player;
import actors.Tile;
import actors.Enemy;

import java.awt.image.BufferStrategy;
public class Game extends Canvas implements KeyListener {

    private static int tileSize = 32;
    Map map = new Map(50, 50, tileSize);

    Player player;
    Enemy enemy;

    BufferStrategy strategy;

    int gameWidth = 800;
    int gameHeight = 600;

    public Game() {
        JFrame container = new JFrame("Game");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(gameWidth, gameHeight));
        panel.setLayout(null);
        setBounds(0,0, gameWidth, gameHeight);
        panel.add(this);
        setIgnoreRepaint(true);
        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);

        createBufferStrategy(2);
        strategy = getBufferStrategy();
        addKeyListener(this);
        requestFocus();
        initGame();
    }
    public void initGame() {
        initMap();
        player = new Player(8, 1, tileSize);
        enemy  = new Enemy(1, 4, tileSize);
    }
    public void initMap() {
        map.setTile(0, 0, new Tile(tileSize));
        map.setTile(0, 1, new Tile(tileSize));
        map.setTile(0, 2, new Tile(tileSize));
        map.setTile(0, 3, new Tile(tileSize));
        map.setTile(0, 4, new Tile(tileSize));
        map.setTile(1, 5, new Tile(tileSize));
        map.setTile(2, 6, new Tile(tileSize));
        map.setTile(3, 7, new Tile(tileSize));
        map.setTile(4, 8, new Tile(tileSize));
        map.setTile(5, 9, new Tile(tileSize));
        map.setTile(5, 9, new Tile(tileSize));
        map.setTile(5, 9, new Tile(tileSize));
        map.setTile(6, 9, new Tile(tileSize));
        map.setTile(7, 9, new Tile(tileSize));
        map.setTile(8, 9, new Tile(tileSize));
        //map.setTile(8, 8, new Tile(tileSize));
        map.setTile(9, 9, new Tile(tileSize));
        map.setTile(10, 9, new Tile(tileSize));
        map.setTile(11, 9, new Tile(tileSize));
        map.setTile(12, 9, new Tile(tileSize));
        map.setTile(13, 8, new Tile(tileSize));
        map.setTile(14, 8, new Tile(tileSize));
        map.setTile(15, 8, new Tile(tileSize));
        map.setTile(16, 8, new Tile(tileSize));
        map.setTile(16, 7, new Tile(tileSize));
        map.setTile(16, 6, new Tile(tileSize));
        //map.setTile(16, 5, new Tile(tileSize));
    }

    double x1 = 50;
    double y1 = 50;
    double x2 = 50;
    double y2 = 100;
    public void render() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,gameWidth,gameHeight);
        drawWorld(g);
        player.draw(g);
        enemy.draw(g);

        g.setColor(Color.green);
        double angle = 0.05;
        double vx = x2 - x1;
        double vy = y2 - y1;

        vx = vx * Math.cos(angle) - vy * Math.sin(angle);
        vy = vx * Math.sin(angle) + vy * Math.cos(angle);

        //x1 = (x1 - x2) * (Math.cos(angle) - (y1 - y2) * Math.sin(angle));
        //y1 = (x1 - x2) * (Math.sin((angle)) + (y1 - y2) * Math.cos(angle));
        x2 = x1 + vx;
        y2 = y1 + vy;

        g.draw(new Line2D.Double(x1, y1, x2, y2));
        g.dispose();
        strategy.show();
    }

    public void drawWorld(Graphics g) {
        map.drawTiles(g);
    }

    public void updateGame() {
        //updatePlayer();
        enemy.move();
        updateEnemy();
    }

    public void updateEnemy() {
        int x = enemy.getX();
        int y = enemy.getY();
        int direction = enemy.getDirection();
        Tile[][] tileMap = map.getTileMap();
        if (!enemy.isMoving()) {
            if (tileMap[x][y + 1] != null) {
                if (tileMap[x + (1 * direction)][y] == null && !(player.getStance() == 0 && (x + 1 * direction) == player.getX())) {
                    if (direction == -1) {
                        enemy.moveLeft();
                    } else if (direction == 1) {
                        enemy.moveRight();
                    }
                } else {
                    enemy.setDirection(direction * -1);
                }
            }
        }
        if (!enemy.isMoving() && tileMap[x][y + 1] == null) {
            enemy.moveDown();
        }
    }

    //public void updatePlayer() {
        //int x = player.getX();
        //int y = player.getY();
        //int direction = player.getDirection();
        //Tile[][] tileMap = map.getTileMap();
        //if (player.canMove()) {
            //if (player.wantsToMove() && tileMap[x][y + 1] != null) {
                //if (player.getStance() == 1 && tileMap[x + (1 * direction)][y] == null) {
                    //if (direction == -1) {
                        //player.moveLeft();
                    //} else if (direction == 1) {
                        //player.moveRight();
                    //}
                //}else if (player.getStance() == 2 && tileMap[x + (1 * direction)][y] == null && tileMap[x + (1 * direction)][y - 1] == null) {
                    //if (direction == -1) {
                        //player.moveLeft();
                    //} else if (direction == 1) {
                        //player.moveRight();
                    //}
                //} else if (player.getStance() == 2 && tileMap[x + (1 * direction)][y] != null && tileMap[x + (1 * direction)][y - 1] == null) {
                    //if (direction == -1) {
                        //player.moveLeft();
                        //player.moveUp();
                        //player.setStance(1);
                    //} else if (direction == 1) {
                        //player.moveRight();
                        //player.moveUp();
                        //player.setStance(1);
                    //}
                //}
            //}
        //}

        //if (!player.isMoving() && tileMap[x][y + 1] == null) {
            //player.setStance(1);//Check laters
            //player.moveDown();
        //}
    //}

    public void gameLoop() {
        while (true) {
            updateGame();
            player.update(map);
            render();
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Main loop: " + e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {}
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.buttonRight = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.buttonLeft = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.buttonUp = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.buttonDown = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.buttonRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.buttonLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.buttonDown = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.buttonUp = false;
        }
    }

    public static void main(String[]args) {
        Game game = new Game();
        game.gameLoop();
    }
}
