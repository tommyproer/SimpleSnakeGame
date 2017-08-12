package com.snake.main.frame;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Container frame for the entire snake game.
 * X position is position from the top, Y position is the position from the left.
 */
public class GameWindow extends JFrame {

	private static final long serialVersionUID = -2542001418764869760L;

	private static final String UP_ACTION = "UP";
	private static final String DOWN_ACTION = "DOWN";
	private static final String RIGHT_ACTION = "RIGHT";
	private static final String LEFT_ACTION = "LEFT";
	private static final String PAUSE_ACTION = "PAUSE";

	private static final List<KeyStroke> LEFT_KEY_STROKES = Arrays.asList(KeyStroke.getKeyStroke("LEFT"), KeyStroke.getKeyStroke("A"));
	private static final List<KeyStroke> RIGHT_KEY_STROKES = Arrays.asList(KeyStroke.getKeyStroke("RIGHT"), KeyStroke.getKeyStroke("D"));
	private static final List<KeyStroke> DOWN_KEY_STROKES = Arrays.asList(KeyStroke.getKeyStroke("DOWN"), KeyStroke.getKeyStroke("S"));
	private static final List<KeyStroke> UP_KEY_STROKES = Arrays.asList(KeyStroke.getKeyStroke("UP"), KeyStroke.getKeyStroke("W"));
	private static final List<KeyStroke> PAUSE_KEY_STROKES = Arrays.asList(KeyStroke.getKeyStroke("P"));

	/**
	 * List of positions the snake is occupying
	 */
	private ArrayList<Position> snakePositions;

	/**
	 * All possible positions on the grid,
	 * used as a helper to spawn food.
	 */
	private final Set<Position> allPossiblePositions = new HashSet<>();

	/**
	 * Actual representation of the game
	 */
	private static ArrayList<ArrayList<DataOfSquare>> gameGrid;

	/**
	 * Previous direction that the snake moved.
	 */
	private GameDirection.Direction lastDirection;

	/**
	 * The current direction that the snake will move
	 */
	private GameDirection.Direction currentDirection;

	/**
	 * Often the user may press two keys in the single turn.
	 * We do not want to penalize the user in this case
	 * and want to do our best guess of what the user intends.
	 * nextDirectionGuess will store the user input if the
	 * user has already pressed a key in the same turn.
	 *
	 * E.g. Pressing left then up in the same turn means
	 * the snake should go left for one turn then go up the next turn,
	 * and nextDirectionGuess will store the UP direction.
	 */
	private GameDirection.Direction nextDirectionGuess;

	/**
	 * Indicates the user has already pressed a key
	 * in this turn.
	 */
	private boolean directionCommitted = false;

	/**
	 * Current size of the snake.
	 */
	private int sizeSnake;

	/**
	 * Current position of the food.
	 */
	private Position foodPosition;

	/**
	 * Position of the head of the snake
	 */
	private Position headSnakePosition;

	/**
	 * Highscore of the game.
	 */
	private int highscore;

	/**
	 * Controllers.
	 */
	private static SoundController soundController = SoundController.getInstance();
	private static ImageController imageController = ImageController.getInstance();
	private static WindowListener windowListener = WindowListener.getInstance();

	/**
	 * Score panel displays the score and difficulty level.
	 */
	private static ScorePanel scorePanel = ScorePanel.getInstance();

	/**
	 * Stores game configuration.
	 */
	private static Configuration configuration = Configuration.getInstance();

	/**
	 * Get grid size.
	 * E.g. a grid size of 20 means the grid is 20x20
	 */
	private static final int gridSize = configuration.getGridSize();

	/**
	 * Used to pause the game.
	 */
	private static boolean paused = false;

	/**
	 * 1. Initialize window icon.
	 * 3. Set default sound settings.
	 * 4. Initialize all possible positions, used for spawning food.
	 * 5. Initialize game window frame.
	 * 6. Display game instructions.
	 * 7. Reset score and snake.
	 */
	public GameWindow() {
		setIcon();
		getDefaultSoundSettings();
		initializeAllPossiblePositions();
		initializeGameWindow();
		new GameInstructions(this, true);
		reset();
	}

	/**
	 * Set window icon.
	 */
	private void setIcon() {
		try {
			setIconImage(
					ImageIO.read(this.getClass()
							.getResource(Configuration.getDefaultThemeLocation() +
									Configuration.getSnakeHeadRightLocation())));
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
		}
	}

