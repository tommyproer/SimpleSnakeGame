package com.snake.main.frame.grid;

import com.snake.main.frame.GameWindow;

/**
 * Position on the game grid.
 */
public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void moveLeft() { y = (y + GameWindow.getGridSize() - 1) % GameWindow.getGridSize(); }
	public void moveRight() {
		 y = (y+1) % GameWindow.getGridSize();
	}
	public void moveUp() { x = (x + GameWindow.getGridSize() - 1) % GameWindow.getGridSize(); }
	public void moveDown() {
		x = (x+1) % GameWindow.getGridSize();
	}

	public int getX(){
	  return x;
	}
	public int getY(){
	  return y;
	}

	@Override
	public String toString() {
		return String.format("[%d, %d]", x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		return other.getX() == x && other.getY() == y;
	}
} 