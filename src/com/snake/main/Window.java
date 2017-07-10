package com.snake.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.google.common.collect.Sets;

/**
 * New window for the snake game.
 * X position is position from the top, Y position is the position from the left.
 * TODO: Upon resizing window, we may have to reset all the image icons again in the game
 * TODO: We may have to add another variable called "isCommitted", basically the idea is once the
 * TODO: user changes direction of the snake, we may want to commit to the move and any extra moves will be considered next move
 *
 * TODO: We want to store the scores, also accumulate the points so that the user can "shop" for stuff (actually this is debatable if I want to do this, maybe for this simplicity of this game I won't implement this)
 */
public class Window extends JFrame {

	private static final long serialVersionUID = -2542001418764869760L;
	private static final int gridSize = Configuration.getGridSize();

	private static final String UP_ACTION = "UP";
	private static final String DOWN_ACTION = "DOWN";
	private static final String RIGHT_ACTION = "RIGHT";
	private static final String LEFT_ACTION = "LEFT";

	private ArrayList<Position> snakePositions;
	private final Set<Position> allPossiblePositions;
	private ArrayList<ArrayList<DataOfSquare>> gameGrid;

	private GameDirection.Direction lastDirection;
	private GameDirection.Direction currentDirection;
	private boolean directionCommitted;

	private int sizeSnake;
	private Position foodPosition;
	private Position headSnakePosition;

	private int score = 0;
	private JTextField scoreField;

	private static BufferedImage bgImage;
	private static BufferedImage food;
	private static BufferedImage snakeHeadRight;
	private static BufferedImage snakeHeadLeft;
	private static BufferedImage snakeHeadDown;
	private static BufferedImage snakeHeadUp;
	private static BufferedImage snake;
	private static BufferedImage snakeTail;
	private static BufferedImage collision;

	/**
	 * Initialize window, set the title, gridSize, close operation and add key listener.
	 */
	public Window() {
		setTitle(Configuration.getGameTitle());
		setSize(Configuration.getWindowWidth(), Configuration.getWindowHeight());
		// TODO: Have the user be able to set a permanent window size before beginning game
		// TODO: adjust image to be this scale once the window size is set so we don't have to scale the image every time
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		sizeSnake = Configuration.getInitialSnakeSize();
		snakePositions = new ArrayList<>();
		allPossiblePositions = new HashSet<>();

		currentDirection = Configuration.getInitialSnakeDirection();
		lastDirection = Configuration.getInitialSnakeDirection();
		headSnakePosition = new Position(Configuration.getInitialSnakePosx(), Configuration.getInitialSnakePoxy());
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		initializeGame();
		new MainMenu(this, "Main Menu", true);
		spawnFoodRandomly();
	}

	public void reset() {
		sizeSnake = Configuration.getInitialSnakeSize();
		snakePositions = new ArrayList<>();

		currentDirection = Configuration.getInitialSnakeDirection();
		lastDirection = Configuration.getInitialSnakeDirection();
		headSnakePosition = new Position(Configuration.getInitialSnakePosx(), Configuration.getInitialSnakePoxy());
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		new MainMenu(this, "Main Menu", true);
		spawnFoodRandomly();
	}

	public int getScore() {
		return score;
	}

	/**
	 * Get the grid size.
	 */
	public static int getGridSize() {
		return gridSize;
	}

	/**
	 * Initialize all arrays and menus, etc.
	 */
	private void initializeGame() {
		initializeImages("default");
		initializeAllPossiblePositions();
		initializeGridAndGameData();
		initializeScorePanel();

		setJMenuBar(new MenuBar());
		initializeFrame();
	}

	public void reinitializeColors() {
		gameGrid.forEach(outerGrid -> {
			outerGrid.forEach(square -> {
				square.lightSquare(bgImage);
			});
		});
	}

