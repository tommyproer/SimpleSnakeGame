package com.snake.main;

import org.apache.log4j.PropertyConfigurator;

/**
 * Begins application, invoked through gradle.
 */
class Main {
	public static void main(String[] args) {
		PropertyConfigurator.configure(Configuration.getMainPath() + Configuration.getLog4jPath());

		final Window window = new Window("SimpleSnakeGame");
		window.run();
		window.setVisible(true);
	}
}
