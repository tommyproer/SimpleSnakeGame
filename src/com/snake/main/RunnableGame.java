package com.snake.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls game logic.
 */
public class RunnableGame implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(RunnableGame.class);

    private static int sizeSnake = Configuration.getInitialSnakeSize();
    public static GameDirection.Direction lastDirection;
    public static GameDirection.Direction direction;

    private Position headSnakePosition;
    private ArrayList<Position> snakePositions = new ArrayList<>();
    private Set<Position> allPositions = new HashSet<>();
    private Position foodPosition;

    public RunnableGame(final Position initialPosition) {
        direction = Configuration.getInitialSnakeDirection();
        lastDirection = Configuration.getInitialSnakeDirection();

        // Initialize all positions
        for (int i = 0; i < Window.windowSize; i++) {
            for (int j = 0; j < Window.windowSize; j++) {
                allPositions.add(new Position(i, j));
            }
        }

        headSnakePosition = new Position(initialPosition.getX(), initialPosition.getY());
        snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

        spawnFoodRandomly();
    }

    @Override
    public void run() {
        while(true) {
            moveSnake(direction);
            checkCollision();
            pause();
        }
    }

    /**
     * Pause between each snake move.
     */
    private void pause() {
        try {
            Thread.sleep(Configuration.getSpeed());
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
        LOG.info("Collision, game over. Snake windowSize: " + sizeSnake);
        System.out.println("Collision!");
        while(true) {
            pause();
        }
    }

    /**
     * Spawns food in a random location that is not occupied by the snake.
     */
    private void spawnFoodRandomly() {
        final List<Position> nonSnakePositions = new ArrayList<>(Sets.filter(allPositions, (input) -> !snakePositions.contains(input)));
        this.foodPosition = nonSnakePositions.get(((int) (Math.random()*1000)) % nonSnakePositions.size());

        LOG.info("New food spawn: {}, {}, snake windowSize: {}", foodPosition.getX(), foodPosition.getY(), sizeSnake);
        Window.gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(DataOfSquare.GameColor.FOOD);
    }

    /**
     * Moves the head of the snake, then deletes tail of snake.
     *
     * @param gameDirection direction of the snake.
     */
    private void moveSnake(final GameDirection.Direction gameDirection) {
        switch(gameDirection) {
            case RIGHT:
                headSnakePosition.moveRight();
                lastDirection = GameDirection.Direction.RIGHT;
                break;
            case LEFT:
                headSnakePosition.moveLeft();
                lastDirection = GameDirection.Direction.LEFT;
                break;
            case UP:
                headSnakePosition.moveUp();
                lastDirection = GameDirection.Direction.UP;
                break;
            case DOWN:
                headSnakePosition.moveDown();
                lastDirection = GameDirection.Direction.DOWN;
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
