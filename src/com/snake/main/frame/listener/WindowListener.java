package com.snake.main.frame.listener;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import com.snake.main.controller.SoundController;

public class WindowListener implements AWTEventListener {

    private static volatile WindowListener windowListener;

    public static WindowListener getInstance() {
        if (windowListener == null) {
            windowListener = new WindowListener();
        }
        return windowListener;
    }

    private static final SoundController soundController = SoundController.getInstance();

    private WindowListener() {}

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof KeyEvent) {
            KeyEvent key = (KeyEvent) event;
            if (key.getID() == KeyEvent.KEY_PRESSED) {
                if (key.getKeyChar() == 'm' || key.getKeyChar() == 'M') {
                    soundController.toggleThemeMusic();
                } else if (key.getKeyChar() == 'l' || key.getKeyChar() == 'L') {
                    soundController.toggleMute();
                } else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }

            }
        }
    }
}
