package com.snake.main.controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.snake.main.Configuration;
import com.snake.main.frame.GameWindow;
import com.snake.main.util.ImageUtilSingleton;

/**
 * TODO: Right now the images are perfectly 42 by 42 pixels... will this always be true?
 * TODO: The answer is no, we have to find a way to scale
 */
public class ImageController {

    private static volatile ImageController imageController;

    private static final int SIZE = 42;

    public static ImageController getInstance() {
        if (imageController == null) {
            imageController = new ImageController();
        }
        return imageController;
    }

    private static ImageUtilSingleton imageUtil = ImageUtilSingleton.getInstance();

    private static BufferedImage bgImage;
    private static BufferedImage food;
    private static BufferedImage snakeHeadRight;
    private static BufferedImage snakeHeadLeft;
    private static BufferedImage snakeHeadDown;
    private static BufferedImage snakeHeadUp;
    private static BufferedImage snake;
    private static BufferedImage snakeTail;
    private static BufferedImage collision;

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
