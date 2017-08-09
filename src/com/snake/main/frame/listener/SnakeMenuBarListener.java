package com.snake.main.frame.listener;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.snake.main.controller.SoundController;
import com.snake.main.frame.GameInstructions;
import com.snake.main.frame.Window;

public class SnakeMenuBarListener implements ActionListener {

    private static final SoundController soundController = SoundController.getInstance();

    public static final String MUTE = "mute";
    public static final String PAUSE = "pause";
    public static final String PAUSE_MUSIC = "pause_music";
    public static final String INSTRUCTIONS = "instructions";

    private JFrame frame;

    public SnakeMenuBarListener(final JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getActionCommand().equals(MUTE)) {
            soundController.toggleMute();
        } else if (event.getActionCommand().equals(PAUSE)) {
            Window.togglePause();
        } else if (event.getActionCommand().equals(PAUSE_MUSIC)) {
            soundController.toggleThemeMusic();
        } else if (event.getActionCommand().equals(INSTRUCTIONS)) {
            Window.pauseGame();
            new GameInstructions(frame, true);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Window.unpauseGame();
        }
    }
}
