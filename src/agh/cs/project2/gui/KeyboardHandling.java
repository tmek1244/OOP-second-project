package agh.cs.project2.gui;

import agh.cs.project2.backend.Game;
import agh.cs.project2.backend.SnakeDirection;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KeyboardHandling extends KeyAdapter{
    private Game game;
    private Visualization parent;
    private boolean shiftPressed = false;

    KeyboardHandling(Game game, Visualization parent)
    {
        this.game = game;
        this.parent = parent;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(!this.parent.timer.isRunning())
            this.parent.runGame();
        if(key == KeyEvent.VK_LEFT) {
            this.game.changeDirection(SnakeDirection.LEFT);
        }
        else if(key == KeyEvent.VK_RIGHT) {
            this.game.changeDirection(SnakeDirection.RIGHT);
        }
        else if (key == KeyEvent.VK_UP) {
            this.game.changeDirection(SnakeDirection.TOP);
        }
        else if(key == KeyEvent.VK_DOWN) {
            this.game.changeDirection(SnakeDirection.DOWN);
        }
        if(key == KeyEvent.VK_SHIFT && !this.shiftPressed)
        {
            this.parent.changeDelay(Math.max(100 - this.game.getSnake().size()/10, 50));
            this.shiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SHIFT && this.shiftPressed)
        {
            this.parent.changeDelay(Math.max(200 - this.game.getSnake().size()/20, 100));
            this.shiftPressed = false;
        }
    }
}