	/**
	 * Set the window title, size and default close operation.
	 */
	private void configureWindow() {
		setTitle(configuration.getGameTitle());
		setSize(configuration.getWindowWidth(), configuration.getWindowHeight());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Decide whether to play music and sound effects
	 * in the game based on the configuration in
	 * game.cfg
	 */
	private void getDefaultSoundSettings() {
		if (!configuration.defaultPlaySoundEffects()) {
			soundController.toggleMuteSoundEffects();
		}
		if (configuration.defaultPlayMusic()) {
			soundController.playThemeMusic();
		}
	}

	/**
	 * Initialize allPossiblePositions on the game grid.
	 * This will be used to help spawn food.
	 */
	private void initializeAllPossiblePositions() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				allPossiblePositions.add(new Position(i, j));
			}
		}
	}

	/**
	 * Configure and display game window.
	 * Initialize all arrays and menus.
	 */
	private void initializeGameWindow() {
		configureWindow();
		displayFrame();
		initializeGrid();

		getContentPane().add(scorePanel, BorderLayout.SOUTH);
		setJMenuBar(new MainMenuBar(this));
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

		new MainMenu(this, "Game Menu", true);

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

		// Initialize key strokes for snake movement
		initializeMovementKeyStrokes(snakeGridContainer);

		// Initialize key strokes for sound toggling
		this.getToolkit().addAWTEventListener(windowListener, AWTEvent.KEY_EVENT_MASK);

		getContentPane().add(snakeGridContainer);
		snakeGridContainer.requestFocusInWindow();
	}

	/**
	 * Initialize movement actions and pause action.
	 *
	 * @param snakeGridContainer jpanel container to add the actions to
	 */
	private void initializeMovementKeyStrokes(final JPanel snakeGridContainer) {
		UP_KEY_STROKES.forEach(stroke -> {
			snakeGridContainer.getInputMap().put(stroke, UP_ACTION);
		});
		snakeGridContainer.getActionMap().put(UP_ACTION, new MoveUpAction());

		DOWN_KEY_STROKES.forEach(stroke -> {
			snakeGridContainer.getInputMap().put(stroke, DOWN_ACTION);
		});
		snakeGridContainer.getActionMap().put(DOWN_ACTION, new MoveDownAction());

		LEFT_KEY_STROKES.forEach(stroke -> {
			snakeGridContainer.getInputMap().put(stroke, LEFT_ACTION);
		});
		snakeGridContainer.getActionMap().put(LEFT_ACTION, new MoveLeftAction());

		RIGHT_KEY_STROKES.forEach(stroke -> {
			snakeGridContainer.getInputMap().put(stroke, RIGHT_ACTION);
		});
		snakeGridContainer.getActionMap().put(RIGHT_ACTION, new MoveRightAction());

		PAUSE_KEY_STROKES.forEach(stroke -> {
			snakeGridContainer.getInputMap().put(stroke, PAUSE_ACTION);
		});
		snakeGridContainer.getActionMap().put(PAUSE_ACTION, new PauseAction());
	}

	private void displayFrame() {
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
	 * This is each "step" in the game.
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
	 * Determines the speed of the game.
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
	 * Does this by getting all the positions that isn't occupied by the snake
	 * then pick a random location.
	 */
	private void spawnFoodRandomly() {
		if (imageController.getFood() == null) {
			return; // This is needed in the case that the user selects exit.
		}
		final List<Position> nonSnakePositions = new ArrayList<>(Sets.filter(allPossiblePositions, (input) -> !snakePositions.contains(input)));
		if (nonSnakePositions.size() == 0) {
			System.out.println("YOU WIN!");
			System.exit(0);
		}
		this.foodPosition = nonSnakePositions.get((int) (Math.random()*nonSnakePositions.size()));

		System.out.println(String.format("New food spawn: %d, %d", foodPosition.getX(), foodPosition.getY(), sizeSnake));

		gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(imageController.getFood());
	}

	/**
	 * Action objects that will help the
	 * snake move different directions and pause the game
	 */

	private class MoveUpAction extends AbstractAction {
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

	private class MoveDownAction extends AbstractAction {
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

	private class MoveRightAction extends AbstractAction {
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

	private class MoveLeftAction extends AbstractAction {
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

	private class PauseAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			togglePause();
		}
	}
}
