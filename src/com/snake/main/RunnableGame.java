package com.snake.main;

import javax.swing.JOptionPane;

/**
 * Runs game.
 */
public class RunnableGame implements Runnable {
    private Window window;

    public RunnableGame() {
        window = new Window();
    }

    @Override
    public void run() {
        while (true) {
            boolean gameOver = false;
            while (!gameOver) {
                gameOver = window.iterate();
            }
            int score = window.getScore();
            int n = JOptionPane.showConfirmDialog(
                    window,
                    String.format("Game over. Your score is %d.\nWould you like to play again?", score),
                    "Game Over",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                window.dispose();
                window = new Window();
            } else {
                window.dispose();
                break;
            }
        }
    }
}
