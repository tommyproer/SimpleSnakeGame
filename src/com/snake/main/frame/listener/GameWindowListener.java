package com.snake.main.frame.listener;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import com.snake.main.controller.SoundController;

/**
 * Listener for the Game Window.
 */
public class GameWindowListener implements AWTEventListener {

    private static volatile GameWindowListener gameWindowListener;

    public static GameWindowListener getInstance() {
        if (gameWindowListener == null) {
            gameWindowListener = new GameWindowListener();
        }
        return gameWindowListener;
    }

    private static final SoundController soundController = SoundController.getInstance();

    private GameWindowListener() {}

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof KeyEvent) {
            KeyEvent key = (KeyEvent) event;
            if (key.getID() == KeyEvent.KEY_PRESSED) {
                if (key.getKeyChar() == 'm' || key.getKeyChar() == 'M') {
                    soundController.toggleThemeMusic();
                } else if (key.getKeyChar() == 'n' || key.getKeyChar() == 'n') {
                    soundController.toggleMuteSoundEffects();
                } else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }

            }
        }
    }
}
