package com.snake.main;

import javax.swing.JOptionPane;

/**
 * Runs game.
 */
public class RunnableGame implements Runnable {
    private Window window;

    private static boolean exitGame = false;

    public RunnableGame() {
        window = new Window();
    }

    public static void exitGame() {
        exitGame = true;
    }

    @Override
    public void run() {
        int n = JOptionPane.YES_OPTION;
        while (n == JOptionPane.YES_OPTION && !exitGame) {
            boolean gameOver = false;
            while (!gameOver) {
                gameOver = window.iterate();
            }

            n = JOptionPane.showConfirmDialog(
                    window,
                    String.format("Your score is %d.\n\nWould you like to play again?", window.getScore()),
                    "Game Over",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                window.reset();
            } else {
                window.dispose();
            }
        }
    }
}
