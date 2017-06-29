package com.snake.main;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.google.common.collect.Sets;

/**
 * New window for the snake game.
 * X position is position from the top, Y position is the position from the left.
 */
public class Window extends JFrame {
	public class KeyboardListener extends KeyAdapter {
		@Override
		public void keyPressed(final KeyEvent e) {
			switch(e.getKeyCode()) {
				case 39:	// -> Right
					if (lastDirection != GameDirection.Direction.LEFT)
						currentDirection = GameDirection.Direction.RIGHT;
					break;
				case 38:	// -> Top
					if(lastDirection != GameDirection.Direction.DOWN)
						currentDirection = GameDirection.Direction.UP;
					break;
				case 37: 	// -> Left
					if(lastDirection != GameDirection.Direction.RIGHT)
						currentDirection = GameDirection.Direction.LEFT;
					break;

				case 40:	// -> Bottom
					if(lastDirection != GameDirection.Direction.UP)
						currentDirection = GameDirection.Direction.DOWN;
					break;
				default:
					break;
			}
		}
	}

//	private static final long serialVersionUID = -2542001418764869760L;
	private static final int gridSize = Configuration.getGridSize();

	private final ArrayList<Position> snakePositions;
	private final Set<Position> allPossiblePositions;
	private final ArrayList<ArrayList<DataOfSquare>> gameGrid;

	private GameDirection.Direction lastDirection;
	private GameDirection.Direction currentDirection;

	private int sizeSnake;
	private Position foodPosition;
	private Position headSnakePosition;

	/**
	 * Initialize window, set the title, gridSize, close operation and add key listener.
	 */
	public Window() {
		setTitle(Configuration.getGameTitle());
		setSize(Configuration.getWindowWidth(), Configuration.getWindowHeight());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addKeyListener(new KeyboardListener());

		sizeSnake = Configuration.getInitialSnakeSize();
		snakePositions = new ArrayList<>();
		allPossiblePositions = new HashSet<>();
		gameGrid = new ArrayList<>();
		currentDirection = Configuration.getInitialSnakeDirection();
		lastDirection = Configuration.getInitialSnakeDirection();
		headSnakePosition = new Position(Configuration.getInitialSnakePosx(), Configuration.getInitialSnakePoxy());
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		initializeGame(gridSize);
	}

	/**
	 * Get the grid size.
	 */
	public static int getGridSize() {
		return gridSize;
	}

	/**
	 * This is each "step" in a game.
	 * Move the snake and check for a collision.
	 */
	public boolean iterate() {
		pause();
		moveSnake();
		return checkCollision();
	}

	/**
	 * Moves the head of the snake, then deletes tail of snake.
	 */
	private void moveSnake() {
		switch(currentDirection) {
			case RIGHT:
				headSnakePosition.moveRight();
				lastDirection = GameDirection.Direction.RIGHT;
				break;
			case LEFT:
				headSnakePosition.moveLeft();
				lastDirection = GameDirection.Direction.LEFT;
				break;
			case UP:
				headSnakePosition.moveUp();
				lastDirection = GameDirection.Direction.UP;
				break;
			case DOWN:
				headSnakePosition.moveDown();
				lastDirection = GameDirection.Direction.DOWN;
				break;
		}
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		while (snakePositions.size() > sizeSnake) {
			Position tail = snakePositions.get(0);
			gameGrid.get(tail.getX()).get(tail.getY()).lightSquare(DataOfSquare.GameColor.BACKGROUND);
			snakePositions.remove(0);
		}

		gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(DataOfSquare.GameColor.SNAKE);
	}

	/**
	 * Check the position of the head of the snake and
	 * if it is equal to the position of any other part of the snake
	 * there is a collision.
	 */
	private boolean checkCollision() {
		if (snakePositions.subList(0, snakePositions.size()-1)
				.stream()
				.filter(headSnakePosition::equals)
				.findAny()
				.isPresent()) {
			System.out.println(String.format("Game Over! Final Score: %s", sizeSnake));

			for (int i = 0 ; i < 20; i++) {
				pause();
			}
			return true;
		}

		if (headSnakePosition.equals(foodPosition)) {
			sizeSnake = sizeSnake + 1;
			spawnFoodRandomly();
		}

		return false;
	}

	/**
	 * Pause between each snake move.
	 */
	private void pause() {
		try {
			Thread.sleep(Configuration.getSpeed());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Spawns food in a random location that is not occupied by the snake.
	 */
	private void spawnFoodRandomly() {
		final List<Position> nonSnakePositions = new ArrayList<>(Sets.filter(allPossiblePositions, (input) -> !snakePositions.contains(input)));
		this.foodPosition = nonSnakePositions.get(((int) (Math.random()*1000)) % nonSnakePositions.size());

		System.out.println(String.format("New food spawn: %d, %d", foodPosition.getX(), foodPosition.getY(), sizeSnake));
		gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(DataOfSquare.GameColor.FOOD);
	}

	/**
	 * Initialize all arrays and menus, etc.
	 */
	private void initializeGame(final int gridSize) {
		final JPanel snakeGridContainer = new JPanel(new GridLayout(gridSize, gridSize,0,0));
		// Creates Threads and its data and adds it to the arrayList
		for (int i = 0; i < gridSize; i++){
			final ArrayList<DataOfSquare> data = new ArrayList<>();
			for(int j = 0; j < gridSize; j++){
				DataOfSquare dataOfSquare = new DataOfSquare(DataOfSquare.GameColor.BACKGROUND);
				data.add(dataOfSquare);
				snakeGridContainer.add(dataOfSquare.getSquare());
			}
			gameGrid.add(data);
		}

		// Initialize all positions
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				allPossiblePositions.add(new Position(i, j));
			}
		}

		getContentPane().add(snakeGridContainer);
		setJMenuBar(createMenuBar());

		//TODO: This invalidates the key listener for some reason
//		JPanel t2 = new JPanel();
//		JTextArea jTextArea = new JTextArea("Testing");
//		jTextArea.setEditable(false);
//		t2.add(jTextArea);
//		getContentPane().add(t2, BorderLayout.SOUTH);

		spawnFoodRandomly();
	}

	/**
	 * TODO: Make the menu actually do something
	 */
	private JMenuBar createMenuBar() {
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.setOpaque(true);
		jMenuBar.setSize(10, 30);
		jMenuBar.setVisible(true);
		jMenuBar.add(new JMenu("File"));
		jMenuBar.add(new JMenu("Options"));

		return jMenuBar;
	}
}
