package agh.cs.project2.backend;

import java.util.LinkedList;

public class Snake {
    private SnakeDirection direction;
    private LinkedList<Coordinates> snakeBody;
    private Integer howManyToAdd = 0;
    private GameBoard board;

    public Snake(GameBoard board)
    {
        this.board = board;
        this.direction = SnakeDirection.RIGHT;
        this.snakeBody = new LinkedList<>();
    }

    public void addToSnake(Coordinates coords)
    {
        this.snakeBody.addFirst(coords);
    }

    public Coordinates getHead()
    {
        return this.snakeBody.getFirst();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void move(Coordinates nextPosition)
    {
        this.snakeBody.addFirst(nextPosition);
        if(this.howManyToAdd == 0) {
            this.board.setField(this.snakeBody.getLast(), FieldElement.NOTHING);
            this.snakeBody.removeLast();
        }
        else
            this.howManyToAdd--;
        this.board.setField(this.getHead(), FieldElement.SNAKE);
    }

    public void eatApple()
    {
        this.howManyToAdd += 3;
    }

    public void eatApple(int value)
    {
        this.howManyToAdd += value;
    }

    public void changeDirection(SnakeDirection newDirection)
    {
        if(this.direction.isNewDirectionValid(newDirection))
            this.direction = newDirection;
    }

    public LinkedList<Coordinates> getSnakeBody()
    {
        return this.snakeBody;
    }
}
