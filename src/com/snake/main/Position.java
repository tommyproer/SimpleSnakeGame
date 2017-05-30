package com.snake.main;

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
	public void changeData(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
	  return x;
	}
	public int getY(){
	  return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		return other.getX() == x && other.getY() == y;
	}

	private void validatePosition(final int x, final int y) {
		System.out.println("TODO: validate positions within grid here");
	}
} 