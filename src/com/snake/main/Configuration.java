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
 * Reads optional configuration from cfg/game.cfg file,
 * otherwise uses default configurations.
 */
public class Configuration {

    private static volatile Configuration configuration;

    /**
     * Enforce singleton pattern.
     */
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

    // Difficulties
    private static final String EASY = "Easy";
    private static final String NORMAL = "Normal";
    private static final String HARD = "Hard";
    private static final String EXTREME = "Extreme";

    /**
     * Loads up game properties from GAME_CFG_LOCATION.
     */
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

    private int SPEED = 80; // This is misleading, this is actually initialized through the game's difficulty.

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

    public boolean defaultPlaySoundEffects() {
        return Boolean.valueOf(properties.getProperty("sound_effects", "true"));
    }

    public boolean defaultPlayMusic() {
        return Boolean.valueOf(properties.getProperty("music", "true"));
    }

    public static String getDefaultThemeLocation() { return "/images/default"; }

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
                .put(EASY, 140)
                .put(NORMAL, 90)
                .put(HARD, 70)
                .put(EXTREME, 50)
                .build();
    }
}
