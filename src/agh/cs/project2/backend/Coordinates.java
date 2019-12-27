package agh.cs.project2.backend;

import java.util.Random;

public class Coordinates {
    public final int x;
    public final int y;

    Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    Coordinates add(Coordinates other)
    {
        return new Coordinates(this.x + other.x, this.y + other.y);
    }

    Coordinates modulo(Coordinates other)
    {
        return new Coordinates((this.x + other.x) % other.x, (this.y + other.y) % other.y);
    }
    @Override
    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

    static Coordinates randomCoords(Coordinates limits)
    {
        return new Coordinates(new Random().nextInt(limits.x), new Random().nextInt(limits.y));
    }
}
