package com.snake.main.frame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class ScorePanel extends JPanel {

    private static volatile ScorePanel scorePanel;

    public static ScorePanel getInstance() {
        if (scorePanel == null) {
            scorePanel = new ScorePanel();
        }
        return scorePanel;
    }

    private final JTextField scoreField;
    private final JTextField difficultyField;
    private int score = 0;
    private final int increaseScoreValue = 50;

    private ScorePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints labelConstraints = createLabelConstraint();
        GridBagConstraints fieldConstraints = createFieldConstraint();

        scoreField = new JTextField();
        scoreField.setEditable(false);
        scoreField.setFocusable(false);
        scoreField.setText(Integer.toString(score));

        JLabel scoreLabel = new JLabel("Score: ");
        scoreLabel.setLabelFor(scoreField);

        add(scoreLabel, labelConstraints);
        add(scoreField, fieldConstraints);

        difficultyField = new JTextField();
        difficultyField.setEditable(false);
        difficultyField.setFocusable(false);

        JLabel difficultyLabel = new JLabel("Difficulty: ");
        difficultyLabel.setLabelFor(difficultyField);

        add(difficultyLabel, labelConstraints);
        add(difficultyField, fieldConstraints);
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

    public void setDifficulty(final String difficulty) {
        difficultyField.setText(difficulty);
    }

    private GridBagConstraints createLabelConstraint() {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.gridwidth = GridBagConstraints.RELATIVE;
        labelConstraints.fill = GridBagConstraints.NONE;
        labelConstraints.weightx = 0.0;

        return labelConstraints;
    }

    private GridBagConstraints createFieldConstraint() {
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.anchor = GridBagConstraints.WEST;
        fieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;

        return fieldConstraints;
    }
}
