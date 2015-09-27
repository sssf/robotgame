package game;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import utils.Camera;
import actors.*;
import world.*;

import java.awt.image.BufferStrategy;
public class Game extends Canvas implements KeyListener {

    private int tileSize = 32;
    private Camera camera;
    int angle = 0;//Play time

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

        world.loadMap(tileSize, "test.txt");
        initGame();
        camera = new Camera((Player)world.getInstance().getPlayer(), gameWidth, gameHeight, gameWidth/2, 800, 0, 600);
    }
    public void initGame() {
        world.createActor(17, 4, tileSize, World.PLAYER);
        world.createActor(1,  1, tileSize, World.ENEMY);
        world.createActor(10,  1, tileSize, World.SMART_ENEMY);
    }

    int i = 0;
    public void render() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,gameWidth,gameHeight);
        //camera.update(g);
        g.rotate(Math.toRadians(angle), World.getInstance().getPlayer().getRealX(),World.getInstance().getPlayer().getRealY());
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
        if (e.getKeyCode() == KeyEvent.VK_PLUS) {}
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

        if (e.getKeyCode() == KeyEvent.VK_PLUS) {
            angle += 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            angle -= 1;
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
