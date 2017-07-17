package com.snake.main.controller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.snake.main.Configuration;
import com.snake.main.frame.Window;

/**
 * Controls sounds of the game
 */
public class SoundController {

    private static volatile SoundController soundController;

    public static SoundController getInstance() {
        if (soundController == null) {
            soundController = new SoundController();
        }
        return soundController;
    }

    private Clip eatClip;
    private Clip gameOver;
    private Clip themeMusic;

    private SoundController () {
        try {
            eatClip = AudioSystem.getClip();
            eatClip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(Configuration.getEatClipLocation())));
            eatClip.setFramePosition(eatClip.getFrameLength()); // For some reason this prevents the first loop from executing twice

            gameOver = AudioSystem.getClip();
            gameOver.open(AudioSystem.getAudioInputStream(this.getClass().getResource(Configuration.getGameOverClipLocation())));
            gameOver.setFramePosition(gameOver.getFrameLength());

            themeMusic = AudioSystem.getClip();
            themeMusic.open(AudioSystem.getAudioInputStream(Window.class.getResource(Configuration.getInstance().getThemeClipLocation())));
            themeMusic.setMicrosecondPosition(20_000_000L);
            FloatControl gainControl = (FloatControl) themeMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-4.0f);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void playEatClip() {
        eatClip.loop(1);
    }

    public void playGameOver() {
        gameOver.loop(1);
    }

    public void playThemeMusic() {
        themeMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopThemeMusic() {
        themeMusic.stop();
        themeMusic.setMicrosecondPosition(20_000_000L);
    }

    public void toggleThemeMusic() {
        if (themeMusic.isRunning()) {
            themeMusic.stop();
        } else {
            themeMusic.start();
        }
    }
}
