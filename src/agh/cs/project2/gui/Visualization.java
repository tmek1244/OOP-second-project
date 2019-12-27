package agh.cs.project2.gui;

import agh.cs.project2.SettingsLoader;
import agh.cs.project2.backend.Coordinates;
import agh.cs.project2.backend.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Visualization extends JPanel implements ActionListener {

    private Image snake;
    private Image apple;
    private Image wall;
    private int mapSizeX;
    private int mapSizeY;
    private Game map;
    private ArrayList<Image> snakeHead;
    private KeyListener currentKeyListener = null;

    Timer timer;
    boolean hasGameBegun;

    private final int PERIOD_INTERVAL = SettingsLoader.periodInterval;


    private final int scale = SettingsLoader.scale;

    Visualization() {
        this.mapSizeX = SettingsLoader.width;
        this.mapSizeY = SettingsLoader.height;

        this.map = new Game(this.mapSizeX, this.mapSizeY);
        this.hasGameBegun = false;
        this.snakeHead = new ArrayList<>();

        initBoard();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawMap(g);
    }

    void runGame()
    {
        if(this.hasGameBegun) {
            this.hasGameBegun = false;
            this.map = new Game(this.mapSizeX, this.mapSizeY);
            this.prepareBoard();
            repaint();
        }
        else if(!this.timer.isRunning() && !this.hasGameBegun)
        {
            this.hasGameBegun = true;
            this.timer.start();
        }
    }

    private void initBoard() {
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(this.mapSizeX * this.scale, this.mapSizeY * this.scale));
        this.prepareBoard();
        loadImage();
        timer = new Timer(PERIOD_INTERVAL, this);
//        timer.start();
    }

    private void prepareBoard()
    {
        if(this.currentKeyListener != null)
            removeKeyListener(this.currentKeyListener);
        this.currentKeyListener = new KeyboardHandling(this.map, this);
        addKeyListener(this.currentKeyListener);
    }

    private void drawMap(Graphics g)
    {
        for(Coordinates coords: this.map.getWalls())
            this.drawOneImage(g, this.wall, coords);
        if(this.map.getAppleCoords() != null)
            this.drawOneImage(g, this.apple, this.map.getAppleCoords());
        int i = 0;
        for(Coordinates coords: this.map.getSnake())
        {
            if(i == 0)
            {
                this.drawOneImage(g, this.snakeHead.get(this.map.getDirection().ordinal()), coords);
                i = 1;
            }
            else
                this.drawOneImage(g, this.snake, coords);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawOneImage(Graphics g, Image image, Coordinates coordsForImage)
    {
        g.drawImage(image, this.scale * coordsForImage.x, this.scale * coordsForImage.y, this);
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("src/images/snake.png");
        this.snake = ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT);

        ii = new ImageIcon("src/images/snakeTOP.png");
        this.snakeHead.add(ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT));
        ii = new ImageIcon("src/images/snakeRIGHT.png");
        this.snakeHead.add(ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT));
        ii = new ImageIcon("src/images/snakeDOWN.png");
        this.snakeHead.add(ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT));
        ii = new ImageIcon("src/images/snakeLEFT.png");
        this.snakeHead.add(ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT));

        ii = new ImageIcon("src/images/apple.png");
        this.apple = ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT);
        ii = new ImageIcon("src/images/wall.png");
        this.wall = ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(this.timer.isRunning()){
            if (!this.map.makeMove()) {
                this.timer.stop();
            }
            this.map.spawnApple();
            repaint();
        }
    }
}
