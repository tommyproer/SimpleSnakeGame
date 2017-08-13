package com.snake.main.controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.snake.main.Configuration;
import com.snake.main.frame.GameWindow;
import com.snake.main.util.ImageUtil;

/**
 * Initializes and stores the images used for the game.
 */
public class ImageController {

    private static volatile ImageController imageController;

    public static ImageController getInstance() {
        if (imageController == null) {
            imageController = new ImageController();
        }
        return imageController;
    }

    private static ImageUtil imageUtil = ImageUtil.getInstance();

    private BufferedImage bgImage;
    private BufferedImage food;
    private BufferedImage snakeHeadRight;
    private BufferedImage snakeHeadLeft;
    private BufferedImage snakeHeadDown;
    private BufferedImage snakeHeadUp;
    private BufferedImage snake;
    private BufferedImage snakeTail;
    private BufferedImage collision;

    private boolean isImagesInitialized = false;

    private ImageController() {}

    public void initializeImages(final String imageOption) {
        final String image_base = "/images/" + imageOption;
        try {
            bgImage = ImageIO.read(GameWindow.class.getResource(image_base + Configuration.getBgLocation()));
            food = ImageIO.read(GameWindow.class.getResource(image_base + Configuration.getFoodLocation()));
            snake = ImageIO.read(GameWindow.class.getResource(image_base + Configuration.getSnakeBodyLocation()));
            snakeTail = ImageIO.read(GameWindow.class.getResource(image_base + Configuration.getSnakeTailLocation()));
            collision = ImageIO.read(GameWindow.class.getResource(image_base + Configuration.getCollisionLocation()));
            snakeHeadRight = ImageIO.read(GameWindow.class.getResource(image_base + Configuration.getSnakeHeadRightLocation()));
            snakeHeadLeft = imageUtil.getFlippedImage(imageUtil.getRotatedImage(snakeHeadRight, 180));
            snakeHeadUp = imageUtil.getRotatedImage(snakeHeadRight, -90);
            snakeHeadDown = imageUtil.getRotatedImage(snakeHeadRight, 90);
            isImagesInitialized = true;
        } catch (Exception e) {
            System.out.println("Encountered Exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public BufferedImage getBgImage() { return bgImage; }
    public BufferedImage getFood() { return food; }
    public BufferedImage getSnakeHeadRight() { return snakeHeadRight; }
    public BufferedImage getSnakeHeadLeft() { return snakeHeadLeft; }
    public BufferedImage getSnakeHeadDown() { return snakeHeadDown; }
    public BufferedImage getSnakeHeadUp() { return snakeHeadUp; }
    public BufferedImage getSnake() { return snake; }
    public BufferedImage getSnakeTail() { return snakeTail; }
    public BufferedImage getCollision() { return collision; }
    public boolean isImagesInitialized() {
        return isImagesInitialized;
    }

    public void rescaleImages(double widthScale, double heightScale) {
        bgImage = imageUtil.getScaledImage(bgImage, widthScale, heightScale);
        food = imageUtil.getScaledImage(food, widthScale, heightScale);
        snake = imageUtil.getScaledImage(snake, widthScale, heightScale);
        snakeTail = imageUtil.getScaledImage(snakeTail, widthScale, heightScale);
        collision = imageUtil.getScaledImage(collision, widthScale, heightScale);
        snakeHeadRight = imageUtil.getScaledImage(snakeHeadRight, widthScale, heightScale);
        snakeHeadLeft = imageUtil.getScaledImage(snakeHeadLeft, widthScale, heightScale);
        snakeHeadDown = imageUtil.getScaledImage(snakeHeadDown, widthScale, heightScale);
        snakeHeadUp = imageUtil.getScaledImage(snakeHeadUp, widthScale, heightScale);
    }
}
