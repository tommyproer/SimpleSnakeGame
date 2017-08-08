package com.snake.main.frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Frame;

public class GameInstructions extends JDialog {

    public GameInstructions(final Frame owner, final boolean modal) {
        super(owner, "Instructions", modal);

        JLabel instructionPanel = new JLabel();
        instructionPanel.setOpaque(true);
        instructionPanel.setText("test");

        this.add(instructionPanel);
        this.setLocationRelativeTo(owner);
        this.setSize(400,250);
        this.setVisible(true);

    }
}
