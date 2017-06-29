package com.snake.main;

/**
 * Begins application, invoked through gradle.
 */
class Main {
	public static void main(String[] args) {

		final Window window = new Window("SimpleSnakeGame");
		window.run();
		window.setVisible(true);
	}
}
