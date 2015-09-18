package game;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import actors.*;
import world.*;
//import actors.Enemy;

import java.awt.image.BufferStrategy;
public class Game extends Canvas implements KeyListener {

    private static int tileSize = 32;
    //world map = new Map(50, 50, tileSize);

    World world = World.createWorld();
    //Player player;
    //SmartEnemy enemy;

    BufferStrategy strategy;

    final int gameWidth = 800;
    final int gameHeight = 600;

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
        world.initWorld(50, 50, tileSize);
        initGame();
    }
    public void initGame() {
        initMap();
        world.createActor(15, 1, tileSize, World.PLAYER);
        world.createActor(9, 1, tileSize, World.ENEMY);
    }
    public void initMap() {
        world.setTile(0, 0, new Tile(tileSize));
        world.setTile(0, 1, new Tile(tileSize));
        world.setTile(0, 2, new Tile(tileSize));
        world.setTile(0, 3, new Tile(tileSize));
        world.setTile(0, 4, new Tile(tileSize));
        world.setTile(1, 5, new Tile(tileSize));
        world.setTile(2, 6, new Tile(tileSize));
        world.setTile(3, 7, new Tile(tileSize));
        world.setTile(4, 8, new Tile(tileSize));
        world.setTile(5, 9, new Tile(tileSize));
        world.setTile(5, 9, new Tile(tileSize));
        world.setTile(5, 9, new Tile(tileSize));
        world.setTile(6, 9, new Tile(tileSize));
        world.setTile(7, 9, new Tile(tileSize));
        world.setTile(8, 10, new Tile(tileSize));
        //map.setTile(8, 8, new Tile(tileSize));
        world.setTile(9, 9, new Tile(tileSize));
        world.setTile(10, 9, new Tile(tileSize));
        world.setTile(11, 9, new Tile(tileSize));
        world.setTile(12, 9, new Tile(tileSize));
        world.setTile(13, 8, new Tile(tileSize));
        world.setTile(14, 8, new Tile(tileSize));
        world.setTile(15, 8, new Tile(tileSize));
        world.setTile(16, 8, new Tile(tileSize));
        world.setTile(16, 7, new Tile(tileSize));
        world.setTile(17, 8, new Tile(tileSize));
        world.setTile(18, 9, new Tile(tileSize));
        world.setTile(19, 7, new Tile(tileSize));
        world.setTile(19, 8, new Tile(tileSize));
        world.setTile(17, 9, new Tile(tileSize));
        world.setTile(19, 9, new Tile(tileSize));
    }

    double x1 = 150;
    double y1 = 150;

    double x2 = 50;
    double y2 = 100;
    int i = 1;
    public void render() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        //g.rotate(Math.toRadians(i++), 400, 300);
        g.setColor(Color.black);
        g.fillRect(0,0,gameWidth,gameHeight);
        //g.fillRect(-400,-400,gameWidth*2,gameHeight*2);
        drawWorld(g);
        g.setColor(Color.green);
        double angle =-0.05;

        double vx = x2 - x1;
        double vy = y2 - y1;
        vx = vx * Math.cos(angle) - vy * Math.sin(angle);
        vy = vx * Math.sin(angle) + vy * Math.cos(angle);

        x2 = x1 + vx;
        y2 = y1 + vy;

        g.draw(new Line2D.Double(x1, y1, x2, y2));
        g.dispose();
        strategy.show();
    }

    public void drawWorld(Graphics2D g) {
        world.draw(g);
    }

    public void updateGame() {
        //enemy.update();
        //player.update();
        world.update();
    }

    public void gameLoop() {
        while (true) {
            updateGame();
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
        Player player = (Player)world.getPlayer();
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
        Player player = (Player)world.getPlayer();
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
