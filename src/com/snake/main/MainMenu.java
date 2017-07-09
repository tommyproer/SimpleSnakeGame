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
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class MainMenu extends JDialog {

    private static final String EASY = "Easy";
    private static final String NORMAL = "Normal";
    private static final String HARD = "Hard";
    private static final String EXTREME = "Extreme";

    private static final String DEFAULT = "default";
    private static final String ORANGE = "orange";

    private static final String PLAY_BUTTON = "Play!";
    private static final String EXIT_BUTTON = "Exit";

    private static final Map<String, Integer> DIFFICULTY_TO_SPEED_MAP = ImmutableMap.<String, Integer>builder()
            .put(EASY, 50)
            .put(NORMAL, 35)
            .put(HARD, 20)
            .put(EXTREME, 2)
            .build();

    private ButtonGroup difficultyButtonGroup;
    private ButtonGroup themeButtonGroup;

    public MainMenu(JFrame owner, final String title, final boolean modal) {
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
        menuPanel.add(createButtonsPanel(), BorderLayout.SOUTH);

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
        this.setSize(200, 250);
        this.setResizable(false);

        this.setVisible(true);
    }

    private JPanel createButtonsPanel() {
        // Create play and exit buttons
        JButton playButton = new JButton(PLAY_BUTTON);
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String difficultyCommand = difficultyButtonGroup.getSelection().getActionCommand();
                String themeCommand = themeButtonGroup.getSelection().getActionCommand();

                Window.initializeImages(themeCommand);

                Configuration.setSpeed(DIFFICULTY_TO_SPEED_MAP.get(difficultyCommand));
                dispose();
            }
        });

        JButton exitButton = new JButton(EXIT_BUTTON);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttons = new JPanel(new BorderLayout());
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
        final int numThemes = 2;
        JRadioButton[] radioButtons = new JRadioButton[numThemes];
        themeButtonGroup = new ButtonGroup();

        radioButtons[0] = new JRadioButton("Default");
        radioButtons[0].setActionCommand(DEFAULT);

        radioButtons[1] = new JRadioButton("Creamy Orange");
        radioButtons[1].setActionCommand(ORANGE);

        for (int i = 0; i < numThemes; i++) {
            themeButtonGroup.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(new JLabel("Choose Theme"));

        for (int i = 0; i < numThemes; i++) {
            box.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        return pane;
    }
}
