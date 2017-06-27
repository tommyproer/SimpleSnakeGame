package com.snake.main;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * New window for the snake game.
 */
public class Window extends JFrame {
	private final Logger LOG = LoggerFactory.getLogger(Window.class);

	private static final long serialVersionUID = -2542001418764869760L;
	public static ArrayList<ArrayList<DataOfSquare>> gameGrid;

	public static int windowSize = Configuration.getWindowSize();

	/**
	 * Initialize window, set the title, windowSize, close operation and add key listener.
	 *
	 * @param title title of the window.
	 */
	public Window(final String title) {
		LOG.info("New game. Speed: " + Configuration.getSpeed() + ", Snake windowSize: " + Configuration.getInitialSnakeSize());
		setTitle(title);
		setSize(Configuration.getWindowWidth(), Configuration.getWindowHeight());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addKeyListener(new KeyboardListener());
	}

	public void run() {
		gameGrid = new ArrayList<>();

		//TODO: This invalidates the key listener for some reason
//		JPanel t2 = new JPanel();
//		JTextArea jTextArea = new JTextArea("Testing");
//		jTextArea.setEditable(false);
//		t2.add(jTextArea);
//		getContentPane().add(t2, BorderLayout.SOUTH);

		JPanel snakeContainer = new JPanel(new GridLayout(Window.windowSize, Window.windowSize,0,0));
		// Creates Threads and its data and adds it to the arrayList
		for (int i = 0; i < windowSize; i++){
			ArrayList<DataOfSquare> data = new ArrayList<>();
			for(int j = 0; j < windowSize; j++){
				DataOfSquare dataOfSquare = new DataOfSquare(DataOfSquare.GameColor.BACKGROUND);
				data.add(dataOfSquare);
				snakeContainer.add(dataOfSquare.getSquare());

			}
			gameGrid.add(data);
		}

		getContentPane().add(snakeContainer);

		this.setJMenuBar(createMenuBar());

		final RunnableGame runnableGame =
				new RunnableGame(
						new Position(Configuration.getInitialSnakePosx(),
								Configuration.getInitialSnakePoxy())
				);
		final Thread thread = new Thread(runnableGame);

		try {
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Encountered error while executing main thread", e);
		}
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
