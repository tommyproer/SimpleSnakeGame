package com.snake.main.frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Shows instructions to users on how to play the game.
 */
public class GameInstructions extends JDialog implements ActionListener {

    private static final String OK = "OK";

    public GameInstructions(final Frame owner, final boolean modal) {
        super(owner, "Instructions", modal);

        this.setLayout(new BorderLayout(10, 10));
        this.add(createInstructionsPanel(
                Arrays.asList(
                        "Use WASD or Arrow Keys to move snake\n",
                        "Press P to pause game\n",
                        "Press M to mute\n",
                        "Press T to pause music\n"
                )
        ));
        this.add(createButtonsPanel(), BorderLayout.SOUTH);

        this.setSize(300,150);
        this.setLocationRelativeTo(this);
        this.setVisible(true);
    }

    private JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout(10, 10));

        JButton okButton = new JButton();
        okButton.setText("Got It");
        okButton.setMnemonic(KeyEvent.VK_G);
        okButton.setActionCommand(OK);
        okButton.addActionListener(this);
        okButton.setSize(10,10);
        buttonPanel.add(okButton);

        return buttonPanel;
    }

    private JPanel createInstructionsPanel(final List<String> instructions) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));

        instructions.forEach(instruction -> {
            JLabel instructionLabel = new JLabel();
            instructionLabel.setText(instruction);

            jPanel.add(instructionLabel);
        });

        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals(OK)) {
            this.dispose();
        }
    }
}
