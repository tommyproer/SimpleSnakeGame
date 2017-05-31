package com.snake.main;

public class Configuration {

    private static int SPEED = 80;
    private static final int INITIAL_SNAKE_SIZE = 3;
    private static final RunnableGame.GameDirection INITIAL_SNAKE_DIRECTION = RunnableGame.GameDirection.RIGHT;
    private static final String MAIN_PATH = "C:\\Users\\Tommy\\TommysDocuments\\workspace\\SimpleSnakeGame";
    private static final String LOG_PATH = "\\src\\resources\\log4j.properties";
    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 700;
    private static final int INITIAL_SNAKE_POSX = 10;
    private static final int INITIAL_SNAKE_POXY = 10;

    public static int getSpeed() { return SPEED; }

    public static int getInitialSnakeSize() {
        return INITIAL_SNAKE_SIZE;
    }

    public static RunnableGame.GameDirection getInitialSnakeDirection() {
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
