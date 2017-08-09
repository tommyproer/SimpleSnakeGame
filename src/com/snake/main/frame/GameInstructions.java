package com.snake.main.frame;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Frame;
import java.util.Arrays;
import java.util.List;

/**
 * Shows instructions to users on how to play the game
 */
public class GameInstructions extends JDialog {

    public GameInstructions(final Frame owner, final boolean modal) {
        super(owner, "Instructions", modal);

        this.add(createInstructions(
                Arrays.asList(
                        "Use WASD or Arrow Keys to move snake\n",
                        "Press P to pause game\n",
                        "Press L to mute\n",
                        "Press M to pause music\n"
                )
        ));
        this.setLocationRelativeTo(null);
        this.setSize(300,130);
        this.setVisible(true);
    }

    private JPanel createInstructions(List<String> instructions) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));

        instructions.forEach(instruction -> {
            JLabel instructionLabel = new JLabel();
            instructionLabel.setText(instruction);

            jPanel.add(instructionLabel);
        });

        return jPanel;
    }
}
