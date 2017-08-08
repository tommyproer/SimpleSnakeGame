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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.snake.main.Configuration;
import com.snake.main.RunnableGame;
import com.snake.main.controller.ImageController;

/**
 * Main menu of the game.
 */
public class MainMenu extends JDialog {

    private static final int HEIGHT = 310;
    private static final int WIDTH = 200;

    // Difficulties
    private static final String EASY = "Easy";
    private static final String NORMAL = "Normal";
    private static final String HARD = "Hard";
    private static final String EXTREME = "Extreme"; // TODO: this also needs to be in configuration

    // Themes
    private static final List<String> THEMES = Arrays.asList("default", "princess", "retro");
    private static final List<String> THEMES_NAMES = Arrays.asList("Default", "Princess", "Retro");

    private static final String PLAY_BUTTON = "Play!";
    private static final String EXIT_BUTTON = "Exit";

    private static final Map<String, Integer> DIFFICULTY_TO_SPEED_MAP = Configuration.getDifficultyMap();

    private ButtonGroup difficultyButtonGroup;
    private ButtonGroup themeButtonGroup;

    public MainMenu(Window owner, final String title, final boolean modal) {
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

    private JPanel createButtonsPanel(Window jFrame) {
        JPanel buttons = new JPanel(new BorderLayout());

        // Create play buttons
        JButton playButton = new JButton(PLAY_BUTTON);
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String difficultyCommand = difficultyButtonGroup.getSelection().getActionCommand();
                String themeCommand = themeButtonGroup.getSelection().getActionCommand();

                ImageController.getInstance().initializeImages(themeCommand);

                buttons.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                jFrame.reinitializeColors();

                Configuration.getInstance().setSpeed(DIFFICULTY_TO_SPEED_MAP.get(difficultyCommand));
                ScorePanel.getInstance().setDifficulty(difficultyCommand);
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
        final int numDifficulty = 4;
        JRadioButton[] radioButtons = new JRadioButton[numDifficulty];
        difficultyButtonGroup = new ButtonGroup();

        radioButtons[0] = new JRadioButton(EASY);
        radioButtons[0].setActionCommand(EASY);

        radioButtons[1] = new JRadioButton(NORMAL);
        radioButtons[1].setActionCommand(NORMAL);

        radioButtons[2] = new JRadioButton(HARD);
        radioButtons[2].setActionCommand(HARD);

        radioButtons[3] = new JRadioButton(EXTREME);
        radioButtons[3].setActionCommand(EXTREME);

        for (int i = 0; i < numDifficulty; i++) {
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
