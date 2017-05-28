package com.snake.main;

import javax.swing.JFrame;

import org.apache.log4j.PropertyConfigurator;

public class Main {

	public static void main(String[] args) {
		PropertyConfigurator.configure(Configuration.getMainPath() + "\\src\\resources\\log4j.properties");

		//Creating the window with all its awesome snaky features
		Window f1= new Window();
		
		//Setting up the window settings
		f1.setTitle("Snake");
		f1.setSize(300,300);
		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

	}
}
