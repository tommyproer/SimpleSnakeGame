package com.snake.main.frame;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.google.common.collect.Sets;
import com.snake.main.Configuration;
import com.snake.main.controller.ImageController;
import com.snake.main.controller.SoundController;
import com.snake.main.exception.CryptoException;
import com.snake.main.frame.grid.DataOfSquare;
import com.snake.main.enums.GameDirection;
import com.snake.main.frame.grid.Position;
import com.snake.main.frame.listener.WindowListener;
import com.snake.main.handler.HighscoreHandler;

/**
 * New window for the snake game.
 * X position is position from the top, Y position is the position from the left.
 * TODO: Upon resizing window, we may have to reset all the image icons again in the game
 * TODO: We want to store the scores, also accumulate the points so that the user can "shop" for stuff (actually this is debatable if I want to do this, maybe for this simplicity of this game I won't implement this)
 */
public class Window extends JFrame {

	private static final long serialVersionUID = -2542001418764869760L;

	private static final String UP_ACTION = "UP";
	private static final String DOWN_ACTION = "DOWN";
	private static final String RIGHT_ACTION = "RIGHT";
	private static final String LEFT_ACTION = "LEFT";

	private ArrayList<Position> snakePositions;
	private final Set<Position> allPossiblePositions = new HashSet<>();
	private static ArrayList<ArrayList<DataOfSquare>> gameGrid;

	private GameDirection.Direction lastDirection;
	private GameDirection.Direction currentDirection;
	private GameDirection.Direction nextDirectionGuess;
	private boolean directionCommitted = false;

	private int sizeSnake;
	private Position foodPosition;
	private Position headSnakePosition;
	private int highscore;

	// Controllers
	private static SoundController soundController = SoundController.getInstance();
	private static ImageController imageController = ImageController.getInstance();
	private static WindowListener windowListener = WindowListener.getInstance();

	// Panels
	private static ScorePanel scorePanel = ScorePanel.getInstance();

	// Configuration
	private static Configuration configuration = Configuration.getInstance();

	private static final int gridSize = configuration.getGridSize();
	private static boolean paused = false;

