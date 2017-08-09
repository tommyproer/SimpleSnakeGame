package com.snake.main.frame;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.KeyEvent;

import com.snake.main.frame.listener.SnakeMenuBarListener;

/**
 * Menu bar at the top of the page.
 */
public class MainMenuBar extends JMenuBar {

    private final SnakeMenuBarListener snakeMenuBarListener;

    public MainMenuBar(final JFrame frame) {
        snakeMenuBarListener = new SnakeMenuBarListener(frame);

        this.setOpaque(true);
        this.setSize(10, 30);
        this.setVisible(true);

        this.add(getFileMenu());
        this.add(getHelperMenu());
    }

    private JMenu getFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_O);

        JMenuItem muteItem = new JMenuItem("Toggle Mute");
        muteItem.setMnemonic(KeyEvent.VK_M);
        muteItem.setActionCommand(SnakeMenuBarListener.MUTE);
        muteItem.addActionListener(snakeMenuBarListener);
        fileMenu.add(muteItem);

        JMenuItem pauseItem = new JMenuItem("Toggle Pause");
        pauseItem.setMnemonic(KeyEvent.VK_P);
        pauseItem.setActionCommand(SnakeMenuBarListener.PAUSE);
        pauseItem.addActionListener(snakeMenuBarListener);
        fileMenu.add(pauseItem);

        JMenuItem musicItem = new JMenuItem("Toggle Pause Music");
        musicItem.setMnemonic(KeyEvent.VK_T);
        musicItem.setActionCommand(SnakeMenuBarListener.PAUSE_MUSIC);
        musicItem.addActionListener(snakeMenuBarListener);
        fileMenu.add(musicItem);

        return fileMenu;
    }

    private JMenu getHelperMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem instructionItem = new JMenuItem("Instructions");
        instructionItem.setMnemonic(KeyEvent.VK_I);
        instructionItem.setActionCommand(SnakeMenuBarListener.INSTRUCTIONS);
        instructionItem.addActionListener(snakeMenuBarListener);
        helpMenu.add(instructionItem);

        return helpMenu;
    }


}
