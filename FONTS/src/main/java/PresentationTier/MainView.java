package PresentationTier;

import LogicTier.KenkenAlreadyInPersistence;
import LogicTier.KenkenNotGenerated;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.Duration;

import javax.swing.border.Border;


public class MainView extends JFrame {
    private CtrlPresentation ctrlPresentation;
    private JPanel appEntrancePanel;
    private JButton logInButton;
    private JButton registerButton;
    private final String PATH = "./Data";
    private JPanel previousPanel;
    private String previousPanelId;

    //Panel RegisterPage
    private JPanel registerPagePanel;
    private JTextField insertUsernameField;
    private JPasswordField insertPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton confirmRegisterButton;
    private JButton returnRegisterPage;

    //Panel LogInPage
    private JPanel logInPagePanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton confirmLogInButton;
    private JButton returnLogInPage;

    //Panel MainMenuPage
    private JPanel mainMenuPanel;
    private JButton playGameButton;
    private JButton continueGameButton;
    private JButton importKenkenButton;
    private JButton createKenkenButton;
    private JButton settingsButton;
    private JButton rulesButton;
    private JButton rankingButton;
    private JButton statisticButton;

    //Panel SettingsPage
    private JPanel settingsPanel;
    private JCheckBox musicOnOff;
    private JButton returnSettingsToMainMenu;
    private JSlider musicVolume;
    private JComboBox<String> musicSelection;

    //Panel RulesPage
    private JPanel rulesPanel;
    private JButton returnRulesToMainMenu;

    //Panel ImportKenkenPage
    private JPanel importKenkenPanel;
    private JTextField fileNameField;
    private JButton uploadButton;
    private JButton returnImportToMainMenu;

    //Panel TypeKenkenPage
    private JPanel typeKenkenPanel;
    private JButton returnTypeToMainMenu;
    private JTextArea typeKenkenTextArea;
    private JButton importTypedKenkenButton;

    //Panel CreateRandomKenkenPage
    private JPanel createRandomKenkenPanel;
    private JButton createButton;
    private JButton returnCreateRandomKenkenToMainMenu;

    //Panel RankingPage
    private JPanel rankingPanel;
    private JButton returnRankToMainButton;

    //Panel StatisticsPage
    private JPanel statisticsPanel;

    //Panel Kenken SelectionMenu
    private JPanel kenkenSelectionMenuPanel;

    //Panel Continue Game
    private JPanel continueGamePanel;

    //Listeners
    private ActionListener exitAction;
    private ActionListener returnAppEntranceAction;
    private ActionListener returnToMainMenu;
    private ActionListener returnToPreviousPanel;
    private AbstractAction returnToMainMenuKeyboard;

    //Fonts
    private Font titleFont;
    private Font buttonFont;
    private Font subtitleTextFont;
    private Font textFont;

    //Menu Bars
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu profileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenuItem exitApp;
    private JMenuItem logOut;
    private JMenuItem eliminateProfile;
    private JMenuItem settings;
    private JMenuItem rules;

    Color[] pastelColors = {
            new Color(235, 197, 197),
            new Color(235, 202, 197),
            new Color(235, 206, 197),
            new Color(235, 211, 197),
            new Color(235, 215, 197),
            new Color(235, 220, 197),
            new Color(235, 225, 197),
            new Color(235, 229, 197),
            new Color(235, 234, 197),
            new Color(232, 235, 197),
            new Color(228, 235, 197),
            new Color(223, 235, 197),
            new Color(219, 235, 197),
            new Color(214, 235, 197),
            new Color(209, 235, 197),
            new Color(205, 235, 197),
            new Color(200, 235, 197),
            new Color(197, 235, 199),
            new Color(197, 235, 203),
            new Color(197, 235, 208),
            new Color(197, 235, 212),
            new Color(197, 235, 217),
            new Color(197, 235, 222),
            new Color(197, 235, 226),
            new Color(197, 235, 231),
            new Color(197, 235, 235),
            new Color(197, 231, 235),
            new Color(197, 226, 235),
            new Color(197, 222, 235),
            new Color(197, 217, 235),
            new Color(197, 212, 235),
            new Color(197, 208, 235),
            new Color(197, 203, 235),
            new Color(197, 199, 235),
            new Color(200, 197, 235),
            new Color(205, 197, 235),
            new Color(209, 197, 235),
            new Color(214, 197, 235),
            new Color(219, 197, 235),
            new Color(223, 197, 235),
            new Color(228, 197, 235),
            new Color(232, 197, 235),
            new Color(235, 197, 234),
            new Color(235, 197, 229),
            new Color(235, 197, 225),
            new Color(235, 197, 220),
            new Color(235, 197, 215),
            new Color(235, 197, 211),
            new Color(235, 197, 206),
            new Color(235, 197, 202)
    };

