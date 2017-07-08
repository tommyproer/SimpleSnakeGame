package com.snake.main;

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
        boolean gameOver = false;
        while (!gameOver) {
            gameOver = window.iterate();
        }
        window.dispose(); // Need to remove this later to go back to main menu
//        window = new Window();
//        gameOver = false;
//        while (!gameOver) {
//            gameOver = window.iterate();
//        }
//        window.dispose();
    }
}