	/**
	 * Initialize window, set the title, gridSize, close operation and add key listener.
	 */
	public Window() {
		try {
			setIconImage(
					ImageIO.read(this.getClass()
							.getResource("/images/default" + Configuration.getSnakeHeadRightLocation())));
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
		}

		setTitle(configuration.getGameTitle());
		setSize(configuration.getWindowWidth(), configuration.getWindowHeight());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		if (!configuration.isMusicOn()) {
			soundController.toggleMute();
		}
		soundController.playThemeMusic();

		// Initialize variables
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				allPossiblePositions.add(new Position(i, j));
			}
		}

		// Initialize Game Window
		initializeGameWindow();
		reset();
	}

	/**
	 * Reset the game.
	 */
	public void reset() {
		sizeSnake = configuration.getInitialSnakeSize();
		snakePositions = new ArrayList<>();

		currentDirection = configuration.getInitialSnakeDirection();
		lastDirection = configuration.getInitialSnakeDirection();

		headSnakePosition = new Position(configuration.getInitialSnakePosx(), configuration.getInitialSnakePoxy());
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		new MainMenu(this, "Game Menu", true); // TODO: pause the game instead and set modal to false

		spawnFoodRandomly();

		scorePanel.resetScore();
	}

	/**
	 * Get the grid size.
	 */
	public static int getGridSize() {
		return gridSize;
	}

	public int getHighscore() { return highscore; }

	/**
	 * Initialize all arrays and menus, etc.
	 */
	private void initializeGameWindow() {
		initializeFrame();

		initializeGrid();

		getContentPane().add(scorePanel, BorderLayout.SOUTH);
		setJMenuBar(new MainMenuBar(this));
	}

	public void reinitializeColors() {
		gameGrid.forEach(outerGrid -> {
			outerGrid.forEach(square -> {
				square.lightSquare(imageController.getBgImage());
			});
		});
	}

	public static void rescaleAllSquares(int width, int height) {
		gameGrid.forEach(outerGrid -> {
			outerGrid.forEach(square -> {
				square.resizeImage(width, height);
			});
		});
	}

	/**
	 * Initialize JPanel container and gameGrid. Both contain the same DataOfSquare objects,
	 * but the gameGrid will be the object used to mutate the DataOfSquare objects,
	 * while snakeGridContainer is used to display the objects.
	 */
	private void initializeGrid() {
		try {
			highscore = HighscoreHandler.getHighscore("AIELCXDFSWOVIDKS");
		} catch (CryptoException e) {
			e.printStackTrace();
		}
		// Creates Threads and its data and adds it to gameGrid, which represents the game grid locations
		gameGrid = new ArrayList<>();
		final JPanel snakeGridContainer = new JPanel(new GridLayout(gridSize, gridSize));
		for (int i = 0; i < gridSize; i++) {
			final ArrayList<DataOfSquare> data = new ArrayList<>();
			for(int j = 0; j < gridSize; j++){
				DataOfSquare dataOfSquare = new DataOfSquare();
				data.add(dataOfSquare);
				snakeGridContainer.add(dataOfSquare.getSquare());
			}
			gameGrid.add(data);
		}

		// Initialize key strokes
		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("UP"), UP_ACTION);
		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("W"), UP_ACTION);
		snakeGridContainer.getActionMap().put(UP_ACTION, new MoveUp());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), DOWN_ACTION);
		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("S"), DOWN_ACTION);
		snakeGridContainer.getActionMap().put(DOWN_ACTION, new MoveDown());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), LEFT_ACTION);
		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("A"), LEFT_ACTION);
		snakeGridContainer.getActionMap().put(LEFT_ACTION, new MoveLeft());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), RIGHT_ACTION);
		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("D"), RIGHT_ACTION);
		snakeGridContainer.getActionMap().put(RIGHT_ACTION, new MoveRight());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("P"), "PAUSE");
		snakeGridContainer.getActionMap().put("PAUSE", new Pause()); // TODO: have instructions somewhere

		this.getToolkit().addAWTEventListener(windowListener, AWTEvent.KEY_EVENT_MASK);

		getContentPane().add(snakeGridContainer);
		snakeGridContainer.requestFocusInWindow();
	}

	private void initializeFrame() {
		this.setLocationRelativeTo(null); // Set window in middle of screen
		this.setVisible(true);
	}

	public static void togglePause() {
		paused = !paused;
	}

	public static void pauseGame() {
		paused = true;
	}

	public static void unpauseGame() {
		paused = false;
	}

	/**
	 * This is each "step" in a game.
	 * Move the snake and check for a collision.
	 */
	public boolean iterate() {
		while (paused) {
			pause();
		}
		pause();
		moveSnake();
		return checkCollision();
	}

	/**
	 * Pause between each snake move.
	 */
	private void pause() {
		try {
			Thread.sleep(configuration.getSpeed());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Moves the head of the snake, then deletes tail of snake.
	 */
	private void moveSnake() {
		// Check next guess
		if (nextDirectionGuess != null && !directionCommitted) {
			currentDirection = nextDirectionGuess;
			nextDirectionGuess = null;
		}

		// Move snake head position
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
		directionCommitted = false;

		// Update snake positions
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		// Remove tail
		while (snakePositions.size() > sizeSnake) {
			Position tail = snakePositions.get(0);
			gameGrid.get(tail.getX()).get(tail.getY()).lightSquare(imageController.getBgImage());
			snakePositions.remove(0);
		}

		// Change snake head to snake body
		if (snakePositions.size() > 1) {
			Position previousHead = snakePositions.get(snakePositions.size() - 2);
			gameGrid.get(previousHead.getX()).get(previousHead.getY()).lightSquare(imageController.getSnake());
		}

		// Put snake head
		switch (currentDirection) {
			case LEFT: gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(imageController.getSnakeHeadLeft());
				break;
			case DOWN:
				gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(imageController.getSnakeHeadDown());
				break;
			case UP:
				gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(imageController.getSnakeHeadUp());
				break;
			case RIGHT:
			default:
				gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(imageController.getSnakeHeadRight());
				break;
		}

		// Add tail
		gameGrid.get(snakePositions.get(0).getX()).get(snakePositions.get(0).getY()).lightSquare(imageController.getSnakeTail());
	}

	/**
	 * Check the position of the head of the snake and
	 * if it is equal to the position of any other part of the snake
	 * there is a collision.
	 */
	private boolean checkCollision() {
		if (snakePositions.subList(0, snakePositions.size()-1)
				.stream()
				.anyMatch(headSnakePosition::equals)) {
			gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(imageController.getCollision());
			System.out.println(String.format("Game Over! Final Score: %s", scorePanel.getScore()));

			soundController.playGameOver();
			try {

				if (scorePanel.getScore() > highscore) {
					HighscoreHandler.writeHighscore(scorePanel.getScore(), "AIELCXDFSWOVIDKS");
					highscore = scorePanel.getScore();
				}
			} catch (CryptoException e) {
				e.printStackTrace();
			}
			return true;
		}

		// Check to see if snake ate the food
		if (headSnakePosition.equals(foodPosition)) {
			scorePanel.increaseScore();

			sizeSnake = sizeSnake + 1;
			soundController.playEatClip();
			spawnFoodRandomly();
		}

		return false;
	}

	/**
	 * Spawns food in a random location that is not occupied by the snake.
	 */
	private void spawnFoodRandomly() {
		final List<Position> nonSnakePositions = new ArrayList<>(Sets.filter(allPossiblePositions, (input) -> !snakePositions.contains(input)));
		if (nonSnakePositions.size() == 0) {
			System.out.println("YOU WIN!");
			System.exit(0);
		}
		this.foodPosition = nonSnakePositions.get(((int) (Math.random()*1000)) % nonSnakePositions.size());

		System.out.println(String.format("New food spawn: %d, %d", foodPosition.getX(), foodPosition.getY(), sizeSnake));
		gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(imageController.getFood());
	}

	private class MoveUp extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (directionCommitted) {
				if (!currentDirection.equals(GameDirection.Direction.DOWN) && !currentDirection.equals(GameDirection.Direction.UP)) {
					nextDirectionGuess = GameDirection.Direction.UP;
				}
			} else if (lastDirection != GameDirection.Direction.DOWN) {
				currentDirection = GameDirection.Direction.UP;
				directionCommitted = true;
				nextDirectionGuess = null;
			}
		}
	}

	private class MoveDown extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (directionCommitted) {
				if (!currentDirection.equals(GameDirection.Direction.UP) && !currentDirection.equals(GameDirection.Direction.DOWN)) {
					nextDirectionGuess = GameDirection.Direction.DOWN;
				}
			} else if (lastDirection != GameDirection.Direction.UP) {
				currentDirection = GameDirection.Direction.DOWN;
				directionCommitted = true;
				nextDirectionGuess = null;
			}
		}
	}

	private class MoveRight extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (directionCommitted) {
				if (!currentDirection.equals(GameDirection.Direction.LEFT) && !currentDirection.equals(GameDirection.Direction.RIGHT)) {
					nextDirectionGuess = GameDirection.Direction.RIGHT;
				}
			} else if (lastDirection != GameDirection.Direction.LEFT) {
				currentDirection = GameDirection.Direction.RIGHT;
				directionCommitted = true;
				nextDirectionGuess = null;
			}
		}
	}

	private class MoveLeft extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (directionCommitted) {
				if (!currentDirection.equals(GameDirection.Direction.RIGHT) && !currentDirection.equals(GameDirection.Direction.LEFT)) {
					nextDirectionGuess = GameDirection.Direction.LEFT;
				}
			} else if (lastDirection != GameDirection.Direction.RIGHT) {
				currentDirection = GameDirection.Direction.LEFT;
				directionCommitted = true;
				nextDirectionGuess = null;
			}
		}
	}

	private class Pause extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			togglePause();
		}
	}
}
