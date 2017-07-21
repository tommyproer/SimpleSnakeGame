package com.snake.main.frame.grid;

import java.awt.Image;

import com.snake.main.frame.grid.SquarePanel;

public class DataOfSquare {
	public enum GameColor {
		SNAKE, SNAKE_HEAD, SNAKE_TAIL, FOOD, BACKGROUND
	}

	private SquarePanel square;

	public DataOfSquare(){
		square = new SquarePanel();
	}
	public void lightSquare(final Image image){
		square.changeImage(image);
	}

	public void resizeImage(int width, int height) {
		square.resizeSquare(width, height);
	}

	public SquarePanel getSquare() {
		return square;
	}
}
