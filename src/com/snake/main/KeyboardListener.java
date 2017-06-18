package com.snake.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

	@Override
	public void keyPressed(final KeyEvent e) {
		switch(e.getKeyCode()) {
			case 39:	// -> Right
						if (RunnableGame.lastDirection != GameDirection.Direction.LEFT)
							RunnableGame.direction = GameDirection.Direction.RIGHT;
						break;
			case 38:	// -> Top
						if(RunnableGame.lastDirection != GameDirection.Direction.DOWN)
							RunnableGame.direction = GameDirection.Direction.UP;
						break;
			case 37: 	// -> Left
						if(RunnableGame.lastDirection != GameDirection.Direction.RIGHT)
							RunnableGame.direction = GameDirection.Direction.LEFT;
						break;

			case 40:	// -> Bottom
						if(RunnableGame.lastDirection != GameDirection.Direction.UP)
							RunnableGame.direction = GameDirection.Direction.DOWN;
						break;

			default: 	break;
		}
	}
}
