package com.snake.main;

/**
 * Controls game configuration.
 */
public class Configuration {

    private static final String GAME_TITLE = "Simple Snake Game";
    private static int SPEED = 40; // TODO: Implement difficulty level (put in menu?) to adjust speed
    private static final int INITIAL_SNAKE_SIZE = 3;
    private static final GameDirection.Direction INITIAL_SNAKE_DIRECTION = GameDirection.Direction.RIGHT;
    private static final int WINDOW_HEIGHT = 850;
    private static final int WINDOW_WIDTH = 850;
    private static final int gridSize = 20;
    private static final int INITIAL_SNAKE_POSX = gridSize /2;
    private static final int INITIAL_SNAKE_POXY = gridSize /2;

    public static int getSpeed() { return SPEED; }

    public static void setSpeed(final int speed) {
        SPEED = speed;
    }

    public static int getInitialSnakeSize() {
        return INITIAL_SNAKE_SIZE;
    }

    public static GameDirection.Direction getInitialSnakeDirection() {
        return INITIAL_SNAKE_DIRECTION;
    }

    public static int getWindowHeight() { return WINDOW_HEIGHT; }

    public static int getWindowWidth() { return WINDOW_WIDTH; }

    public static int getInitialSnakePosx() { return INITIAL_SNAKE_POSX; }

    public static int getInitialSnakePoxy() { return INITIAL_SNAKE_POXY; }

    public static int getGridSize() { return gridSize; }

    public static String getGameTitle() { return GAME_TITLE; }

    public static String getSnakeBodyLocation() { return "\\src\\resources\\snake.png"; }

    public static String getSnakeHeadRightLocation() { return "\\src\\resources\\snake_head_right.png"; }

    public static String getSnakeHeadLeftLocation() { return "\\src\\resources\\snake_head_left.png"; }

    public static String getSnakeHeadUpLocation() { return "\\src\\resources\\snake_head_up.png"; }

    public static String getSnakeHeadDownLocation() { return "\\src\\resources\\snake_head_down.png"; }

    public static String getSnakeTailLocation() { return "\\src\\resources\\snake_tail.png"; }

    public static String getFoodLocation() { return "\\src\\resources\\food.png"; }

    public static String getBgLocation() { return "\\src\\resources\\bg.png"; }

    public static String getCollisionLocation() { return "\\src\\resources\\collision.png"; }
}
