package agh.cs.project2.backend;

import java.util.ArrayList;

class GameBoard {
    private ArrayList<ArrayList<FieldElement>> gameMap;

    GameBoard(int width, int height)
    {
        this.gameMap = new ArrayList<>(width);
        for(int i = 0; i < width; i++)
        {
            this.gameMap.add(new ArrayList<>(height));
            for(int j = 0; j < height; j++)
                this.gameMap.get(i).add(FieldElement.NOTHING);
        }
    }

    boolean isFull(Coordinates coords)
    {
        return this.getFieldAt(coords) != FieldElement.NOTHING;
    }

    void setField(Coordinates coords, FieldElement to)
    {
        this.gameMap.get(coords.x).set(coords.y, to);
    }

    FieldElement getFieldAt(Coordinates coords)
    {
        return this.gameMap.get(coords.x).get(coords.y);
    }
}
