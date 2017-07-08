package com.snake.main;

import java.awt.Image;

public class DataOfSquare {
	public enum GameColor {
		SNAKE, SNAKE_HEAD, SNAKE_TAIL, FOOD, BACKGROUND
	}

	private SquarePanel square;

	public DataOfSquare(final Image image){
		square = new SquarePanel(image);
	}
	public void lightSquare(final Image image){
		square.changeImage(image);
	}
	public SquarePanel getSquare() {
		return square;
	}
}
