package com.snake.main;

/**
 * Starting point for SimpleSnakeGame application.
 */
class Main {
	public static void main(final String[] args) {
		final Thread thread = new Thread(
				new RunnableGame());

		thread.start();
	}
}
