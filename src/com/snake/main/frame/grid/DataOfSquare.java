package com.snake.main.frame.grid;

import java.awt.Image;

public class DataOfSquare {

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
