package com.snake.main;

import javax.swing.JOptionPane;

import com.snake.main.frame.GameWindow;
import com.snake.main.frame.ScorePanel;

/**
 * Runs Simple Snake Game.
 */
public class RunnableGame implements Runnable {
    private GameWindow gameWindow;

    private static boolean exitGame = false;

    public RunnableGame() {
        gameWindow = new GameWindow();
    }

    /**
     * Called when we want to exit the game
     */
    public static void exitGame() {
        exitGame = true;
    }

    /**
     * Run the game
     */
    @Override
    public void run() {
        int n = JOptionPane.YES_OPTION;
        while (n == JOptionPane.YES_OPTION && !exitGame) {
            int highscore = gameWindow.getHighscore();

            boolean gameOver = false;
            while (!gameOver) {
                gameOver = gameWindow.iterate();
            }

            n = showGameOverDialog(highscore);

            if (n == JOptionPane.YES_OPTION) {
                gameWindow.reset();
            } else {
                gameWindow.dispose();
            }
        }
    }

    private int showGameOverDialog(final int highscore) {
        return JOptionPane.showConfirmDialog(
                gameWindow,
                getHighscoreMessage(highscore),
                "Game Over",
                JOptionPane.YES_NO_OPTION);
    }

    private String getHighscoreMessage(final int highscore) {
        if (gameWindow.getHighscore() > highscore) {
            return String.format("Congrats, your new high score is %d!!\n\nWould you like to play again?", gameWindow.getHighscore());
        }
        return String.format("Your score is %d.\nHighscore is %d.\nWould you like to play again?", ScorePanel.getInstance().getScore(), gameWindow.getHighscore());
    }


}
