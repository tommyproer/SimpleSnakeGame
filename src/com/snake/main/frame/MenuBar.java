package com.snake.main.frame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        this.setOpaque(true);
        this.setSize(10, 30);
        this.setVisible(true);
        this.add(new JMenu("File"));
        this.add(new JMenu("Options"));
    }
}
