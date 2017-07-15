package com.snake.main;

import com.snake.main.controller.GameDirection;

/**
 * Controls game configuration.
 */
public class Configuration {

    private static final String GAME_TITLE = "Simple Snake Game";
    private static int SPEED = 40; // TODO: Implement difficulty level (put in menu?) to adjust speed
    private static final int INITIAL_SNAKE_SIZE = 3;
    private static final GameDirection.Direction INITIAL_SNAKE_DIRECTION = GameDirection.Direction.RIGHT;
    private static final int WINDOW_HEIGHT = 930;
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

    public static String getSnakeBodyLocation() { return "/snake.png"; }

    public static String getSnakeHeadRightLocation() { return "/snake_head_right.png"; }

    public static String getSnakeTailLocation() { return "/snake_tail_right.png"; }

    public static String getFoodLocation() { return "/food.png"; }

    public static String getBgLocation() { return "/bg.png"; }

    public static String getCollisionLocation() { return "/collision.png"; }

    public static String getEatClipLocation() { return "/music/eat.wav"; }

    public static String getThemeClipLocation() { return "/music/themes/Mountain Downhill 1.wav"; }

    public static String getGameOverClipLocation() { return "/music/GameOver.wav"; }
}
