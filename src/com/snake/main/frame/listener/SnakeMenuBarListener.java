package com.snake.main.frame.listener;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.snake.main.RunnableGame;
import com.snake.main.controller.SoundController;
import com.snake.main.frame.GameInstructions;
import com.snake.main.frame.GameWindow;

/**
 * Listener for the Menu Bar.
 */
public class SnakeMenuBarListener implements ActionListener {

    private static final SoundController soundController = SoundController.getInstance();

    public static final String MUTE = "mute";
    public static final String PAUSE = "pause";
    public static final String PAUSE_MUSIC = "pause_music";
    public static final String INSTRUCTIONS = "instructions";
    public static final String EXIT = "exit";

    private JFrame frame;

    public SnakeMenuBarListener(final JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getActionCommand().equals(MUTE)) {
            soundController.toggleMuteSoundEffects();
        } else if (event.getActionCommand().equals(PAUSE)) {
            GameWindow.togglePause();
        } else if (event.getActionCommand().equals(PAUSE_MUSIC)) {
            soundController.toggleThemeMusic();
        } else if (event.getActionCommand().equals(INSTRUCTIONS)) {
            GameWindow.pauseGame();
            new GameInstructions(frame, true);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            GameWindow.unpauseGame();
        } else if (event.getActionCommand().equals(EXIT)) {
            RunnableGame.exitGame();
        }
    }
}
