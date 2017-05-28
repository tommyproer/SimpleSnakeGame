package com.snake.main;

public class Configuration {

    private static int SPEED = 70;
    private static final int INITIAL_SNAKE_SIZE = 3;
    private static final String MAIN_PATH = "C:\\Users\\Tommy\\Documents\\workspace\\SimpleSnakeGame";

    public static int getSpeed() {
        return SPEED;
    }

    public static int getInitialSnakeSize() {
        return INITIAL_SNAKE_SIZE;
    }

    public static int getInitialSnakeDirection() {
        return 4;
    }

    public static String getMainPath() {
        return MAIN_PATH;
    }
}
