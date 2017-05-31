package com.snake.main;

import org.apache.log4j.PropertyConfigurator;

/**
 * Invoked through gradle.
 */
class Main {
	public static void main(String[] args) {
		PropertyConfigurator.configure(Configuration.getMainPath() + Configuration.getLog4jPath());

		Window window = new Window("SimpleSnakeGame");
		window.run();
		window.setVisible(true);
	}
}
