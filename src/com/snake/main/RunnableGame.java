package com.snake.main;

/**
 * Runs game.
 */
public class RunnableGame implements Runnable {
    private final Window window;

    public RunnableGame() {
        window = new Window();
        window.setVisible(true);
    }

    @Override
    public void run() {
        boolean gameOver = false;
        while(!gameOver) {
            gameOver = window.iterate();
        }
        window.dispose(); // Need to remove this later to go back to main menu
    }
}
