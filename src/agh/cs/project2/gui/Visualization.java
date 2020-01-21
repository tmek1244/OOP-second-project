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
    private Image goldenApple;
    private Image wall;
    private int mapSizeX;
    private int mapSizeY;
    private Game map;
    private ArrayList<Image> snakeHead;
    private KeyListener currentKeyListener = null;
    private int previousResult = 10;

    Timer timer;
    Boolean hasGameBegun;

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
        if(!this.hasGameBegun && previousResult != 10) {
            drawMap(g);
            drawFinalMessage(g);
        }
        else
            drawGame(g);
    }

    void runGame()
    {
        if(this.hasGameBegun) {
            this.hasGameBegun = false;
            previousResult = this.map.getSnake().size();
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
        loadImages();
        timer = new Timer(PERIOD_INTERVAL, this);
    }

    private void prepareBoard()
    {
        if(this.currentKeyListener != null)
            removeKeyListener(this.currentKeyListener);
        this.currentKeyListener = new KeyboardHandling(this.map, this);
        addKeyListener(this.currentKeyListener);
    }

    private void drawGame(Graphics g)
    {
        this.drawMap(g);
        this.drawSnakeLength(g);
    }

    private void drawMap(Graphics g)
    {
        for(Coordinates coords: this.map.getWalls())
            this.drawOneImage(g, this.wall, coords);

        this.drawOneImage(g, this.goldenApple, this.map.getGoldenAppleCoords());
        this.drawOneImage(g, this.apple, this.map.getAppleCoords());

        int i = 0;
        for(Coordinates coords: this.map.getSnake())
        {
            if((i++) == 0)
                this.drawOneImage(g, this.snakeHead.get(this.map.getDirection().ordinal()), coords);
            else
                this.drawOneImage(g, this.snake, coords);
        }
        //Toolkit.getDefaultToolkit().sync();
    }

    private void drawSnakeLength(Graphics g)
    {
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        g.setColor(Color.red);
        g.drawString("Snake length: " + this.map.getSnake().size(), 0, 30);
    }

    private void drawFinalMessage(Graphics g)
    {
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

        g.setColor(Color.GREEN);
        g.drawString("Congratulation! You had scored : " + this.previousResult,
                this.mapSizeX * this.scale / 2 - 200, this.mapSizeY * this.scale / 2);
    }

    private void drawOneImage(Graphics g, Image image, Coordinates coordsForImage)
    {
        if(coordsForImage != null)
            g.drawImage(image, this.scale * coordsForImage.x, this.scale * coordsForImage.y, this);
    }

    private void loadImages()
    {
        this.snake = this.getSingleImage("src/images/snake.png");

        this.snakeHead.add(this.getSingleImage("src/images/snakeTOP.png"));
        this.snakeHead.add(this.getSingleImage("src/images/snakeRIGHT.png"));
        this.snakeHead.add(this.getSingleImage("src/images/snakeDOWN.png"));
        this.snakeHead.add(this.getSingleImage("src/images/snakeLEFT.png"));

        this.apple = this.getSingleImage("src/images/apple.png");
        this.goldenApple = this.getSingleImage("src/images/goldenApple.png");

        this.wall = this.getSingleImage("src/images/wall.png");
    }

    private Image getSingleImage(String filename)
    {
        ImageIcon ii = new ImageIcon(filename);
        return ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(this.timer.isRunning()){
            if (!this.map.makeMove()) {
                this.timer.stop();
            }
            this.map.spawnApple();
            this.map.spawnGoldenApple();
            repaint();
        }
    }

    void changeDelay(int delay)
    {
        this.timer.stop();
        this.timer.setDelay(delay);
        this.timer.start();
    }
}
