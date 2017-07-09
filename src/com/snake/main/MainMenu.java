package com.snake.main;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JDialog {

    public MainMenu(JFrame owner, final String title, final boolean modal) {
        super(owner, title, modal);

        JPanel menuPanel = new JPanel();

        JPanel difficultyPanel = createDifficultyPanel(this, owner);
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(20,20,5,20));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Difficulty", null,
                difficultyPanel,
                "Select the difficulty of the game");

        menuPanel.add(tabbedPane);


        this.getContentPane().add(menuPanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        this.pack();
        this.setLocationRelativeTo(this);
        this.setVisible(true);
    }

    private JPanel createDifficultyPanel(JDialog jDialog, JFrame jFrame) {
        final int numButtons = 4;
        JRadioButton[] radioButtons = new JRadioButton[numButtons];
        final ButtonGroup group = new ButtonGroup();

        final String easy = "EASY";
        final String normal = "NORMAL";
        final String hard = "HARD";
        final String veryHard = "VERY_HARD";

        radioButtons[0] = new JRadioButton("Easy");
        radioButtons[0].setActionCommand(easy);

        radioButtons[1] = new JRadioButton("Normal");
        radioButtons[1].setActionCommand(normal);

        radioButtons[2] = new JRadioButton("Hard");
        radioButtons[2].setActionCommand(hard);

        radioButtons[3] = new JRadioButton("Extreme");
        radioButtons[3].setActionCommand(veryHard);

        for (int i = 0; i < numButtons; i++) {
            group.add(radioButtons[i]);
        }
        radioButtons[1].setSelected(true);

        JButton playButton = new JButton("Play!");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();

                if (command.equals(easy)) {
                    Configuration.setSpeed(50);
                } else if (command.equals(normal)) {
                    Configuration.setSpeed(35);
                } else if (command.equals(hard)) {
                    Configuration.setSpeed(20);
                } else if (command.equals(veryHard)) {
                    Configuration.setSpeed(2);
                }
                jDialog.dispose();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel box = new JPanel();
        JLabel label = new JLabel("Difficulty");

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(label);

        for (int i = 0; i < numButtons; i++) {
            box.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(playButton, BorderLayout.LINE_START);
        buttons.add(exitButton, BorderLayout.LINE_END);


        pane.add(buttons, BorderLayout.PAGE_END);

        return pane;
    }
}
