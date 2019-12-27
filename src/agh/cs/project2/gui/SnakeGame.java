package agh.cs.project2.gui;

import agh.cs.project2.SettingsLoader;

import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame{
    private SnakeGame() {
        SettingsLoader.load();
        initUI();
    }

    private void initUI() {
        add(new Visualization());
        setResizable(false);
        setBackground(Color.BLACK);
        pack();

        setTitle("SNAKE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeGame();
            ex.setVisible(true);
        });
    }
}
