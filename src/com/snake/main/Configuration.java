package com.snake.main;

import java.io.File;

/**
 * TODO: log4.properties file is not properly rotating files.
 */
public class Configuration {

    private static int SPEED = 80;
    private static final int INITIAL_SNAKE_SIZE = 3;
    private static final GameDirection.Direction INITIAL_SNAKE_DIRECTION = GameDirection.Direction.RIGHT;
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 600;
    private static final int INITIAL_SNAKE_POSX = 10;
    private static final int INITIAL_SNAKE_POXY = 10;

    public static int getSpeed() { return SPEED; }

    public static int getInitialSnakeSize() {
        return INITIAL_SNAKE_SIZE;
    }

    public static GameDirection.Direction getInitialSnakeDirection() {
        return INITIAL_SNAKE_DIRECTION;
    }

    public static String getMainPath() {
        return System.getProperty("user.dir");
    }

    public static String getLog4jPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(File.separator);
        sb.append("src");
        sb.append(File.separator);
        sb.append("resources");
        sb.append(File.separator);
        sb.append("log4j.properties");
        return sb.toString();
    }

    public static int getWindowHeight() { return WINDOW_HEIGHT; }

    public static int getWindowWidth() { return WINDOW_WIDTH; }

    public static int getInitialSnakePosx() { return INITIAL_SNAKE_POSX; }

    public static int getInitialSnakePoxy() { return INITIAL_SNAKE_POXY; }
}
