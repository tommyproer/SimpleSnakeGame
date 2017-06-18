package com.snake.main;

/**
 * TODO: We should be able to figure out the main path implicitly..
 * TODO: log4.properties file is not properly rotating files.
 * TODO: log4j.properties file should be able to get the main path from this file (i.e. have only one source of truth for this)
 */
public class Configuration {

    private static int SPEED = 150;
    private static final int INITIAL_SNAKE_SIZE = 3;
    private static final GameDirection.Direction INITIAL_SNAKE_DIRECTION = GameDirection.Direction.RIGHT;
    private static final String MAIN_PATH = "/home/tommy/workspace/SimpleSnakeGame";
    private static final String WINDOWS_MAIN_PATH = "C:\\Users\\Tommy\\TommysDocuments\\workspace\\SimpleSnakeGame";
    private static final String LOG_PATH = "/src/resources/log4j.properties";
    private static final String WINDOWS_LOG_PATH = "\\src\\resources\\log4j.properties";
    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 700;
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
        return MAIN_PATH;
    }

    public static String getLog4jPath() { return LOG_PATH; }

    public static int getWindowHeight() { return WINDOW_HEIGHT; }

    public static int getWindowWidth() { return WINDOW_WIDTH; }

    public static int getInitialSnakePosx() { return INITIAL_SNAKE_POSX; }

    public static int getInitialSnakePoxy() { return INITIAL_SNAKE_POXY; }
}