	/**
	 * Initialize images used in the game.
	 * TODO: We may need to reinitialize images if user selects a different pair of images
	 */
	public static void initializeImages(final String imageOption) {

		String userDir = System.getProperty("user.dir");
		try {
			bgImage = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getBgLocation()));
			food = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getFoodLocation()));
			snakeHeadRight = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getSnakeHeadRightLocation()));
			snakeHeadLeft = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getSnakeHeadLeftLocation()));
			snakeHeadUp = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getSnakeHeadUpLocation()));
			snakeHeadDown = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getSnakeHeadDownLocation()));
			snake = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getSnakeBodyLocation()));
			snakeTail = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getSnakeTailLocation()));
			collision = ImageIO.read(new File(userDir + Configuration.getBaseImageLocation() + imageOption + Configuration.getCollisionLocation()));
		} catch (IOException e) {
			System.out.println("Encountered IOException: " + e.getMessage());
			System.exit(1);
		}

	}

	/**
	 * Initialize all possible positions, which is used to help spawn food
	 */
	private void initializeAllPossiblePositions() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				allPossiblePositions.add(new Position(i, j));
			}
		}
	}

	/**
	 * Initialize JPanel container and gameGrid. Both contain the same DataOfSquare objects,
	 * but the gameGrid will be the object used to mutate the DataOfSquare objects,
	 * while snakeGridContainer is used to display the objects.
	 */
	private void initializeGridAndGameData() {
		// Creates Threads and its data and adds it to gameGrid, which represents the game grid locations
		gameGrid = new ArrayList<>();
		final JPanel snakeGridContainer = new JPanel(new GridLayout(gridSize, gridSize));
		for (int i = 0; i < gridSize; i++) {
			final ArrayList<DataOfSquare> data = new ArrayList<>();
			for(int j = 0; j < gridSize; j++){
				DataOfSquare dataOfSquare = new DataOfSquare(bgImage);
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

		getContentPane().add(snakeGridContainer);
	}

	private void initializeFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setLocation(x, y);
		this.setVisible(true);
		this.setResizable(false);
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

		// Remove tail
		while (snakePositions.size() > sizeSnake) {
			Position tail = snakePositions.get(0);
			gameGrid.get(tail.getX()).get(tail.getY()).lightSquare(bgImage);
			snakePositions.remove(0);
		}

		// Change snake head to snake body
		if (snakePositions.size() > 1) {
			Position previousHead = snakePositions.get(snakePositions.size() - 2);
			gameGrid.get(previousHead.getX()).get(previousHead.getY()).lightSquare(snake);
		}

		// Put snake head
		switch (currentDirection) {
			case LEFT: gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(snakeHeadLeft);
				break;
			case DOWN:
				gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(snakeHeadDown);
				break;
			case UP:
				gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(snakeHeadUp);
				break;
			case RIGHT:
			default:
				gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(snakeHeadRight);
				break;
		}

		// Add tail
		gameGrid.get(snakePositions.get(0).getX()).get(snakePositions.get(0).getY()).lightSquare(snakeTail);
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
			gameGrid.get(headSnakePosition.getX()).get(headSnakePosition.getY()).lightSquare(collision);
			System.out.println(String.format("Game Over! Final Score: %s", score));

			return true;
		}

		// Check to see if snake ate the food
		if (headSnakePosition.equals(foodPosition)) {
			score += 20;
			scoreField.setText(Integer.toString(score));

			sizeSnake = sizeSnake + 1;
			spawnFoodRandomly();
		}

		return false;
	}

	/**
	 * Spawns food in a random location that is not occupied by the snake.
	 */
	private void spawnFoodRandomly() {
		final List<Position> nonSnakePositions = new ArrayList<>(Sets.filter(allPossiblePositions, (input) -> !snakePositions.contains(input)));
		this.foodPosition = nonSnakePositions.get(((int) (Math.random()*1000)) % nonSnakePositions.size());

		System.out.println(String.format("New food spawn: %d, %d", foodPosition.getX(), foodPosition.getY(), sizeSnake));
		gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(food);
	}

	private void initializeScorePanel() {
		scoreField = new JTextField(5);
		scoreField.setEditable(false);
		scoreField.setActionCommand("Score");
		scoreField.setFocusable(false);
		scoreField.setText(Integer.toString(score));

		JLabel scoreLabel = new JLabel("Score: ");
		scoreLabel.setLabelFor(scoreField);

		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;

		scorePanel.add(scoreLabel, constraints);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;

		scorePanel.add(scoreField, constraints);

		getContentPane().add(scorePanel, BorderLayout.SOUTH);
	}

	private class MoveUp extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lastDirection != GameDirection.Direction.DOWN) {
				currentDirection = GameDirection.Direction.UP;
			}

		}
	}

	private class MoveDown extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lastDirection != GameDirection.Direction.UP) {
				currentDirection = GameDirection.Direction.DOWN;
			}

		}
	}

	private class MoveRight extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lastDirection != GameDirection.Direction.LEFT) {
				currentDirection = GameDirection.Direction.RIGHT;
			}

		}
	}

	private class MoveLeft extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lastDirection != GameDirection.Direction.RIGHT) {
				currentDirection = GameDirection.Direction.LEFT;
			}

		}
	}
}
