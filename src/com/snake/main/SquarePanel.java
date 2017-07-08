package com.snake.main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

/**
 * TODO: Have multiple icons of possible snakes/animals
 */
public class SquarePanel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	public SquarePanel(final Image image){
		ImageIcon imageIcon = new ImageIcon(image);

		this.setIcon(imageIcon);
	}
	
	public void changeImage(final Image image) {
		ImageIcon imageIcon =
				new ImageIcon(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));

		this.setIcon(imageIcon);
	}
}

