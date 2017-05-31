package com.snake.main;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
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
	public static int width = 20;
	public static int height = 20; // TODO: Get this into config, which requires some thought because Gridlayout is kinda using this

	/**
	 * Initialize window, set the title, size, close operation and add key listener.
	 *
	 * @param title title of the window.
	 */
	public Window(final String title) {
		LOG.info("New game. Speed: " + Configuration.getSpeed() + ", Snake size: " + Configuration.getInitialSnakeSize());
		setTitle(title);
		setSize(Configuration.getWindowWidth(), Configuration.getWindowHeight());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addKeyListener(new KeyboardListener());
	}

	public void run() {
		gameGrid = new ArrayList<>();
		getContentPane().setLayout(new GridLayout(20, 20,0,0));

		// Creates Threads and its data and adds it to the arrayList
		for (int i = 0; i < width; i++){
			ArrayList<DataOfSquare> data = new ArrayList<>();
			for(int j = 0 ;j < height; j++){
				DataOfSquare dataOfSquare = new DataOfSquare(DataOfSquare.GameColor.BACKGROUND);
				data.add(dataOfSquare);
				getContentPane().add(dataOfSquare.getSquare());
			}
			gameGrid.add(data);
		}

		ThreadsController threadsController = new ThreadsController(new Position(Configuration.getInitialSnakePosx(),
				Configuration.getInitialSnakePoxy()));
		try {
			threadsController.start();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}



	}
}
