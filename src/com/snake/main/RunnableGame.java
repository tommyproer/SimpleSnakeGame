package com.snake.main;

import javax.swing.JOptionPane;

import com.snake.main.frame.ScorePanel;
import com.snake.main.frame.Window;

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
            int highscore = window.getHighscore();

            boolean gameOver = false;
            while (!gameOver) {
                gameOver = window.iterate();
            }

            String message;
            if (window.getHighscore() > highscore) {
                message = String.format("Congrats, your new high score is %d!!\n\nWould you like to play again?", window.getHighscore());
            } else {
                message = String.format("Your score is %d.\nHighscore is %d.\nWould you like to play again?", ScorePanel.getInstance().getScore(), window.getHighscore());
            }

            n = JOptionPane.showConfirmDialog(
                    window,
                    message,
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
