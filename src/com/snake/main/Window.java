package com.snake.main;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;


class Window extends JFrame {
	private static final long serialVersionUID = -2542001418764869760L;
	public static ArrayList<ArrayList<DataOfSquare>> Grid;
	public static int width = 20;
	public static int height = 20;
	public Window() {
		
		// Creates the arraylist that'll contain the threads
		Grid = new ArrayList<>();

		// Setting up the layout of the panel
		getContentPane().setLayout(new GridLayout(20, 20,1,1));

		// Creates Threads and its data and adds it to the arrayList
		for (int i=0; i<width; i++){
			ArrayList<DataOfSquare> data= new ArrayList<>();
			for(int j=0;j<height;j++){
				DataOfSquare dataOfSquare = new DataOfSquare(DataOfSquare.GameColor.BACKGROUND);
				data.add(dataOfSquare);
				getContentPane().add(dataOfSquare.getSquare());
			}
			Grid.add(data);
		}

		// initial position of the snake
		Position position = new Position(10,10);
		// passing this value to the controller
		ThreadsController threadsController = new ThreadsController(position);
		//Let's start the game now..
		threadsController.start();

		// Links the window to the keyboard listener.
		this.addKeyListener(new KeyboardListener());

		//To do : handle multiplayers .. The above works, test it and see what happens
		
		//Position position2 = new Position(13,13);
		//ControlleurThreads c2 = new ControlleurThreads(position2);
		//c2.start();
	}
}
