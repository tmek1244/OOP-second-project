package agh.cs.project2.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Game {
    private Coordinates upperRightCorner;
    private Snake snake;
    private ArrayList<Coordinates> walls;
    private GameBoard gameBoard;
    private Coordinates appleCoords;
    private Coordinates goldenAppleCoords;
    private boolean isApple = false;
    private boolean isGoldenApple = false;
    private int ghost = 10;
    private int timerForGoldenApple = new Random().nextInt(50) + 50;
    private int timeToExpire;

    private int howManyNormalApple = 0;

    public Game(int width, int height)
    {
        this.upperRightCorner = new Coordinates(width, height);
        this.gameBoard = new GameBoard(width, height);
        this.walls = new ArrayList<>();
        this.snake = new Snake(this.gameBoard);

        this.spawnSnake();
        this.generateMap();
    }

    public boolean makeMove()
    {
        Coordinates nextPosition = this.snake.getHead().add(Objects.requireNonNull(this.snake.getDirection().directionToCoords())).modulo(this.upperRightCorner);

        if(ghost > 0)
            ghost--;
        else if(cannotMoveTo(nextPosition))
            return false;

        if(this.gameBoard.getFieldAt(nextPosition) == FieldElement.APPLE) {
            howManyNormalApple++;
            this.isApple = false;
            this.snake.eatApple();
        }

        if(this.isGoldenApple && this.gameBoard.getFieldAt(nextPosition) == FieldElement.GOLDEN_APPLE)
        {
            this.isGoldenApple = false;
            this.snake.eatApple(10);
        }

        this.snake.move(nextPosition);
        return true;
    }

    public void changeDirection(SnakeDirection newDirection)
    {
        this.snake.changeDirection(newDirection);
    }

    public void spawnApple()
    {
        if(this.isApple)
            return;
        this.isApple = true;
        Coordinates positionForApple;
        do {
            positionForApple = Coordinates.randomCoords(this.upperRightCorner);
        } while(this.gameBoard.isFull(positionForApple));
        this.gameBoard.setField(positionForApple, FieldElement.APPLE);
        this.appleCoords = positionForApple;
    }

    public void spawnGoldenApple()
    {
        if(this.isGoldenApple)
        {
            if(this.timeToExpire > 0)
                this.timeToExpire--;
            else
            {
                this.isGoldenApple = false;
                this.gameBoard.setField(this.goldenAppleCoords, FieldElement.NOTHING);
                return;
            }
            if(this.timerForGoldenApple == 0) this.timerForGoldenApple = new Random().nextInt(50) + 50;
            return;
        }

        if(this.timerForGoldenApple == 0)
        {
            this.isGoldenApple = true;
            Coordinates positionForApple;
            do {
                positionForApple = Coordinates.randomCoords(this.upperRightCorner);
            } while(this.gameBoard.isFull(positionForApple));
            this.gameBoard.setField(positionForApple, FieldElement.GOLDEN_APPLE);
            this.goldenAppleCoords = positionForApple;
            this.timeToExpire = 100;
        }
        else
        {
            this.timerForGoldenApple--;
        }
    }

    public LinkedList<Coordinates> getSnake() {
        return this.snake.getSnakeBody();
    }

    public SnakeDirection getDirection() {
        return this.snake.getDirection();
    }

    public Coordinates getAppleCoords() {
        return this.isApple ? this.appleCoords : null;
    }

    public Coordinates getGoldenAppleCoords()
    {
        return this.isGoldenApple ? this.goldenAppleCoords : null;
    }

    public ArrayList<Coordinates> getWalls() {
        return walls;
    }

    public int getHowManyNormalApple()
    {
        return this.howManyNormalApple;
    }

    private boolean cannotMoveTo(Coordinates nextPosition)
    {
        return this.gameBoard.getFieldAt(nextPosition) == FieldElement.SNAKE
                || this.gameBoard.getFieldAt(nextPosition) == FieldElement.WALL;
    }

    private void spawnSnake()
    {
        for(int i = 0; i < 10; i++) {
            Coordinates coords = new Coordinates(this.upperRightCorner.x / 2 + i, this.upperRightCorner.y / 2);
            this.gameBoard.setField(coords, FieldElement.SNAKE);
            this.snake.addToSnake(coords);
        }
    }

    private void generateMap()
    {
        double offsetX = new Random().nextDouble();
        double offsetY = new Random().nextDouble();

        for(int i = 0; i < this.upperRightCorner.x; i++)
        {
            for(int j = 0; j < this.upperRightCorner.y; j++)
            {
                if((SimplexNoise.noise((0.06*i + offsetX), (0.05*j + offsetY )) + 1) / 2 > 0.75)
                {
                    Coordinates newWall = new Coordinates(i, j);
                    this.gameBoard.setField(newWall, FieldElement.WALL);
                    this.walls.add(newWall);
                }
            }
        }
    }
}
