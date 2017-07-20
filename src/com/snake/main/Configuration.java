package com.snake.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.ImmutableMap;
import com.snake.main.enums.GameDirection;

/**
 * Controls game configuration.
 */
public class Configuration {

    private static volatile Configuration configuration;

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    private final Properties properties;
    private static final String GAME_CFG_LOCATION = "cfg/game.cfg";
    private static final Map<String, String> immutableProperties = ImmutableMap.<String, String>builder()
            .put("initial_snake_size", "4")
            .build();

    private Configuration() {
        InputStream input = null;
        properties = new Properties();
        try {
            input = new FileInputStream(GAME_CFG_LOCATION);
            properties.load(input);
        } catch (IOException e) {
            System.out.println(String.format("File %s not found, using default settings", GAME_CFG_LOCATION));
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        properties.putAll(immutableProperties);
    }

    private int SPEED = 80;

    public int getSpeed() { return SPEED; }

    public void setSpeed(final int speed) {
        SPEED = speed;
    }

    public int getInitialSnakeSize() {
        return Integer.parseInt(properties.getProperty("initial_snake_size", "4"));
    }

    public GameDirection.Direction getInitialSnakeDirection() {
        String direction = properties.getProperty("initial_game_direction", "RIGHT");
        try {
            return GameDirection.Direction.valueOf(direction);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal direction: " + direction);
            return GameDirection.Direction.valueOf("RIGHT");
        }
    }

    public int getWindowHeight() { return Integer.parseInt(properties.getProperty("window_height", "930")); }

    public int getWindowWidth() { return Integer.parseInt(properties.getProperty("window_width", "850")); }

    public int getInitialSnakePosx() { return Integer.parseInt(properties.getProperty("grid_size", "20"))/2; }

    public int getInitialSnakePoxy() { return Integer.parseInt(properties.getProperty("grid_size", "20"))/2; }

    public int getGridSize() { return Integer.parseInt(properties.getProperty("grid_size", "20")); }

    public String getGameTitle() { return properties.getProperty("game_title", "Simple Snake Game"); }

    public String getThemeClipLocation() { return properties.getProperty("theme_music", "/music/themes/Mountain Downhill 1.wav"); }

    public boolean isMusicOn() {
        return Boolean.valueOf(properties.getProperty("sound", "true"));
    }

    public static String getGameOverClipLocation() { return "/music/GameOver.wav"; }

    public static String getSnakeBodyLocation() { return "/snake.png"; }

    public static String getSnakeHeadRightLocation() { return "/snake_head_right.png"; }

    public static String getSnakeTailLocation() { return "/snake_tail_right.png"; }

    public static String getFoodLocation() { return "/food.png"; }

    public static String getBgLocation() { return "/bg.png"; }

    public static String getCollisionLocation() { return "/collision.png"; }

    public static String getEatClipLocation() { return "/music/eat.wav"; }

    public static Map<String, Integer> getDifficultyMap() {
        return ImmutableMap.<String, Integer>builder()
                .put("Easy", 140)
                .put("Normal", 90)
                .put("Hard", 70)
                .put("Extreme", 50)
                .build();
    }
}
