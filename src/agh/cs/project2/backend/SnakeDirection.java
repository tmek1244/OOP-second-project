package agh.cs.project2.backend;

public enum SnakeDirection {
    TOP, RIGHT, DOWN, LEFT;

    public Coordinates directionToCoords()
    {
        if(this == TOP)
            return new Coordinates(0,-1);
        else if(this == RIGHT)
            return new Coordinates(1,0);
        else if(this == DOWN)
            return new Coordinates(0,1);
        else if(this == LEFT)
            return new Coordinates(-1,0);
        return null;
    }

    public boolean isNewDirectionValid(SnakeDirection newDirection)
    {
        return (this.ordinal() + newDirection.ordinal()) % 2 != 0;
    }
}
