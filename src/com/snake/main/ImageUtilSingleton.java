package com.snake.main;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageUtilSingleton {

    public BufferedImage getRotatedImage(BufferedImage bufferedImage, double angle) {

        AffineTransform r = new AffineTransform();
        r.rotate(Math.toRadians(angle), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);

        // To rotate an image and save it you have to create a new BufferedImage...
        BufferedImage target = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        target.createGraphics().drawImage(bufferedImage, r, null);
        return target;
    }

    public BufferedImage getFlippedImage(BufferedImage bufferedImage) {
        AffineTransform r = AffineTransform.getScaleInstance(1, -1);
        r.translate(0, -bufferedImage.getHeight());
        AffineTransformOp op = new AffineTransformOp(r, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bufferedImage, null);
    }
}
