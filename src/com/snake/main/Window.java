package com.snake.main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.google.common.collect.Sets;

/**
 * New window for the snake game.
 * X position is position from the top, Y position is the position from the left.
 * TODO: Upon resizing window, we may have to reset all the image icons again in the game
 */
public class Window extends JFrame {

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

	private int score = 0;
	private JTextField scoreField;

	private BufferedImage bgImage;
	private BufferedImage food;
	private BufferedImage snakeHeadRight;
	private BufferedImage snakeHeadLeft;
	private BufferedImage snakeHeadDown;
	private BufferedImage snakeHeadUp;
	private BufferedImage snake;
	private BufferedImage snakeTail;
	private BufferedImage collision;

	/**
	 * Initialize window, set the title, gridSize, close operation and add key listener.
	 */
	public Window() {
		setTitle(Configuration.getGameTitle());
		setSize(Configuration.getWindowWidth(), Configuration.getWindowHeight());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		sizeSnake = Configuration.getInitialSnakeSize();
		snakePositions = new ArrayList<>();
		allPossiblePositions = new HashSet<>();
		gameGrid = new ArrayList<>();
		currentDirection = Configuration.getInitialSnakeDirection();
		lastDirection = Configuration.getInitialSnakeDirection();
		headSnakePosition = new Position(Configuration.getInitialSnakePosx(), Configuration.getInitialSnakePoxy());
		snakePositions.add(new Position(headSnakePosition.getX(), headSnakePosition.getY()));

		try {
			bgImage = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getBgLocation()));
			food = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getFoodLocation()));
			snakeHeadRight = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getSnakeHeadRightLocation()));
			snakeHeadLeft = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getSnakeHeadLeftLocation()));
			snakeHeadUp = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getSnakeHeadUpLocation()));
			snakeHeadDown = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getSnakeHeadDownLocation()));
			snake = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getSnakeBodyLocation()));
			snakeTail = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getSnakeTailLocation()));
			collision = ImageIO.read(new File(System.getProperty("user.dir") + Configuration.getCollisionLocation()));
		} catch (IOException e) {
			System.out.println("Encountered IOException: " + e.getMessage());
			this.dispose();
		}



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

			for (int i = 0 ; i < 20; i++) {
				pause();
			}
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
		gameGrid.get(foodPosition.getX()).get(foodPosition.getY()).lightSquare(food);
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
				DataOfSquare dataOfSquare = new DataOfSquare(bgImage);
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

		// TODO: Put all below of this into a separate method
		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("UP"), "doSomething");
		snakeGridContainer.getActionMap().put("doSomething", new MoveUp());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "doSomething2");
		snakeGridContainer.getActionMap().put("doSomething2", new MoveDown());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doSomething3");
		snakeGridContainer.getActionMap().put("doSomething3", new MoveLeft());

		snakeGridContainer.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doSomething4");
		snakeGridContainer.getActionMap().put("doSomething4", new MoveRight());



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


		getContentPane().add(snakeGridContainer);
		getContentPane().add(scorePanel, BorderLayout.SOUTH);
		setJMenuBar(createMenuBar());
		// TODO: Put all above to separate method

		this.setVisible(true);
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
