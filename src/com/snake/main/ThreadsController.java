package com.snake.main;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls game logic.
 * TODO: Bug with user pressing two directions in quick succession may lead to false collision
 */
public class ThreadsController extends Thread {
    private final Logger LOG = LoggerFactory.getLogger(ThreadsController.class);

    public enum GameDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private static int sizeSnake = Configuration.getInitialSnakeSize();
    public static GameDirection gameDirection;

    private Position headSnakePosition;
    private ArrayList<Position> snakePositions = new ArrayList<>();
    private Position foodPosition;
	 
    // Constructor of Controller Thread
    ThreadsController(final Position initialPosition) {
        gameDirection = Configuration.getInitialSnakeDirection();

        headSnakePosition = new Position(initialPosition.getX(), initialPosition.getY());
        snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

        spawnFoodRandomly();
    }

    public void run() {
        while(true) {
            moveSnake(gameDirection);
            checkCollision();
            pause();
        }
    }

    /**
    * Pause between each snake move.
    */
    private void pause() {
        try {
            sleep(Configuration.getSpeed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the position of the head of the snake and
     * if it is equal to the position of any other part of the snake
     * there is a collision.
     */
     private void checkCollision() {
         snakePositions.subList(0, snakePositions.size()-1)
                 .stream()
                 .filter(headSnakePosition::equals)
                 .findAny()
                 .ifPresent((consumer) -> stopTheGame());

         if (headSnakePosition.equals(foodPosition)) {
             sizeSnake = sizeSnake + 1;
             spawnFoodRandomly();
         }
     }

     private void stopTheGame() {
         LOG.info("Collision, game over. Snake size: " + sizeSnake);
         while(true){
             pause();
         }
     }

    /**
     * TODO: This needs to be more efficient
     */
     private void spawnFoodRandomly() {
         Position newFoodPosition = new Position((int) (Math.random()*20), (int) (Math.random()*20));
         while (snakePositions.contains(newFoodPosition)) {
             newFoodPosition = new Position((int) (Math.random()*20), (int) (Math.random()*20));
         }
         this.foodPosition = newFoodPosition;

         LOG.info("New food spawn: {}, {}, snake size: {}", foodPosition.getX(), foodPosition.getY(), sizeSnake);
         Window.gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(DataOfSquare.GameColor.FOOD);
     }

    /**
     * Moves the head of the snake, then deletes tail of snake.
     *
     * @param gameDirection direction of the snake.
     */
    private void moveSnake(final GameDirection gameDirection) {
        switch(gameDirection) {
            case RIGHT:
                headSnakePosition.moveRight();
                break;
            case LEFT:
                headSnakePosition.moveLeft();
                break;
            case UP:
                headSnakePosition.moveUp();
                break;
            case DOWN:
                headSnakePosition.moveDown();
                break;
        }
        snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

        while (snakePositions.size() > sizeSnake) {
            Position tail = snakePositions.get(0);
            Window.gameGrid.get(tail.getX()).get(tail.getY()).lightSquare(DataOfSquare.GameColor.BACKGROUND);
            snakePositions.remove(0);
        }

        Window.gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(DataOfSquare.GameColor.SNAKE);
    }
}
