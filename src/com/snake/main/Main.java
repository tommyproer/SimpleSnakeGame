package com.snake.main;

/**
 * Begins application, invoked through gradle.
 */
class Main {
	public static void main(String[] args) {
		final Thread thread = new Thread(
				new RunnableGame());

		try {
			thread.start();
		} catch (Exception e) {
			System.out.println("Encountered error: ");
			e.printStackTrace();
		}
	}
}
