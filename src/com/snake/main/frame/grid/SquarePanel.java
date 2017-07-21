package com.snake.main.frame.grid;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

import com.snake.main.controller.ImageController;
import com.snake.main.frame.Window;

/**
 * TODO: Have multiple icons of possible snakes/animals
 */
public class SquarePanel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	private Image image;
	
	public void changeImage(final Image image) {
		this.image = image;

		if (getWidth() != 0 || getHeight() != 0) {
			if (getWidth() != image.getWidth(null) || getHeight() != image.getHeight(null)) {
				ImageController.getInstance().rescaleImages((double) getWidth()/image.getWidth(null),
						(double) getHeight()/image.getHeight(null));
				Window.rescaleAllSquares(getWidth(), getHeight());
			}
		}

		ImageIcon imageIcon =
				new ImageIcon(this.image);

		this.setIcon(imageIcon);

	}

	public void resizeSquare(int width, int height) {
		image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		this.setIcon(new ImageIcon(image));
	}
}

