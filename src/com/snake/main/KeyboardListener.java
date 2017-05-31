package com.snake.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

	@Override
	public void keyPressed(final KeyEvent e) {
		switch(e.getKeyCode()) {
			case 39:	// -> Right
						if (ThreadsController.gameDirection != ThreadsController.GameDirection.LEFT)
							ThreadsController.gameDirection = ThreadsController.GameDirection.RIGHT;
						break;
			case 38:	// -> Top
						if(ThreadsController.gameDirection != ThreadsController.GameDirection.DOWN)
							ThreadsController.gameDirection = ThreadsController.GameDirection.UP;
						break;
			case 37: 	// -> Left
						if(ThreadsController.gameDirection != ThreadsController.GameDirection.RIGHT)
							ThreadsController.gameDirection = ThreadsController.GameDirection.LEFT;
						break;

			case 40:	// -> Bottom
						if(ThreadsController.gameDirection != ThreadsController.GameDirection.UP)
							ThreadsController.gameDirection = ThreadsController.GameDirection.DOWN;
						break;

			default: 	break;
		}
	}
}