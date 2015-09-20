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
        world.createActor(17, 4, tileSize, World.PLAYER);
        world.createActor(11, 1, tileSize, World.SMART_ENEMY);
        world.createActor(1, 1, tileSize, World.ENEMY);
    }
    public void initMap() {//I can has tile map editor please?
        //Left wall
        world.setTile(0, 0, new Tile(tileSize));
        world.setTile(0, 1, new Tile(tileSize));
        world.setTile(0, 2, new Tile(tileSize));
        world.setTile(0, 3, new Tile(tileSize));
        world.setTile(0, 4, new Tile(tileSize));
        world.setTile(0, 5, new Tile(tileSize));
        world.setTile(0, 6, new Tile(tileSize));
        world.setTile(0, 7, new Tile(tileSize));
        world.setTile(0, 8, new Tile(tileSize));
        world.setTile(0, 9, new Tile(tileSize));
        world.setTile(0, 10, new Tile(tileSize));
        world.setTile(0, 11, new Tile(tileSize));
        world.setTile(0, 12, new Tile(tileSize));
        world.setTile(0, 13, new Tile(tileSize));
        world.setTile(0, 14, new Tile(tileSize));
        world.setTile(0, 15, new Tile(tileSize));

        //Upper floor left
        world.setTile(1, 5, new Tile(tileSize));
        world.setTile(2, 5, new Tile(tileSize));
        world.setTile(3, 5, new Tile(tileSize));
        world.setTile(4, 5, new Tile(tileSize));
        world.setTile(5, 5, new Tile(tileSize));
        world.setTile(6, 5, new Tile(tileSize));
        world.setTile(7, 5, new Tile(tileSize));
        world.setTile(8, 5, new Tile(tileSize));
        world.setTile(9, 5, new Tile(tileSize));
        world.setTile(10, 5, new Tile(tileSize));
        world.setTile(11, 5, new Tile(tileSize));

        //Upper floor right
        world.setTile(13, 5, new Tile(tileSize));
        world.setTile(14, 5, new Tile(tileSize));
        world.setTile(15, 5, new Tile(tileSize));
        world.setTile(16, 5, new Tile(tileSize));
        world.setTile(17, 5, new Tile(tileSize));
        world.setTile(18, 5, new Tile(tileSize));

        //Stair
        world.setTile(12, 8, new Tile(tileSize));
        world.setTile(13, 7, new Tile(tileSize));
        world.setTile(13, 8, new Tile(tileSize));
        world.setTile(14, 8, new Tile(tileSize));
        world.setTile(11, 9, new Tile(tileSize));
        world.setTile(12, 9, new Tile(tileSize));
        world.setTile(13, 9, new Tile(tileSize));
        world.setTile(14, 9, new Tile(tileSize));
        world.setTile(15, 9, new Tile(tileSize));

        //middle ceiling right
        world.setTile(15, 6, new Tile(tileSize));
        world.setTile(16, 6, new Tile(tileSize));
        world.setTile(17, 6, new Tile(tileSize));
        world.setTile(18, 6, new Tile(tileSize));

        //middle ceiling left
        world.setTile(7, 6, new Tile(tileSize));
        world.setTile(8, 6, new Tile(tileSize));
        world.setTile(9, 6, new Tile(tileSize));
        world.setTile(10, 6, new Tile(tileSize));
        world.setTile(11, 6, new Tile(tileSize));
        world.setTile(7, 7, new Tile(tileSize));
        world.setTile(8, 7, new Tile(tileSize));
        world.setTile(9, 7, new Tile(tileSize));
        world.setTile(10, 7, new Tile(tileSize));
        world.setTile(2, 7, new Tile(tileSize));
        world.setTile(3, 7, new Tile(tileSize));
        world.setTile(4, 7, new Tile(tileSize));
        world.setTile(5, 7, new Tile(tileSize));
        world.setTile(6, 7, new Tile(tileSize));
        world.setTile(8, 8, new Tile(tileSize));
        world.setTile(9, 8, new Tile(tileSize));

        //middle floor right
        world.setTile(16, 9, new Tile(tileSize));
        world.setTile(17, 9, new Tile(tileSize));
        world.setTile(18, 9, new Tile(tileSize));
        world.setTile(19, 9, new Tile(tileSize));
        world.setTile(20, 9, new Tile(tileSize));

        //Right wall
        world.setTile(20, 2, new Tile(tileSize));
        world.setTile(20, 3, new Tile(tileSize));
        world.setTile(20, 4, new Tile(tileSize));
        world.setTile(20, 5, new Tile(tileSize));
        world.setTile(20, 6, new Tile(tileSize));
        world.setTile(20, 7, new Tile(tileSize));
        world.setTile(20, 8, new Tile(tileSize));

        //upper ceiling
        world.setTile(16, 4, new Tile(tileSize));
        world.setTile(16, 3, new Tile(tileSize));
        world.setTile(16, 2, new Tile(tileSize));
        world.setTile(17, 2, new Tile(tileSize));
        world.setTile(18, 2, new Tile(tileSize));
        world.setTile(19, 2, new Tile(tileSize));
        world.setTile(20, 2, new Tile(tileSize));


        //middle floor left
        world.setTile(2, 11, new Tile(tileSize));
        world.setTile(2, 12, new Tile(tileSize));
        world.setTile(2, 13, new Tile(tileSize));
        world.setTile(2, 10, new Tile(tileSize));
        world.setTile(3, 10, new Tile(tileSize));
        world.setTile(4, 10, new Tile(tileSize));
        world.setTile(5, 10, new Tile(tileSize));
        world.setTile(7, 10, new Tile(tileSize));
        world.setTile(8, 10, new Tile(tileSize));
        world.setTile(9, 10, new Tile(tileSize));
        world.setTile(10, 10, new Tile(tileSize));

        world.setTile(5, 11, new Tile(tileSize));
        world.setTile(5, 12, new Tile(tileSize));
        world.setTile(6, 12, new Tile(tileSize));
        world.setTile(7, 12, new Tile(tileSize));
        world.setTile(7, 11, new Tile(tileSize));

        //lower ceiling left
        world.setTile(3, 11, new Tile(tileSize));
        world.setTile(4, 11, new Tile(tileSize));
        world.setTile(3, 12, new Tile(tileSize));
        world.setTile(3, 13, new Tile(tileSize));
        world.setTile(5, 13, new Tile(tileSize));
        world.setTile(6, 13, new Tile(tileSize));

        //lower floor left
        world.setTile(1, 15, new Tile(tileSize));
        world.setTile(2, 15, new Tile(tileSize));
        world.setTile(3, 15, new Tile(tileSize));
        world.setTile(4, 15, new Tile(tileSize));
        world.setTile(5, 15, new Tile(tileSize));
        world.setTile(6, 15, new Tile(tileSize));
        world.setTile(7, 15, new Tile(tileSize));
        world.setTile(8, 15, new Tile(tileSize));

        world.setTile(11, 15, new Tile(tileSize));
        world.setTile(12, 15, new Tile(tileSize));
        world.setTile(13, 15, new Tile(tileSize));
        world.setTile(14, 15, new Tile(tileSize));
        world.setTile(15, 15, new Tile(tileSize));
        world.setTile(16, 15, new Tile(tileSize));
        world.setTile(17, 15, new Tile(tileSize));
        world.setTile(18, 15, new Tile(tileSize));
        world.setTile(19, 15, new Tile(tileSize));
}

    public void render() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,gameWidth,gameHeight);
        drawWorld(g);
        g.dispose();
        strategy.show();
    }

    public void drawWorld(Graphics2D g) {
        world.draw(g);
    }

    public void updateGame() {
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
