package com.snake.main.controller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.snake.main.Configuration;
import com.snake.main.frame.GameWindow;

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
    private boolean mute = false;

    private SoundController () {
        try {
            eatClip = AudioSystem.getClip();
            eatClip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(Configuration.getEatClipLocation())));
            eatClip.setFramePosition(eatClip.getFrameLength()); // For some reason this prevents the first loop from executing twice

            gameOver = AudioSystem.getClip();
            gameOver.open(AudioSystem.getAudioInputStream(this.getClass().getResource(Configuration.getGameOverClipLocation())));
            gameOver.setFramePosition(gameOver.getFrameLength());

            themeMusic = AudioSystem.getClip();
            themeMusic.open(AudioSystem.getAudioInputStream(GameWindow.class.getResource(Configuration.getInstance().getThemeClipLocation())));
            FloatControl gainControl = (FloatControl) themeMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-4.0f);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void playEatClip() {
        if (!mute) {
            eatClip.loop(1);
        }

    }

    public void playGameOver() {
        if (!mute) {
            gameOver.loop(1);
        }

    }

    public void playThemeMusic() {
        if (!mute) {
            themeMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }

    }

    public void stopThemeMusic() {
        themeMusic.stop();
    }

    public void toggleThemeMusic() {
        if (themeMusic.isRunning()) {
            themeMusic.stop();
        } else if (!mute) {
            themeMusic.start();
        }
    }

    public void toggleMute() {
        mute = !mute;

        if (mute) {
            themeMusic.stop();
        } else {
            themeMusic.start();
        }
    }
}
