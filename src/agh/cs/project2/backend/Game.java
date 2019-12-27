package agh.cs.project2.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Game {
    private Coordinates upperRightCorner;
    private Snake snake;
    private ArrayList<Coordinates> walls;
    private GameBoard gameBoard;
    private Coordinates appleCoords;
    private boolean isApple = false;

    private int howManyNormalApple = 0;

    public Game(int width, int height)
    {
        this.upperRightCorner = new Coordinates(width, height);
        this.gameBoard = new GameBoard(width, height);
        this.walls = new ArrayList<>();
        this.snake = new Snake(this.gameBoard, this);
        for(int i = 0; i < 10; i++) {
            Coordinates coords = new Coordinates(width / 2 + i, height / 2);
            this.gameBoard.setField(coords, FieldElement.SNAKE);
            this.snake.addToSnake(coords);
        }

        for(int i = 0; i < 15; i++)
        {
            Coordinates newWall = Coordinates.randomCoords(upperRightCorner);
            if(this.gameBoard.isEmpty(newWall))
            {
                this.gameBoard.setField(newWall, FieldElement.WALL);
                this.walls.add(newWall);
            }
        }
    }

    public boolean makeMove()
    {
        Coordinates nextPosition = this.snake.getHead().add(Objects.requireNonNull(this.snake.getDirection().directionToCoords()));
        nextPosition = nextPosition.modulo(this.upperRightCorner);
        if(cantMoveTo(nextPosition))
            return false;

        if(this.gameBoard.getFieldAt(nextPosition) == FieldElement.APPLE) {
            howManyNormalApple++;
            this.isApple = false;
            this.snake.eatApple();
        }

        this.snake.move(nextPosition);
        return true;
    }

    public FieldElement getFieldAt(Coordinates coords)
    {
        return this.gameBoard.getFieldAt(coords);
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
        } while(!this.gameBoard.isEmpty(positionForApple));
        this.gameBoard.setField(positionForApple, FieldElement.APPLE);
        this.appleCoords = positionForApple;
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

    public ArrayList<Coordinates> getWalls() {
        return walls;
    }

    public void addToWalls(Coordinates coords)
    {
        this.walls.add(coords);
    }

    private boolean cantMoveTo(Coordinates nextPosition)
    {
        return this.gameBoard.getFieldAt(nextPosition) == FieldElement.SNAKE
                || this.gameBoard.getFieldAt(nextPosition) == FieldElement.WALL;
    }
}
