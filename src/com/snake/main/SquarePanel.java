package com.snake.main;

import java.awt.Color;
import javax.swing.JPanel;

public class SquarePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public SquarePanel(final Color color){
		this.setBackground(color);
	}
	
	public void changeColor(final Color color) {
		this.setBackground(color);
		this.repaint();
	}
}

