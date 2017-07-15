package com.snake.main.frame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class ScorePanel extends JPanel {

    private static ScorePanel scorePanel;

    public static ScorePanel getInstance() {
        if (scorePanel == null) {
            scorePanel = new ScorePanel();
        }
        return scorePanel;
    }

    private final JTextField scoreField;
    private int score = 0;
    private final int increaseScoreValue = 20;

    private ScorePanel() {
        scoreField = new JTextField(5);
        scoreField.setEditable(false);
        scoreField.setActionCommand("Score");
        scoreField.setFocusable(false);
        scoreField.setText(Integer.toString(score));

        JLabel scoreLabel = new JLabel("Score: ");
        scoreLabel.setLabelFor(scoreField);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;

        add(scoreLabel, constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        add(scoreField, constraints);
    }

    public void increaseScore() {
        score += increaseScoreValue;
        scoreField.setText(Integer.toString(score));
    }

    public void resetScore() {
        score = 0;
        scoreField.setText(Integer.toString(score));
    }

    public int getScore() {
        return score;
    }
}
