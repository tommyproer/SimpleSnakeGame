package com.snake.main.frame;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.snake.main.Configuration;
import com.snake.main.RunnableGame;
import com.snake.main.controller.ImageController;

/**
 * Main menu of the game.
 */
class GameDialogMenu extends JDialog {

    private static final int HEIGHT = 310;
    private static final int WIDTH = 200;

    // Themes
    private static final List<String> THEMES = Arrays.asList("default", "princess", "retro");
    private static final List<String> THEMES_NAMES = Arrays.asList("Default", "Princess", "Retro");

    private static final String PLAY_BUTTON = "Play!";
    private static final String EXIT_BUTTON = "Exit";

    private static final Map<String, Integer> DIFFICULTY_TO_SPEED_MAP = Configuration.getDifficultyMap();

    private ButtonGroup difficultyButtonGroup;
    private ButtonGroup themeButtonGroup;

    GameDialogMenu(GameWindow owner, final String title, final boolean modal) {
        super(owner, title, modal);

        JPanel difficultyPanel = createDifficultyPanel();
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(20,20,5,20));

        JPanel themePanel = createThemePanel();
        themePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Difficulty", null,
                difficultyPanel,
                "Select the difficulty of the game");
        tabbedPane.addTab("Theme", null,
                themePanel,
                "Select theme of the game");

        JPanel menuPanel = new JPanel();
        menuPanel.add(tabbedPane);
        menuPanel.add(createButtonsPanel(owner), BorderLayout.SOUTH);

        this.getContentPane().add(menuPanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                owner.dispose();
                RunnableGame.exitGame();
            }
        });

        this.pack();
        this.setLocationRelativeTo(this);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);

        this.setVisible(true);

    }

    private JPanel createButtonsPanel(GameWindow jFrame) {
        JPanel buttons = new JPanel(new BorderLayout());

        // Create play buttons
        JButton playButton = new JButton(PLAY_BUTTON);
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                String difficultyCommand = difficultyButtonGroup.getSelection().getActionCommand();
                String themeCommand = themeButtonGroup.getSelection().getActionCommand();

                ImageController.getInstance().initializeImages(themeCommand);

                jFrame.reinitializeColors();

                Configuration.getInstance().setSpeed(DIFFICULTY_TO_SPEED_MAP.get(difficultyCommand));
                GameScorePanel.getInstance().setDifficulty(difficultyCommand);
                dispose();

            }
        });

        // Create exit button
        JButton exitButton = new JButton(EXIT_BUTTON);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                RunnableGame.exitGame();
            }
        });

        buttons.add(playButton, BorderLayout.LINE_START);
        buttons.add(exitButton, BorderLayout.LINE_END);

        return buttons;
    }

    private JPanel createDifficultyPanel() {
        ArrayList<String> difficulties = new ArrayList<>(DIFFICULTY_TO_SPEED_MAP.keySet());
        final int numDifficulty = difficulties.size();
        JRadioButton[] radioButtons = new JRadioButton[numDifficulty];
        difficultyButtonGroup = new ButtonGroup();

        for (int i = 0; i < numDifficulty; i++) {
            radioButtons[i] = new JRadioButton(difficulties.get(i));
            radioButtons[i].setActionCommand(difficulties.get(i));
            difficultyButtonGroup.add(radioButtons[i]);
        }
        radioButtons[1].setSelected(true);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(new JLabel("Choose Difficulty"));

        for (int i = 0; i < numDifficulty; i++) {
            box.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        return pane;
    }

    private JPanel createThemePanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(new JLabel("Choose Theme"));

        themeButtonGroup = new ButtonGroup();

        for (int i = 0; i < THEMES.size(); i++) {
            JRadioButton radioButton = new JRadioButton(THEMES_NAMES.get(i));
            radioButton.setActionCommand(THEMES.get(i));

            themeButtonGroup.add(radioButton);
            box.add(radioButton);

            if (i == 0) {
                radioButton.setSelected(true);
            }
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        return pane;
    }
}