    public MainView(CtrlPresentation controlPres) {
        ctrlPresentation = controlPres;
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));
        setSize(800, 600);
        setTitle("Kenken Infinity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeFonts();
        initialiseComponents();
        initialiseListeners();
    }

    private void initializeFonts() {
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(PATH+"/Lettering/aquire.otf"));
            titleFont = titleFont.deriveFont(Font.BOLD, 40);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(titleFont);
        } catch (IOException | FontFormatException e) {
            showErrorMessage(e.getMessage());
        }

        try {
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File(PATH+"/Lettering/timeBurner.ttf"));
            buttonFont = buttonFont.deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(buttonFont);
            UIManager.put("OptionPane.buttonFont", new FontUIResource(buttonFont));
        } catch (IOException | FontFormatException e) {
            showErrorMessage(e.getMessage());
        }

        try {
            subtitleTextFont = Font.createFont(Font.TRUETYPE_FONT, new File(PATH+"/Lettering/cartesian.otf"));
            subtitleTextFont = subtitleTextFont.deriveFont(Font.BOLD, 20);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(subtitleTextFont);
        } catch (IOException | FontFormatException e) {
            showErrorMessage(e.getMessage());
        }

        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File(PATH+"/Lettering/cartesian.otf"));
            textFont = textFont.deriveFont(Font.PLAIN, 15);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(textFont);
            UIManager.put("OptionPane.messageFont", new FontUIResource(textFont));
            UIManager.put("TextField.font", new FontUIResource(textFont));
            UIManager.put("TextArea.font", new FontUIResource(textFont));
        } catch (IOException | FontFormatException e) {
            showErrorMessage(e.getMessage());
        }
    }

    private void initialiseComponents() {

        //Menu bar
        setMenuBar();

        //App Entrance
        setAppEntranceLayout();

        //Register Page
        setRegisterPageLayout();

        //Log In Page
        setLogInPageLayout();

        //Main Menu Page
        setMainMenuPageLayout();

        //Ranking Page
        setRankingPageLayout();

        //Settings Page
        setSettingsPageLayout();

        //Rules Page
        setRulesPageLayout();

        //Import Kenken Page
        setImportKenkenLayout();

        //Type Kenken Page
        setTypeKenkenLayout();

        //Create Random Kenken Page
        setCreateRandomKenkenLayout();

    }

    private void setMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setFont(buttonFont);
        profileMenu = new JMenu("Profile");
        profileMenu.setFont(buttonFont);
        editMenu = new JMenu("Edit");
        editMenu.setFont(buttonFont);
        helpMenu = new JMenu("Help");
        helpMenu.setFont(buttonFont);

        exitApp = new JMenuItem("Exit");
        exitApp.setFont(buttonFont);
        fileMenu.add(exitApp);

        logOut = new JMenuItem("Log Out");
        logOut.setFont(buttonFont);
        profileMenu.add(logOut);
        eliminateProfile = new JMenuItem("Eliminate Profile");
        eliminateProfile.setFont(buttonFont);
        profileMenu.add(eliminateProfile);

        settings = new JMenuItem("Settings");
        settings.setFont(buttonFont);
        editMenu.add(settings);

        rules = new JMenuItem("Rules");
        rules.setFont(buttonFont);
        helpMenu.add(rules);

        menuBar.add(fileMenu);
        menuBar.add(profileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setAppEntranceLayout() {
        appEntrancePanel = new JPanel(new GridBagLayout());
        appEntrancePanel.setBackground(Color.WHITE);
        appEntrancePanel.setSize(800, 600);
        ImageIcon kenkenLogoIcon = new ImageIcon(PATH+"/Icons/kenkenLogoUnitedAppEntrance.png");
        Image kenkenLogoImg = kenkenLogoIcon.getImage().getScaledInstance(400, 400,Image.SCALE_SMOOTH);
        JLabel kenkenLogo = new JLabel(new ImageIcon(kenkenLogoImg));
        logInButton = new JButton("Log In");
        logInButton.setFont(buttonFont);
        registerButton = new JButton("Register");
        registerButton.setFont(buttonFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        appEntrancePanel.add(kenkenLogo, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipadx = 90;
        gbc.ipady = 15;
        appEntrancePanel.add(logInButton, gbc);
        gbc.gridy++;
        gbc.ipadx = 75;
        gbc.ipady = 15;
        appEntrancePanel.add(registerButton, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTH;
        JLabel spacer = new JLabel();
        appEntrancePanel.add(spacer, gbc);

        appEntrancePanel.setFocusable(true);
        appEntrancePanel.requestFocusInWindow();
        logInButton.setFocusable(false);
        registerButton.setFocusable(false);

    }

    private void setLogInPageLayout() {
        logInPagePanel = new JPanel(new GridBagLayout());
        logInPagePanel.setBackground(Color.WHITE);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(textFont.deriveFont(Font.PLAIN, 18));
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(textFont.deriveFont(Font.PLAIN, 18));
        passwordField = new JPasswordField(18);
        confirmLogInButton = new JButton("Log In");
        confirmLogInButton.setFont(buttonFont);
        confirmLogInButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickConfirmLogInButton");
        returnLogInPage = new JButton("Return");
        returnLogInPage.setFont(buttonFont);
        setupKeyBindings(returnLogInPage);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        logInPagePanel.add(usernameLabel, gbc);
        gbc.gridx++;
        logInPagePanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        logInPagePanel.add(passwordLabel, gbc);
        gbc.gridx++;
        logInPagePanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        logInPagePanel.add(confirmLogInButton, gbc);
        gbc.gridx = 10;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        logInPagePanel.add(returnLogInPage, gbc);
        gbc.gridy++;
    }

    private void setRegisterPageLayout() {
        registerPagePanel = new JPanel(new GridBagLayout());
        registerPagePanel.setBackground(Color.WHITE);
        JLabel insertUsernameLabel = new JLabel("Insert Username");
        insertUsernameLabel.setFont(textFont.deriveFont(Font.PLAIN, 18));
        insertUsernameField = new JTextField(15);
        JLabel usernameRequirementsLabel = new JLabel("<html>Username can't contain spaces.</html>");
        usernameRequirementsLabel.setFont(textFont.deriveFont(Font.PLAIN, 12));
        JLabel insertPasswordLabel = new JLabel("Insert Password");
        insertPasswordLabel.setFont(textFont.deriveFont(Font.PLAIN, 18));
        insertPasswordField = new JPasswordField(18);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(textFont.deriveFont(Font.PLAIN, 18));
        confirmPasswordField = new JPasswordField(18);
        JLabel passwordRequirementsLabel = new JLabel("<html>Password must be at least 8 characters long, contain at least one number, <br>one uppercase letter, and one special character.</html>");
        passwordRequirementsLabel.setFont(textFont.deriveFont(Font.PLAIN, 12));
        confirmRegisterButton = new JButton("Register");
        confirmRegisterButton.setFont(buttonFont);
        confirmRegisterButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickConfirmRegisterButton");
        returnRegisterPage = new JButton("Return");
        returnRegisterPage.setFont(buttonFont);
        setupKeyBindings(returnRegisterPage);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        registerPagePanel.add(insertUsernameLabel, gbc);
        gbc.gridx++;
        registerPagePanel.add(insertUsernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        registerPagePanel.add(usernameRequirementsLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPagePanel.add(insertPasswordLabel, gbc);
        gbc.gridx++;
        registerPagePanel.add(insertPasswordField, gbc);


        Icon eyeIcon = new ImageIcon(PATH+"/Icons/visiblePassword.png");
        JCheckBox showPasswordCheckBox = new JCheckBox();
        showPasswordCheckBox.setIcon(eyeIcon);
        showPasswordCheckBox.setSelectedIcon(eyeIcon);
        showPasswordCheckBox.setBorderPainted(false);
        showPasswordCheckBox.setContentAreaFilled(false);
        showPasswordCheckBox.setFocusPainted(false);
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setSelected(false);
        showPasswordCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                insertPasswordField.setEchoChar((char) 0);
                insertPasswordField.setFont(textFont);
                confirmPasswordField.setEchoChar((char) 0);
                confirmPasswordField.setFont(textFont);
            } else {
                insertPasswordField.setFont(new Font("Arial", Font.PLAIN, 12));
                insertPasswordField.setEchoChar('\u25CF');
                confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 12));
                confirmPasswordField.setEchoChar('\u25CF');
            }
        });

        gbc.gridx++;
        registerPagePanel.add(showPasswordCheckBox, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        registerPagePanel.add(confirmPasswordLabel, gbc);
        gbc.gridx++;
        registerPagePanel.add(confirmPasswordField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        registerPagePanel.add(passwordRequirementsLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPagePanel.add(confirmRegisterButton, gbc);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.gridx = 10;
        gbc.gridy = 9;
        registerPagePanel.add(returnRegisterPage, gbc);
    }

    private void setMainMenuPageLayout() {
        mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuPanel.setBackground(Color.WHITE);
        mainMenuPanel.setOpaque(true);
        JLabel menuTitleLabel = new JLabel("Main Menu");
        Font auxFont = titleFont.deriveFont(Font.BOLD, 50);
        menuTitleLabel.setFont(auxFont);
        playGameButton = new JButton("Play Game");
        continueGameButton = new JButton("Continue Game");
        importKenkenButton = new JButton("Import Kenken");
        createKenkenButton = new JButton("Create Kenken");
        settingsButton = new JButton("Settings");
        rulesButton = new JButton("Rules");
        statisticButton = new JButton("Statistics");
        rankingButton = new JButton("Ranking");

        Dimension buttonSize = new Dimension(140, 170);

        ImageIcon playGameIcon = new ImageIcon(PATH+"/Icons/playGame.jpeg");
        Image playGameImg = playGameIcon.getImage().getScaledInstance(130, 130,Image.SCALE_SMOOTH);
        Icon playGame = new ImageIcon(playGameImg);
        playGameButton.setIcon(playGame);
        playGameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playGameButton.setPreferredSize(buttonSize);
        playGameButton.setBackground(Color.WHITE);
        playGameButton.setOpaque(true);
        playGameButton.setFont(buttonFont);

        ImageIcon continueGameIcon = new ImageIcon(PATH+"/Icons/continueGame.jpeg");
        Image continueGameImg = continueGameIcon.getImage().getScaledInstance(130, 130,Image.SCALE_SMOOTH);
        Icon continueGame = new ImageIcon(continueGameImg);
        continueGameButton.setIcon(continueGame);
        continueGameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        continueGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        continueGameButton.setPreferredSize(buttonSize);
        continueGameButton.setBackground(Color.WHITE);
        continueGameButton.setOpaque(true);
        continueGameButton.setFont(buttonFont);

        ImageIcon importKenkenIcon = new ImageIcon(PATH+"/Icons/importKenken.jpeg");
        Image importKenkenImg = importKenkenIcon.getImage().getScaledInstance(130, 130,Image.SCALE_SMOOTH);
        Icon importKenken = new ImageIcon(importKenkenImg);
        importKenkenButton.setIcon(importKenken);
        importKenkenButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        importKenkenButton.setHorizontalTextPosition(SwingConstants.CENTER);
        importKenkenButton.setPreferredSize(buttonSize);
        importKenkenButton.setBackground(Color.WHITE);
        importKenkenButton.setOpaque(true);
        importKenkenButton.setFont(buttonFont);

        ImageIcon createKenkenIcon = new ImageIcon(PATH+"/Icons/createKenken2.jpg");
        Image createKenkenImg = createKenkenIcon.getImage().getScaledInstance(130, 130,Image.SCALE_SMOOTH);
        Icon createKenken = new ImageIcon(createKenkenImg);
        createKenkenButton.setIcon(createKenken);
        createKenkenButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        createKenkenButton.setHorizontalTextPosition(SwingConstants.CENTER);
        createKenkenButton.setPreferredSize(buttonSize);
        createKenkenButton.setBackground(Color.WHITE);
        createKenkenButton.setOpaque(true);
        createKenkenButton.setFont(buttonFont);

        ImageIcon statisticsIcon = new ImageIcon(PATH+"/Icons/statistics.jpeg");
        Image statisticsImg = statisticsIcon.getImage().getScaledInstance(130, 130,Image.SCALE_SMOOTH);
        Icon statistics = new ImageIcon(statisticsImg);
        statisticButton.setIcon(statistics);
        statisticButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        statisticButton.setHorizontalTextPosition(SwingConstants.CENTER);
        statisticButton.setPreferredSize(buttonSize);
        statisticButton.setBackground(Color.WHITE);
        statisticButton.setOpaque(true);
        statisticButton.setFont(buttonFont);

        ImageIcon rankingIcon = new ImageIcon(PATH+"/Icons/ranking.jpeg");
        Image rankingImg = rankingIcon.getImage().getScaledInstance(130, 130,Image.SCALE_SMOOTH);
        Icon ranking = new ImageIcon(rankingImg);
        rankingButton.setIcon(ranking);
        rankingButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        rankingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        rankingButton.setPreferredSize(buttonSize);
        rankingButton.setBackground(Color.WHITE);
        rankingButton.setOpaque(true);
        rankingButton.setFont(buttonFont);

        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        titlePanel.add(menuTitleLabel, gbc);
        mainMenuPanel.add(titlePanel, BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setOpaque(true);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.insets = new Insets(20, 20, 20, 20);

        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        playGameButton.setPreferredSize(buttonSize);
        buttonsPanel.add(playGameButton, buttonConstraints);

        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        continueGameButton.setPreferredSize(buttonSize);
        buttonsPanel.add(continueGameButton, buttonConstraints);

        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 0;
        importKenkenButton.setPreferredSize(buttonSize);
        buttonsPanel.add(importKenkenButton, buttonConstraints);

        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        createKenkenButton.setPreferredSize(buttonSize);
        buttonsPanel.add(createKenkenButton, buttonConstraints);

        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 1;
        statisticButton.setPreferredSize(buttonSize);
        buttonsPanel.add(statisticButton, buttonConstraints);

        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 1;
        rankingButton.setPreferredSize(buttonSize);
        buttonsPanel.add(rankingButton, buttonConstraints);
        mainMenuPanel.add(buttonsPanel, BorderLayout.CENTER);

    }
    
    private void setStatisticsPageLayout(){

        Vector<Integer> intStats = ctrlPresentation.getIntStats();
        Vector<String> stringStats = ctrlPresentation.getStringStats();

        statisticsPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Personal Statistics");
        titleLabel.setFont(titleFont);
        titlePanel.add(titleLabel);
        statisticsPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel personalStatisticsPanel = new JPanel(new GridBagLayout());
        personalStatisticsPanel.setBackground(Color.WHITE);
        personalStatisticsPanel.setOpaque(true);
        personalStatisticsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userName = new JLabel("Username: " );
        userName.setFont(textFont);
        JLabel totalPointsLabel = new JLabel("Total Points: \u003A ");
        totalPointsLabel.setFont(textFont);
        JLabel averagePointsPerGameLabel = new JLabel("Average Points Per Game: ");
        averagePointsPerGameLabel.setFont(textFont);
        JLabel bestPointsLabel = new JLabel("Best Points: ");
        bestPointsLabel.setFont(textFont);
        JLabel gamesWonLabel = new JLabel("Games Won: ");
        gamesWonLabel.setFont(textFont);
        JLabel gamesLostLabel = new JLabel("Games Lost: ");
        gamesLostLabel.setFont(textFont);
        JLabel gamesWonWithoutErrorsLabel = new JLabel("Games Won Without Errors: ");
        gamesWonWithoutErrorsLabel.setFont(textFont);
        JLabel gamesWonWithoutHintsLabel = new JLabel("Games Won Without Hints: ");
        gamesWonWithoutHintsLabel.setFont(textFont);
        JLabel gamesTotalLabel = new JLabel("Total Games: ");
        gamesTotalLabel.setFont(textFont);
        JLabel bestTimeLabel = new JLabel("Best Time: ");
        bestTimeLabel.setFont(textFont);
        JLabel averageTimeLabel = new JLabel("Average Time: ");
        averageTimeLabel.setFont(textFont);

        JLabel username = new JLabel(ctrlPresentation.getUsername());
        username.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel totalPointsValueLabel = new JLabel(intStats.get(0).toString());
        totalPointsValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel averagePointsPerGameValueLabel = new JLabel(intStats.get(1).toString());
        averagePointsPerGameValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel bestPointsValueLabel = new JLabel(intStats.get(2).toString());
        bestPointsValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel gamesWonValueLabel = new JLabel(intStats.get(3).toString());
        gamesWonValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel gamesLostValueLabel = new JLabel( intStats.get(4).toString());
        gamesLostValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel gamesWonWithoutErrorsValueLabel = new JLabel(intStats.get(5).toString());
        gamesWonWithoutErrorsValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel gamesWonWithoutHintsValueLabel = new JLabel(intStats.get(6).toString());
        gamesWonWithoutHintsValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        JLabel gamesTotalValueLabel = new JLabel(intStats.get(7).toString());
        gamesTotalValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));

        Duration duration = Duration.parse(stringStats.get(0));
        JLabel bestTimeValueLabel = new JLabel(duration.toHoursPart() + " hh " + duration.toMinutesPart() + " mm " + duration.toSecondsPart() + " ss");
        bestTimeValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
        duration = Duration.parse(stringStats.get(1));
        JLabel averageTimeValueLabel = new JLabel(duration.toHoursPart() + " hh " + duration.toMinutesPart() + " mm " + duration.toSecondsPart() + " ss");
        averageTimeValueLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 0;
        personalStatisticsPanel.add(userName, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(totalPointsLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(totalPointsValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(averagePointsPerGameLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(averagePointsPerGameValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(bestPointsLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(bestPointsValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(gamesWonLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(gamesWonValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(gamesLostLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(gamesLostValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(gamesWonWithoutErrorsLabel , gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(gamesWonWithoutErrorsValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(gamesWonWithoutHintsLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(gamesWonWithoutHintsValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(gamesTotalLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(gamesTotalValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(bestTimeLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(bestTimeValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        personalStatisticsPanel.add(averageTimeLabel, gbc);
        gbc.gridx++;
        personalStatisticsPanel.add(averageTimeValueLabel, gbc);

        personalStatisticsPanel.setBorder(null);
        JScrollPane scrollPane = new JScrollPane(personalStatisticsPanel);


        statisticsPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel returnPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Return");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(returnToMainMenu);
        setupKeyBindings(returnButton);
        GridBagConstraints gbcRet = new GridBagConstraints();
        gbcRet.gridx = 0;
        gbcRet.gridy = 0;
        gbcRet.weightx = 1;
        gbcRet.anchor = GridBagConstraints.EAST;
        returnPanel.add(returnButton, gbcRet);
        statisticsPanel.add(returnPanel, BorderLayout.SOUTH);
        statisticsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void setRankingPageLayout(){
        LinkedHashMap<String,Integer> ranking = ctrlPresentation.getRanking();
        Vector<Integer> userPos = ctrlPresentation.getUserPos();
        String username = ctrlPresentation.getUsername();
        Object[][] data = new Object[ranking.size()][3];
        int index = 0;
        for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
            data[index][0] = index + 1;
            data[index][1] = entry.getKey();
            data[index][2] = entry.getValue();
            index++;
        }

        String[] columnNames = {"Position", "Username", "Score"};

        JTable rankingTable = new JTable(data, columnNames);
        rankingTable.setFont(textFont);
        rankingTable.setEnabled(false);
        rankingTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(rankingTable);
        rankingTable.setFillsViewportHeight(true);

        rankingTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(subtitleTextFont);
                return label;
            }


        });

        JLabel userPositionLabel = new JLabel();
        if (!userPos.isEmpty()) {
            userPositionLabel = new JLabel("Your position is " + userPos.get(0) + " out of " + ctrlPresentation.getRkSize() + ", with a total of " + userPos.get(1) + " points");
            userPositionLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 15));
            userPositionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        rankingPanel = new JPanel(new BorderLayout());

        JLabel rankingTitle = new JLabel("Ranking");
        rankingTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rankingTitle.setFont(titleFont);
        rankingPanel.add(rankingTitle, BorderLayout.NORTH);

        rankingPanel.add(scrollPane, BorderLayout.CENTER);

        returnRankToMainButton = new JButton("Return");
        returnRankToMainButton.setFont(buttonFont);
        returnRankToMainButton.addActionListener(returnToMainMenu);
        setupKeyBindings(returnRankToMainButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(userPositionLabel, BorderLayout.NORTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(returnRankToMainButton, gbc);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        rankingPanel.add(bottomPanel, BorderLayout.PAGE_END);

        rankingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    private void setRulesPageLayout() {
        rulesPanel = new JPanel(new BorderLayout());

        JLabel rulesTitle = new JLabel("How to play Kenken for dummies");
        rulesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rulesTitle.setFont(titleFont.deriveFont(Font.BOLD, 35));
        rulesPanel.add(rulesTitle, BorderLayout.NORTH);

        String rules = ctrlPresentation.getRules();

        JTextArea rulesText = new JTextArea(rules);
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setFont(textFont);

        rulesPanel.add(new JScrollPane(rulesText), BorderLayout.CENTER);
        Border margin = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        rulesText.setBorder(margin);

        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnRulesToMainMenu = new JButton("Return");
        returnRulesToMainMenu.setFont(buttonFont);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        returnPanel.add(returnRulesToMainMenu, gbc);
        rulesPanel.add(returnPanel, BorderLayout.SOUTH);
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void setSettingsPageLayout() {
        settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBackground(Color.WHITE);
        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(titleFont);
        JLabel musicOnOffLabel = new JLabel("Background Music (on/off)");
        musicOnOffLabel.setFont(textFont);
        musicOnOff = new JCheckBox();
        musicOnOff.setSelected(true);
        returnSettingsToMainMenu = new JButton("Return");
        returnSettingsToMainMenu.setFont(buttonFont);
        setupKeyBindings(returnSettingsToMainMenu);
        musicVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        musicVolume.setMajorTickSpacing(10);
        musicVolume.setPaintTicks(true);
        musicVolume.setPaintLabels(true);
        musicVolume.setFont(textFont);

        String[] musicOptions = ctrlPresentation.getMusicOptions();
        musicSelection = new JComboBox<>(musicOptions);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setOpaque(true);
        titlePanel.add(settingsTitle);
        settingsPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel containerSettingsPanel = new JPanel(new GridBagLayout());
        containerSettingsPanel.setBackground(Color.WHITE);
        containerSettingsPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerSettingsPanel.add(musicOnOffLabel, gbc);
        gbc.gridx++;
        containerSettingsPanel.add(musicOnOff, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel musicSelectionLabel = new JLabel("Music Selection");
        musicSelectionLabel.setFont(textFont);
        containerSettingsPanel.add(musicSelectionLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        containerSettingsPanel.add(musicSelection, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel musicVolumeLabel = new JLabel("Music Volume");
        musicVolumeLabel.setFont(textFont);
        containerSettingsPanel.add(musicVolumeLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        containerSettingsPanel.add(musicVolume, gbc);
        settingsPanel.add(containerSettingsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.getInsets(new Insets(10, 10, 10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setOpaque(true);
        bottomPanel.add(returnSettingsToMainMenu, BorderLayout.EAST);
        settingsPanel.add(bottomPanel, BorderLayout.SOUTH);
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    private void setImportKenkenLayout() {
        importKenkenPanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Upload File");
        titleLabel.setFont(titleFont);
        fileNameField = new JTextField("");
        fileNameField.setEnabled(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        titlePanel.getInsets(new Insets(15, 10, 10, 10));
        importKenkenPanel.add(titlePanel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        importKenkenPanel.add(new JSeparator(), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String formatExplanation = ctrlPresentation.getFormatExplanation();
        formatExplanation = formatExplanation.replace("\\u00F7", "\u00F7");

        JTextArea format = new JTextArea(formatExplanation);
        format.setEditable(false);
        format.setLineWrap(true);
        format.setWrapStyleWord(true);
        format.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        format.setFont(textFont.deriveFont(Font.PLAIN, 12));
        importKenkenPanel.add(new JScrollPane(format), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        importKenkenPanel.add(fileNameField, gbc);

        gbc.gridx++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 0, 10);
        uploadButton = new JButton("Upload File");
        uploadButton.setFont(buttonFont);
        importKenkenPanel.add(uploadButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10);
        JLabel disclaimerLabel = new JLabel("Note: File must be in .txt format.");
        disclaimerLabel.setFont(textFont);
        importKenkenPanel.add(disclaimerLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        JPanel returnPanel = new JPanel(new BorderLayout());
        returnImportToMainMenu = new JButton("Return");
        returnImportToMainMenu.setFont(buttonFont);
        returnPanel.add(returnImportToMainMenu, BorderLayout.EAST);
        importKenkenPanel.add(returnPanel, gbc);
    }

    private void setTypeKenkenLayout() {
        typeKenkenPanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Import Kenken");
        titleLabel.setFont(titleFont);

        String formatExplanation = ctrlPresentation.getFormatExplanation();
        formatExplanation = formatExplanation.replace("\\u00F7", "\u00F7");

        JTextArea format = new JTextArea(formatExplanation);
        format.setEditable(false);
        format.setLineWrap(true);
        format.setWrapStyleWord(true);
        format.setFont(textFont.deriveFont(Font.PLAIN, 12));

        typeKenkenTextArea = new JTextArea();
        typeKenkenTextArea.setLineWrap(true);
        typeKenkenTextArea.setWrapStyleWord(true);
        typeKenkenTextArea.setRows(8);
        typeKenkenTextArea.setColumns(40);
        JScrollPane userInputScrollPane = new JScrollPane(typeKenkenTextArea);

        importTypedKenkenButton = new JButton("Import");
        importTypedKenkenButton.setFont(buttonFont);
        returnTypeToMainMenu = new JButton("Return");
        returnTypeToMainMenu.setFont(buttonFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        titlePanel.getInsets(new Insets(15, 10, 10, 10));
        typeKenkenPanel.add(titlePanel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        typeKenkenPanel.add(new JSeparator(), gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        typeKenkenPanel.add(new JScrollPane(format), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        typeKenkenPanel.add(userInputScrollPane, gbc);

        gbc.gridx++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 0, 10);
        typeKenkenPanel.add(importTypedKenkenButton, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        JPanel returnPanel = new JPanel(new BorderLayout());
        returnPanel.add(returnTypeToMainMenu, BorderLayout.EAST);
        typeKenkenPanel.add(returnPanel, gbc);

    }

    private void setKenkenSelectionMenuLayout(){

        kenkenSelectionMenuPanel = new JPanel(new BorderLayout());
        kenkenSelectionMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        kenkenSelectionMenuPanel.setBackground(Color.WHITE);
        kenkenSelectionMenuPanel.setOpaque(true);

        Vector<Integer> kenkenId = ctrlPresentation.getAvailableKenken();

        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setBackground(Color.WHITE);
        gridBagPanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JPanel buttonPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(buttonPanel.getMaximumSize().width, buttonPanel.getPreferredSize().height));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(true);

        int numColors = pastelColors.length;
        for (int i = 0; i < kenkenId.size(); i++) {
            JButton kenkenButton = new JButton("<html>Kenken <b> " + (i + 1)  + "</b> <br>" + "Size: " + kenkenId.get(i) + "x" + kenkenId.get(i) + "</html>");
            kenkenButton.setFont(buttonFont);
            kenkenButton.setBackground(pastelColors[i%numColors]);
            kenkenButton.setOpaque(true);
            kenkenButton.setBorderPainted(false);
            kenkenButton.setContentAreaFilled(true);
            kenkenButton.setPreferredSize(new Dimension(100, 100));
            int selectedIndex = i;
            kenkenButton.addActionListener(e -> {
                Vector<Vector<Integer>> board = ctrlPresentation.getBoard(selectedIndex);
                boolean available = ctrlPresentation.newGame(selectedIndex);
                if (available) {
                    setGamePagePanel(board, selectedIndex);
                } else {
                    String[] options = {"Continue Game", "Get back to selection", "Delete game and start a new one"};
                    int choice = JOptionPane.showOptionDialog(kenkenSelectionMenuPanel, "There's already a game with this Kenken started", "Game already exisits", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    if (choice == 0) {
                        Vector<Vector<Vector<Integer>>> partialSol = ctrlPresentation.continueGame(selectedIndex);
                        Vector<Vector<Integer>> board1 = ctrlPresentation.getBoard(selectedIndex);
                        setContinuePagePanel(board1, partialSol, selectedIndex);
                    } else if (choice == 2) {
                        ctrlPresentation.deleteGame(selectedIndex);
                        available = ctrlPresentation.newGame(selectedIndex);
                        if (available) {
                            setGamePagePanel(board, selectedIndex);
                        }
                    }
                }
            });
            buttonPanel.add(kenkenButton);
        }
        gridBagPanel.add(buttonPanel, gbc);

        JLabel titleLabel = new JLabel("Kenken Library", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        kenkenSelectionMenuPanel.add(titleLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(gridBagPanel);
        scrollPane.setBorder(null);
        kenkenSelectionMenuPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnButtonPanel.setBackground(Color.WHITE);
        returnButtonPanel.setOpaque(true);
        JButton returnButton = new JButton("Return");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(returnToMainMenu);
        setupKeyBindings(returnButton);
        returnButtonPanel.add(returnButton);
        kenkenSelectionMenuPanel.add(returnButtonPanel, BorderLayout.SOUTH);

    }

    private void setContinueGameLayout(){
        continueGamePanel = new JPanel(new BorderLayout());
        continueGamePanel.setBackground(Color.WHITE);
        continueGamePanel.setOpaque(true);
        Vector<Integer> gameIDs = ctrlPresentation.listGames();
        continueGamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setBackground(Color.WHITE);
        gridBagPanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JPanel buttonPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(buttonPanel.getMaximumSize().width, buttonPanel.getPreferredSize().height));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(true);

        int numColors = pastelColors.length;
        gameIDs.sort(Comparator.naturalOrder());
        for (int i = 0; i < gameIDs.size(); i++) {
            JButton gameButton = new JButton("<html> Game with kenken <b>" + (gameIDs.get(i)+1) + "</b> </html>");

            gameButton.setFont(buttonFont);
            gameButton.setBackground(pastelColors[i%numColors]);
            gameButton.setOpaque(true);
            gameButton.setBorderPainted(false);
            gameButton.setContentAreaFilled(true);
            gameButton.setPreferredSize(new Dimension(100, 100));
            int selectedIndex = i;
            gameButton.addActionListener(e -> {
                Vector<Vector<Vector<Integer>>> partialSol = ctrlPresentation.continueGame(gameIDs.get(selectedIndex));
                Vector<Vector<Integer>> board = ctrlPresentation.getBoard(gameIDs.get(selectedIndex));
                setContinuePagePanel(board, partialSol, gameIDs.get(selectedIndex));
            });
            buttonPanel.add(gameButton);
        }
        gridBagPanel.add(buttonPanel, gbc);

        JLabel titleLabel = new JLabel("Game Library", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        continueGamePanel.add(titleLabel, BorderLayout.NORTH);


        JScrollPane scrollPane = new JScrollPane(gridBagPanel);
        scrollPane.setBorder(null);

        continueGamePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnButtonPanel.setBackground(Color.WHITE);
        returnButtonPanel.setOpaque(true);
        JButton returnButton = new JButton("Return");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(returnToMainMenu);
        setupKeyBindings(returnButton);
        returnButtonPanel.add(returnButton);
        continueGamePanel.add(returnButtonPanel, BorderLayout.SOUTH);

    }

    private void showGenerationProgressDialog(JTextField sizeField, JCheckBox[] operationCheckBoxes) {
        JTextField originalSizeField = new JTextField();
        originalSizeField.setText(sizeField.getText());
        JCheckBox[] originalOperations = new JCheckBox[operationCheckBoxes.length];
        for (int i = 0; i < operationCheckBoxes.length; i++) {
            originalOperations[i] = new JCheckBox(operationCheckBoxes[i].getText());
            originalOperations[i].setSelected(operationCheckBoxes[i].isSelected());
        }

        JDialog dialog = new JDialog(this, "Generating Kenken...", true);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(this);

        ImageIcon loadingIcon = new ImageIcon(PATH+"/Icons/squareWaiting.gif");
        JLabel spinnerLabel = new JLabel(loadingIcon);
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        int iconWidth = loadingIcon.getIconWidth();
        int iconHeight = loadingIcon.getIconHeight();
        spinnerPanel.setBackground(new Color(0, 0, 0, 0));
        spinnerPanel.add(spinnerLabel);
        int dialogWidth = iconWidth + 85;
        int dialogHeight = iconHeight + 60;
        dialog.setSize(dialogWidth, dialogHeight);
        dialog.add(spinnerPanel, BorderLayout.CENTER);

        int size;
        try {
             size = Integer.parseInt(sizeField.getText());
        } catch (NumberFormatException e) {
            Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
            JOptionPane.showMessageDialog(createRandomKenkenPanel, "Please enter a valid size. Size must be an natural number > 2.", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
            dialog.dispose();
            return;
        }

        int[] opIds = ctrlPresentation.getOpIds();
        Vector<Integer> ops = new Vector<>();
        ops.add(opIds[0]);
        for (int k = 0; k < operationCheckBoxes.length; k++) {
            if (operationCheckBoxes[k].isSelected()) {
                ops.add(opIds[k+1]);
            }
        }

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(buttonFont);

        Thread gameGenerationThread;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        gameGenerationThread = new Thread(() -> {
            try {
                int kenkenId = ctrlPresentation.generateRandomKenken(size, ops);
                Thread.sleep(750);
                if (kenkenId < 0) {
                    dialog.dispose();
                    JOptionPane.showMessageDialog(createRandomKenkenPanel, "The Kenken you asked for could not be generated because the restrictions did not allow it to.", "Kenken not generated", JOptionPane.INFORMATION_MESSAGE, null);
                    setMainMenuPanel();
                    sizeField.setText("");
                    for (int k = 0; k < operationCheckBoxes.length; k++) {
                        if (operationCheckBoxes[k].isSelected()) {
                            operationCheckBoxes[k].setSelected(false);
                        }
                    }
                } else {
                    dialog.dispose();
                    JPanel containerPanel = new JPanel(new BorderLayout());
                    JPanel displayPanel = new JPanel();
                    KenkenDisplayPanel kenkenPanel = new KenkenDisplayPanel(ctrlPresentation, kenkenId);
                    kenkenPanel.updateCellSize(getWidth(), getHeight());
                    this.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            kenkenPanel.updateCellSize(getWidth(), getHeight());
                        }
                    });
                    displayPanel.add(kenkenPanel);
                    JLabel kenkenIdLabel = new JLabel("This is the id of the new Kenken: " + String.valueOf(kenkenId+1));
                    kenkenIdLabel.setFont(textFont);
                    displayPanel.add(kenkenIdLabel);
                    containerPanel.add(displayPanel, BorderLayout.CENTER);
                    JPanel buttonsPanel = new JPanel();
                    containerPanel.add(buttonsPanel, BorderLayout.SOUTH);
                    JButton returnButton = new JButton("Return to Main Menu");
                    returnButton.setFont(buttonFont.deriveFont(Font.PLAIN, 12));
                    returnButton.addActionListener(returnToMainMenu);
                    buttonsPanel.add(returnButton);
                    JButton playKenkenButton = new JButton("Play Kenken");
                    playKenkenButton.setFont(buttonFont.deriveFont(Font.PLAIN, 12));
                    playKenkenButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            boolean available = ctrlPresentation.newGame(kenkenId);
                            Vector<Vector<Integer>> board = ctrlPresentation.getBoard(kenkenId);
                            displayPanel.setVisible(false);
                            if (available) ctrlPresentation.startGameView(board, kenkenId);
                        }
                    });
                    buttonsPanel.add(playKenkenButton);
                    JButton generateAnotherButton = new JButton("Generate another");
                    generateAnotherButton.setFont(buttonFont.deriveFont(Font.PLAIN, 12));
                    generateAnotherButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            displayPanel.setVisible(false);
                            showGenerationProgressDialog(originalSizeField, originalOperations);
                        }
                    });
                    buttonsPanel.add(generateAnotherButton);
                    setContentPane(containerPanel);
                    setPreferredSize(new Dimension(getWidth(), getHeight()));
                    pack();
                    revalidate();
                    repaint();
                    setVisible(true);
                }

                sizeField.setText("");
                for (int k = 0; k < operationCheckBoxes.length; k++) {
                    if (operationCheckBoxes[k].isSelected()) {
                        operationCheckBoxes[k].setSelected(false);
                    }
                }

            } catch (KenkenAlreadyInPersistence er) {
                dialog.dispose();
                JOptionPane.showMessageDialog(createRandomKenkenPanel, "Kenken already exists.", "Kenken not generated", JOptionPane.INFORMATION_MESSAGE, null);
                setCreateRandomKenkenPanel();
            } catch (KenkenNotGenerated err) {
                dialog.dispose();
                sizeField.setText("");
                for (int k = 0; k < operationCheckBoxes.length; k++) {
                    if (operationCheckBoxes[k].isSelected()) {
                        operationCheckBoxes[k].setSelected(false);
                    }
                }
                JOptionPane.showMessageDialog(createRandomKenkenPanel, "The Kenken you asked for could not be generated. Please try again or change the settings.", "Kenken not generated", JOptionPane.INFORMATION_MESSAGE, null);
                setCreateRandomKenkenPanel();
            } catch (InterruptedException e) {
            }
        });

        gameGenerationThread.start();

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sizeField.setText("");
                for (JCheckBox operationCheckBox : operationCheckBoxes) {
                    if (operationCheckBox.isSelected()) {
                        operationCheckBox.setSelected(false);
                    }
                }
                if (gameGenerationThread != null) {
                    gameGenerationThread.interrupt();
                }
                dialog.dispose();
                setCreateRandomKenkenPanel();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeField.setText("");
                for (JCheckBox operationCheckBox : operationCheckBoxes) {
                    if (operationCheckBox.isSelected()) {
                        operationCheckBox.setSelected(false);
                    }
                }
                if (gameGenerationThread != null) {
                    gameGenerationThread.interrupt();
                }
                dialog.dispose();
                setCreateRandomKenkenPanel();
            }
        });

        dialog.setVisible(true);
    }

    private void setCreateRandomKenkenLayout() {
        createRandomKenkenPanel = new JPanel(new BorderLayout());
        createRandomKenkenPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createRandomKenkenPanel.setBackground(Color.WHITE);
        createRandomKenkenPanel.setOpaque(true);

        JLabel titleLabel = new JLabel("Create Random Kenken");
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createRandomKenkenPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setOpaque(true);

        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sizePanel.setBackground(Color.WHITE);
        sizePanel.setOpaque(true);
        JLabel sizeLabel = new JLabel("Size (min: 3, max: 100)");
        sizeLabel.setFont(textFont);
        JTextField sizeField = new JTextField(10);
        sizePanel.add(sizeLabel);
        sizePanel.add(sizeField);
        centerPanel.add(sizePanel, BorderLayout.NORTH);

        JPanel operationsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcOp = new GridBagConstraints();
        operationsPanel.setBackground(Color.WHITE);
        operationsPanel.setOpaque(true);
        TitledBorder titleBorder = BorderFactory.createTitledBorder("Operations");
        titleBorder.setTitleFont(subtitleTextFont);
        JLabel opTitle = new JLabel("Operations");
        opTitle.setFont(subtitleTextFont);
        operationsPanel.setBorder(titleBorder);
        String[] operationNames = ctrlPresentation.getOpNames();
        JCheckBox[] operationCheckBoxes = new JCheckBox[operationNames.length-1];
        gbcOp.anchor = GridBagConstraints.WEST;
        gbcOp.fill = GridBagConstraints.HORIZONTAL;
        gbcOp.insets = new Insets(10, 10, 10, 50);
        gbcOp.gridx = 0;
        gbcOp.gridy = 0;
        gbcOp.weightx = 1.0;
        gbcOp.weighty = 0;
        for (int i = 0; i < operationCheckBoxes.length; i++) {
            operationCheckBoxes[i] = new JCheckBox(operationNames[i+1]);
            operationCheckBoxes[i].setFont(textFont);
            operationsPanel.add(operationCheckBoxes[i], gbcOp);
            if (i%2 == 0) gbcOp.gridx++;
            else {
                gbcOp.gridy++;
                gbcOp.gridx = 0;
            }
        }
        gbcOp.gridx = 0;
        gbcOp.gridy++;
        gbcOp.gridwidth = 2;
        gbcOp.weighty = 1.0;
        JPanel spacer = new JPanel();
        spacer.setBackground(Color.WHITE);
        spacer.setOpaque(true);
        operationsPanel.add(spacer, gbcOp);

        centerPanel.add(operationsPanel, BorderLayout.CENTER);

        createRandomKenkenPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridBagLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel createButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createButtonPanel.setBackground(Color.WHITE);
        createButtonPanel.setOpaque(true);
        createButton = new JButton("Create");
        createButton.setFont(buttonFont);
        createButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sizeField.getText() != null && !Objects.equals(sizeField.getText(), "")) {
                    try {
                        if (Integer.parseInt(sizeField.getText()) < 3 || Integer.parseInt(sizeField.getText()) > 100) {
                            Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                            JOptionPane.showMessageDialog(registerPagePanel, "The provided size is invalid.", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
                            sizeField.setText("");
                        } else showGenerationProgressDialog(sizeField, operationCheckBoxes);
                    } catch (NumberFormatException ex) {
                        Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                        JOptionPane.showMessageDialog(registerPagePanel, "The provided size is invalid.", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
                        sizeField.setText("");
                    }
                }
            }
        });
        createButtonPanel.add(createButton);
        gbc.weightx = 1.0;
        southPanel.add(createButtonPanel, gbc);

        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        returnButtonPanel.setBackground(Color.WHITE);
        returnButtonPanel.setOpaque(true);
        returnCreateRandomKenkenToMainMenu = new JButton("Return");
        returnCreateRandomKenkenToMainMenu.setFont(buttonFont);
        returnCreateRandomKenkenToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sizeField.setText("");
                for (int k = 0; k < operationCheckBoxes.length; k++) {
                    if (operationCheckBoxes[k].isSelected()) {
                        operationCheckBoxes[k].setSelected(false);
                    }
                }
            }
        });
        returnCreateRandomKenkenToMainMenu.addActionListener(returnToMainMenu);
        returnButtonPanel.add(returnCreateRandomKenkenToMainMenu);
        gbc.weightx = 0.0;
        gbc.gridx++;
        southPanel.add(returnButtonPanel, gbc);
        createRandomKenkenPanel.add(southPanel, BorderLayout.SOUTH);

    }

    private void setupKeyBindings(JButton button) {
        returnToMainMenuKeyboard = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        };

        button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "clickReturnButton");
        button.getActionMap().put("clickReturnButton", returnToMainMenuKeyboard);
    }

    private void createMultipleUseListeners() {
        exitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };


        returnAppEntranceAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAppEntrancePanel();
                ctrlPresentation.logOut();
            }
        };

        returnToMainMenu = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMainMenuPanel();
            }
        };

        returnToPreviousPanel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(previousPanelId, "AppEntrance")) setAppEntrancePanel();
                else if (Objects.equals(previousPanelId, "Statistics")) setStatisticsPanel();
                else if (Objects.equals(previousPanelId, "MainMenu")) setMainMenuPanel();
                else if (Objects.equals(previousPanelId, "Ranking")) setRankingPagePanel();
                else if (Objects.equals(previousPanelId, "KenkenSelectionMenu")) setKenkenSelectionMenuPanel();
                else if (Objects.equals(previousPanelId, "ContinueGame")) setContinueGamePanel();
                else {
                    setContentPane(previousPanel);
                    setPreferredSize(new Dimension(getWidth(), getHeight()));
                    pack();
                    revalidate();
                    repaint();
                    setVisible(true);
                }
            }
        };
    }

    private void initialiseListeners() {

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLogInPagePanel();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRegisterPagePanel();
            }
        });

        statisticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStatisticsPanel();
            }
        });

        confirmRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = insertUsernameField.getText();
                String password = new String(insertPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                boolean success = ctrlPresentation.registerIntoApp(username, password, confirmPassword);
                if (success) {
                    Icon customIcon = new ImageIcon(PATH+"/Icons/registerIcon.png");
                    JOptionPane.showMessageDialog(registerPagePanel, "Registered successfully! Welcome to Kenken Infinity!", "Register", JOptionPane.INFORMATION_MESSAGE, customIcon);
                    insertUsernameField.setText("");
                    insertPasswordField.setText("");
                    confirmPasswordField.setText("");
                    setMainMenuPanel();
                }
                else {
                    Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                    JOptionPane.showMessageDialog(registerPagePanel, "Unsuccessful register try again!", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
                }
            }
        });

        confirmRegisterButton.getActionMap().put("clickConfirmRegisterButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmRegisterButton.doClick();
            }
        });

        confirmLogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username != null && !username.isEmpty() && !password.isEmpty()) {
                    boolean success = ctrlPresentation.logIntoApp(username, password);
                    if (success) {
                        usernameField.setText("");
                        passwordField.setText("");
                        setMainMenuPanel();
                    } else {
                        Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                        JOptionPane.showMessageDialog(logInPagePanel, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
                    }
                }
            }
        });

        confirmLogInButton.getActionMap().put("clickConfirmLogInButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmLogInButton.doClick();
            }
        });

        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRankingPagePanel();
            }
        });

        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRulesPagePanel();
            }
        });

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Are you sure you wish to log out?";
                Icon customIcon = new ImageIcon(PATH+"/Icons/logOutIcon.png");
                String[] options = {"Yes", "No"};
                int choice = JOptionPane.showOptionDialog(mainMenuPanel, message, "Log out", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, customIcon, options, options[0]);
                if (choice == 0) {
                    ctrlPresentation.logOut();
                    setAppEntrancePanel();
                }
            }
        });

        eliminateProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Are you sure you wish to eliminate your profile?";
                Icon customIcon = new ImageIcon(PATH+"/Icons/eliminateProfileIcon.png");
                String[] options = {"Yes", "No"};
                int choice = JOptionPane.showOptionDialog(mainMenuPanel, message, "Eliminate profile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, customIcon, options, options[0]);
                if (choice == 0) {
                    ctrlPresentation.eliminateProfile();
                    setAppEntrancePanel();
                }
            }
        });

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSettingsPagePanel();
            }
        });

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRulesPagePanel();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSettingsPagePanel();
            }
        });

        musicOnOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicOnOff.isSelected()) {
                    ctrlPresentation.startMusic();
                }
                else {
                    ctrlPresentation.stopMusic();
                }
            }
        });

        importKenkenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Choose between uploading a file or typing the input";
                String[] options = {"Upload", "Type"};
                Icon customIcon = new ImageIcon(PATH+"/Icons/uploadIcon.png");
                int choice = JOptionPane.showOptionDialog(mainMenuPanel, message, "Import Kenken", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, customIcon, options, options[0]);
                if (choice == 0) setImportKenkenPanel();
                else if (choice == 1) setTypeKenkenPanel();
            }
        });

        playGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setKenkenSelectionMenuPanel();
            }
        });

        continueGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContinueGamePanel();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showOpenDialog(importKenkenPanel);
                File selectedFile = fileChooser.getSelectedFile();

                Icon customIcon;
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                        customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                        JOptionPane.showMessageDialog(importKenkenPanel, "Invalid file format. Please select a .txt file.", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
                        fileNameField.setText("");
                    }
                    else {
                        fileNameField.setText(selectedFile.getAbsolutePath());
                        try {
                            boolean success = ctrlPresentation.importKenken(fileNameField.getText());
                            if (success) {
                                customIcon = new ImageIcon(PATH + "/Icons/successfulIcon.png");
                                JOptionPane.showMessageDialog(logInPagePanel, "The kenken was imported successfully.", "Kenken Imported", JOptionPane.INFORMATION_MESSAGE, customIcon);
                                fileNameField.setText("");
                            } else {
                                customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                                JOptionPane.showMessageDialog(logInPagePanel, "The kenken you provided was invalid.", "Error", JOptionPane.ERROR_MESSAGE, customIcon);
                                fileNameField.setText("");
                            }
                        }
                        catch (KenkenAlreadyInPersistence er) {
                            showErrorMessage(er.getMessage());
                            fileNameField.setText("");
                        }
                    }
                }
            }
        });

        importTypedKenkenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newKenken = typeKenkenTextArea.getText();
                String auxiliaryFilePath = PATH+"/KenkenAux.txt";
                ctrlPresentation.clearFile(auxiliaryFilePath);
                ctrlPresentation.writeToFile(auxiliaryFilePath, newKenken);
                try {
                    boolean success = ctrlPresentation.importKenken(auxiliaryFilePath);
                    if (success) {
                        Icon customIcon = new ImageIcon(PATH+"/Icons/successfulIcon.png");
                        JOptionPane.showMessageDialog(logInPagePanel, "The kenken was imported successfully.", "Successful import", JOptionPane.INFORMATION_MESSAGE, customIcon);
                    } else {
                        Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                        JOptionPane.showMessageDialog(logInPagePanel, "The kenken you provided was invalid.", "Invalid Kenken", JOptionPane.ERROR_MESSAGE, customIcon);
                    }
                }
                catch (KenkenAlreadyInPersistence er) {
                    showErrorMessage(er.getMessage());
                }
            }
        });

        createKenkenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Choose between generating a random kenken or a personalized one";
                String[] options = {"Random", "Personalized"};
                Icon customIcon = new ImageIcon(PATH+"/Icons/createIcon.png");
                int choice = JOptionPane.showOptionDialog(mainMenuPanel, message, "Create Kenken", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, customIcon, options, options[0]);
                if (choice == 0) {
                    setCreateRandomKenkenPanel();
                }
                else if (choice == 1) {
                    customIcon = new ImageIcon(PATH+"/Icons/sizeIcon.png");
                    String personalized = "Please insert the size of the Kenken. Note that it must be at least 3.";
                    String userInput = (String) JOptionPane.showInputDialog(mainMenuPanel, personalized, null, JOptionPane.PLAIN_MESSAGE, customIcon, null, null);

                    if (userInput != null) {
                        try {
                            int size = Integer.parseInt(userInput);

                            if (size < 3) {
                                customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                                JOptionPane.showMessageDialog(mainMenuPanel, "Size must be a greater than or equal to 3.", "Invalid input", JOptionPane.ERROR_MESSAGE, customIcon);
                            }
                            else if (size > 100) {
                                customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                                JOptionPane.showMessageDialog(mainMenuPanel, "Size must be a smaller than or equal to 100.", "Invalid input", JOptionPane.ERROR_MESSAGE, customIcon);
                            }
                            else {
                                setCreatePersonalizedKenkenPage(size);
                            }
                        } catch (NumberFormatException er) {
                            customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
                            JOptionPane.showMessageDialog(mainMenuPanel, "Invalid input. Please enter a valid integer.", "Invalid input", JOptionPane.ERROR_MESSAGE, customIcon);
                        }
                    }
                }
            }
        });

        musicVolume.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int volume = source.getValue();
                float volumeFraction = volume / 100f;
                ctrlPresentation.setMusicVolume(volumeFraction);
            }
        });

        Action toggleMusicAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicOnOff.isSelected()) {
                    musicOnOff.setSelected(false);
                    ctrlPresentation.stopMusic();
                }
                else {
                    musicOnOff.setSelected(true);
                    ctrlPresentation.startMusic();
                }
            }
        };

        JRootPane rootPane = this.getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK), "toggleMusic");
        actionMap.put("toggleMusic", toggleMusicAction);

        musicSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMusic = (String) musicSelection.getSelectedItem();
                ctrlPresentation.selectMusic(selectedMusic);
            }
        });

        returnImportToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileNameField.setText("");
            }
        });

        returnTypeToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeKenkenTextArea.setText("");
            }
        });

        returnLogInPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        returnRegisterPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertUsernameField.setText("");
                insertPasswordField.setText("");
                confirmPasswordField.setText("");
            }
        });

        createMultipleUseListeners();

        exitApp.addActionListener(exitAction);

        returnImportToMainMenu.addActionListener(returnToMainMenu);
        setupKeyBindings(returnImportToMainMenu);
        returnLogInPage.addActionListener(returnAppEntranceAction);
        returnRegisterPage.addActionListener(returnAppEntranceAction);
        returnSettingsToMainMenu.addActionListener(returnToPreviousPanel);
        returnRulesToMainMenu.addActionListener(returnToPreviousPanel);
        setupKeyBindings(returnRulesToMainMenu);
        returnImportToMainMenu.addActionListener(returnToMainMenu);
        setupKeyBindings(returnImportToMainMenu);
        returnTypeToMainMenu.addActionListener(returnToMainMenu);
        setupKeyBindings(returnTypeToMainMenu);
        returnCreateRandomKenkenToMainMenu.addActionListener(returnToMainMenu);
        setupKeyBindings(returnCreateRandomKenkenToMainMenu);

    }

    public void setAppEntrancePanel() {
        menuBar.remove(profileMenu);
        setContentPane(appEntrancePanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = appEntrancePanel;
        previousPanelId = "AppEntrance";
    }

    private void setLogInPagePanel() {
        setContentPane(logInPagePanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = logInPagePanel;
        previousPanelId = "Other";
    }

    private void setStatisticsPanel() {
        setStatisticsPageLayout();
        setContentPane(statisticsPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = statisticsPanel;
        previousPanelId = "Statistics";
    }

    private void setRegisterPagePanel() {
        setContentPane(registerPagePanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = registerPagePanel;
        previousPanelId = "Other";
    }

    public void setMainMenuPanel() {
        menuBar.remove(helpMenu);
        menuBar.add(profileMenu);
        menuBar.add(helpMenu);
        setContentPane(mainMenuPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = mainMenuPanel;
        previousPanelId = "MainMenu";
    }

    private void setRankingPagePanel(){
        setRankingPageLayout();
        setContentPane(rankingPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = rankingPanel;
        previousPanelId = "Ranking";
    }

    private void setRulesPagePanel() {
        setContentPane(rulesPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanelId = "Other";
    }

    private void setSettingsPagePanel() {
        setContentPane(settingsPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanelId = "Other";
    }

    private void setImportKenkenPanel() {
        setContentPane(importKenkenPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = importKenkenPanel;
        previousPanelId = "Other";
    }

    private void setTypeKenkenPanel() {
        setContentPane(typeKenkenPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = typeKenkenPanel;
        previousPanelId = "Other";
    }

    private void setKenkenSelectionMenuPanel(){
        setKenkenSelectionMenuLayout();
        setContentPane(kenkenSelectionMenuPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = kenkenSelectionMenuPanel;
        previousPanelId = "KenkenSelectionMenu";
    }

    private void setContinueGamePanel(){
        setContinueGameLayout();
        setContentPane(continueGamePanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = continueGamePanel;
        previousPanelId = "ContinueGame";
    }

    private void setGamePagePanel(Vector<Vector<Integer>> board, int kenkenId){
        ctrlPresentation.startGameView(board, kenkenId);
    }

    private void setContinuePagePanel(Vector<Vector<Integer>> board, Vector<Vector<Vector<Integer>>> partialSol, int kenkenId){
        ctrlPresentation.continueGameView(board, partialSol, kenkenId);
    }

    private void setCreateRandomKenkenPanel() {
        setContentPane(createRandomKenkenPanel);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        pack();
        revalidate();
        repaint();
        setVisible(true);
        previousPanel = createRandomKenkenPanel;
        previousPanelId = "Other";
    }

    private void setCreatePersonalizedKenkenPage(int size) {
        ctrlPresentation.createGeneratorView(size);
    }

    public void showErrorMessage(String s) {
        Icon customIcon = new ImageIcon(PATH + "/Icons/wrongIcon.png");
        JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE, customIcon);
    }
}
