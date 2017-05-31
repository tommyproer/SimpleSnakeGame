package com.snake.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

	@Override
	public void keyPressed(final KeyEvent e) {
		switch(e.getKeyCode()) {
			case 39:	// -> Right
						if (RunnableGame.direction != RunnableGame.GameDirection.LEFT)
							RunnableGame.direction = RunnableGame.GameDirection.RIGHT;
						break;
			case 38:	// -> Top
						if(RunnableGame.direction != RunnableGame.GameDirection.DOWN)
							RunnableGame.direction = RunnableGame.GameDirection.UP;
						break;
			case 37: 	// -> Left
						if(RunnableGame.direction != RunnableGame.GameDirection.RIGHT)
							RunnableGame.direction = RunnableGame.GameDirection.LEFT;
						break;

			case 40:	// -> Bottom
						if(RunnableGame.direction != RunnableGame.GameDirection.UP)
							RunnableGame.direction = RunnableGame.GameDirection.DOWN;
						break;

			default: 	break;
		}
	}
}