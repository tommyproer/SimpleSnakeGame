package com.snake.main;

import java.awt.Color;

public class DataOfSquare {
	public enum GameColor {
		SNAKE, FOOD, BACKGROUND
	}

	private SquarePanel square;

	public DataOfSquare(final GameColor gameColor){
		square = new SquarePanel(convertToColor(gameColor));
	}
	public void lightMeUp(final GameColor gameColor){
		square.changeColor(convertToColor(gameColor));
	}

	public SquarePanel getSquare() {
		return square;
	}

	private Color convertToColor(GameColor gameColor) {
		switch (gameColor) {
			case SNAKE:
				return Color.DARK_GRAY;
			case FOOD:
				return Color.BLUE;
			case BACKGROUND:
			default:
				return Color.WHITE;
		}
	}
}
