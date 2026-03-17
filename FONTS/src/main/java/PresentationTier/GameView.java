
package PresentationTier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView extends JFrame {

    private JPanel mainPanel;
    private CellPanel[][] gameBoard;
    private JTextField selectedCell;
    private int[] selectedCellCoords;
    CtrlPresentation ctrlP;
    private boolean noteMode = false;
    private Instant start;
    private Timer timer;
    private JLabel timerLabel;
    private JButton pauseButton;
    private JButton restartButton;
    private JButton askHint;
    private JLabel livesLabel;
    private JLabel hintsLabel;
    private JPanel boardPanel;
    private int boardSize;
    private int regionNumber;
    Vector<Vector<Integer>> board;
    private int idKenken;
    private int KKSize;
    private Color cellSelectedColor;
    private Color regionSelectedColor;
    private Color cellNotedColor;
    private Color regionNotedColor;
    private Color correctAnswerColor;
    private Duration elapsedTime = Duration.ZERO;
    private JPanel gamePanel;
    private String startTimeF;

    private Font buttonFont;
    private Font textFont;

    public GameView(CtrlPresentation ctrlPresentation, Vector<Vector<Integer>> board1, int kenkenId, String startTime) {
        setMinimumSize(new Dimension(500, 500));


        ctrlP = ctrlPresentation;
        board = board1;
        idKenken = kenkenId;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        KKSize = board.get(0).get(0);
        startTimeF = startTime;
        if (KKSize > 7) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setSize(800, 800);
        }
        cellSelectedColor = new Color(133, 168, 255, 255);
        regionSelectedColor = new Color(176, 207, 251, 255);
        cellNotedColor = new Color(255, 173, 193);
        regionNotedColor = new Color(255, 205, 221);
        correctAnswerColor = new Color(163, 255, 155);
        initializeFonts();
        pack();
        setTitle("Game with Kenken " + (kenkenId+1));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        askHint = new JButton("Ask a Hint");
        askHint.setFont(buttonFont);
        restartButton = new JButton("Restart");
        restartButton.setFont(buttonFont);
        restartButton.addActionListener(e -> {
            timer.stop();
            String[] options = {"Restart", "Get back to game"};
            int choice = JOptionPane.showOptionDialog(mainPanel, "Are you sure you want to restart the game? No progress will be saved", "Restart", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                ctrlP.restartGame();
                int lives = ctrlP.getLivesRemaining();
                if (lives != -1) livesLabel.setText("LIVES: " + lives);
                hintsLabel.setText("HINTS: " + (10 - ctrlP.getHintsRemaining()));
                restartGame(Duration.parse(startTimeF));
            } else {
                timer.start();
            }
        });

        pauseButton = new JButton("Exit");
        pauseButton.setFont(buttonFont);
        pauseButton.addActionListener(e -> {
            timer.stop();
            elapsedTime = Duration.between(start, Instant.now()).plus(elapsedTime);
            ctrlP.pauseGame();
            String[] options = {"Resume", "Save and Exit"};
            int choice = JOptionPane.showOptionDialog(mainPanel, "Game paused. Choose resume or save and exit.", "Game Paused", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == 0) {

                ctrlP.resumeGame();
                start = Instant.now();
                timer.start();
            } else {
                selectedCell = null;
                selectedCellCoords = null;
                ctrlP.closeGame();
                navigateMainMenu();

            }
        });

        pauseButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "clickPauseButton");
        pauseButton.getActionMap().put("clickPauseButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseButton.doClick();
            }
        });

        JToggleButton noteModeButton = new JToggleButton("Note Mode");
        noteModeButton.setFont(buttonFont);
        noteModeButton.addActionListener(e -> {
            noteMode = noteModeButton.isSelected();
            if (selectedCell != null) {
                int x = ((CellPanel) selectedCell.getParent().getParent()).getXCoord();
                int y = ((CellPanel) selectedCell.getParent().getParent()).getYCoord();
                if (noteMode) {
                    paintRegion(x, y, cellNotedColor, regionNotedColor);
                }
                else paintRegion(selectedCellCoords[0], selectedCellCoords[1], cellSelectedColor, regionSelectedColor);
            }
        });

        createBoardPanel();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.min(getWidth(), getHeight()) / boardSize;
                for (CellPanel[] cellRow : gameBoard) {
                    for (CellPanel cell : cellRow) {
                        cell.setPreferredSize(new Dimension(size, size));
                    }
                }
                boardPanel.revalidate();
                boardPanel.repaint();
            }
        });

        int buttonSize = 50;
        int grid = (int) Math.ceil(Math.sqrt(boardSize));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(grid, grid));
        for (int i = 1; i <= boardSize; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setFont(buttonFont.deriveFont(Font.BOLD, 20));
            numberButton.setPreferredSize(new Dimension(buttonSize, buttonSize));

            if (KKSize <= 9) {
                numberButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(String.valueOf(i)), "click"+String.valueOf(i));
                numberButton.getActionMap().put("click"+String.valueOf(i), new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numberButton.doClick();
                    }
                });
            }

            numberButton.addActionListener(e -> {
                if (selectedCell != null) {
                    CellPanel cellPanel = (CellPanel) selectedCell.getParent().getParent();
                    if (noteMode) {
                        ctrlP.fillNote(selectedCellCoords[0], selectedCellCoords[1], Integer.parseInt(numberButton.getText()));
                        cellPanel.updateNoteField(ctrlP.getSquareValue(selectedCellCoords[0], selectedCellCoords[1]));

                    } else {
                        cellPanel.getNoteField().setVisible(false);
                        selectedCell.setText(numberButton.getText());

                        if (!ctrlP.isGameEnded()) ((CellPanel) selectedCell.getParent().getParent()).updateNoteField(ctrlP.getSquareValue(selectedCellCoords[0], selectedCellCoords[1]));
                        boolean correctAnswer = ctrlP.fillSquare(selectedCellCoords[0], selectedCellCoords[1], Integer.parseInt(numberButton.getText()));
                        if (correctAnswer) {
                            paintRegion(selectedCellCoords[0], selectedCellCoords[1], correctAnswerColor, Color.WHITE);
                            if (!ctrlP.isGameEnded()) ((CellPanel) selectedCell.getParent().getParent()).updateNoteField(ctrlP.getSquareValue(selectedCellCoords[0], selectedCellCoords[1]));
                            selectedCell.setEnabled(false);
                            selectedCell = null;
                            selectedCellCoords = null;

                            if (ctrlP.isGameEnded()) {
                               gameWon();
                            }

                        } else {
                            paintRegion(selectedCellCoords[0], selectedCellCoords[1], Color.WHITE, Color.WHITE);
                            selectedCell.setText(null);
                            String PATH = "./Data";
                            ImageIcon original = new ImageIcon(PATH+"/Icons/failureAnimation.gif");
                            float scaleFactor = 0.5f;
                            ImageIcon failureAnimation = new ImageIcon(original.getImage().getScaledInstance((int) (original.getIconWidth()*scaleFactor), (int) (original.getIconHeight()*scaleFactor), Image.SCALE_DEFAULT));
                            JOptionPane.showMessageDialog(mainPanel, "Incorrect answer. Please try again.", "Wrong answer", JOptionPane.INFORMATION_MESSAGE, failureAnimation);
                            cellPanel.getNoteField().setVisible(true);
                            if (!ctrlP.isGameEnded())
                                ((CellPanel) selectedCell.getParent().getParent()).updateNoteField(ctrlP.getSquareValue(selectedCellCoords[0], selectedCellCoords[1]));
                            selectedCell = null;
                            selectedCellCoords = null;
                            int lives = ctrlP.getLivesRemaining();
                            if (lives != -1) livesLabel.setText("LIVES: " + lives);
                            else {

                                livesLabel.setText("LIVES: 0");
                                timer.stop();
                                original = new ImageIcon(PATH+"/Icons/deadInsideAnimation.gif");
                                ImageIcon gameLostAnimation = new ImageIcon(original.getImage().getScaledInstance((int) (original.getIconWidth()*scaleFactor), (int) (original.getIconHeight()*scaleFactor), Image.SCALE_DEFAULT));
                                String[] options = {"Retry", "Main menu"};
                                int choice = JOptionPane.showOptionDialog(mainPanel, "Game over. You have run out of lives", "Game over", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, gameLostAnimation, options, options[0]);
                                if (choice == 0) {
                                    ctrlP.newGame(idKenken);
                                    ctrlP.startGameView(board, idKenken);
                                    this.setVisible(false);
                                } else {
                                    navigateMainMenu();
                                }
                            }
                        }
                    }
                }

            });
            buttonPanel.add(numberButton);

        }

        timerLabel = new JLabel("TIME: ");
        InitTimer(Duration.parse(startTimeF));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setFont(buttonFont.deriveFont(Font.BOLD, 20));

        livesLabel = new JLabel("LIVES: " + ctrlP.getLivesRemaining());
        livesLabel.setHorizontalAlignment(JLabel.CENTER);
        livesLabel.setFont(buttonFont.deriveFont(Font.BOLD, 20));

        hintsLabel = new JLabel("HINTS: " + (10 - ctrlP.getHintsRemaining()));
        hintsLabel.setHorizontalAlignment(JLabel.CENTER);
        hintsLabel.setFont(buttonFont.deriveFont(Font.BOLD, 20));

        askHint.addActionListener(e -> {
            Vector<Integer> hintLoc = ctrlP.askForHint();
            if (!hintLoc.isEmpty()) {
                if (ctrlP.isGameEnded()) {
                 gameWon();
                } else {
                    hintsLabel.setText("HINTS : " + (10 - ctrlP.getHintsRemaining()));
                    if (selectedCell != null) {
                        int x = ((CellPanel) selectedCell.getParent().getParent()).getXCoord();
                        int y = ((CellPanel) selectedCell.getParent().getParent()).getYCoord();
                        paintRegion(x, y, Color.WHITE, Color.WHITE);
                    }
                    selectedCell = null;
                    selectedCellCoords = null;

                    Vector<Integer> hint = ctrlP.getSquareValue(hintLoc.get(0), hintLoc.get(1));
                    int value = hint.get(0);

                    CellPanel cellPanel = gameBoard[hintLoc.get(0)][hintLoc.get(1)];
                    cellPanel.getNoteField().setVisible(false);
                    cellPanel.getCell().setText(value + "");
                    cellPanel.getCell().setVisible(true);
                    cellPanel.changeColor(correctAnswerColor);
                    cellPanel.getCell().setForeground(Color.BLACK);
                    cellPanel.getCell().setEnabled(false);
                    cellPanel.updateNoteField(ctrlP.getSquareValue(hintLoc.get(0), hintLoc.get(1)));

                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "No hints remaining.");
                hintsLabel.setText("Hints: 0");
            }


        });

        JPanel gameMenuPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        gameMenuPanel.setLayout(new BoxLayout(gameMenuPanel, BoxLayout.Y_AXIS));
        gameMenuPanel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, Integer.MAX_VALUE)));

        gameMenuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gameMenuPanel.add(timerLabel);
        gameMenuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gameMenuPanel.add(livesLabel);
        gameMenuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gameMenuPanel.add(hintsLabel);
        gameMenuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gameMenuPanel.add(askHint);
        gameMenuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gameMenuPanel.add(noteModeButton);

        JPanel pauseRestartPanel = new JPanel();
        pauseRestartPanel.setLayout(new BoxLayout(pauseRestartPanel, BoxLayout.Y_AXIS));
        pauseRestartPanel.add(restartButton);
        pauseRestartPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        pauseRestartPanel.add(pauseButton);
        pauseRestartPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        menuPanel.add(gameMenuPanel, BorderLayout.NORTH);
        menuPanel.add(pauseRestartPanel, BorderLayout.SOUTH);
        Border margin = BorderFactory.createEmptyBorder(0, 20, 0, 10);
        menuPanel.setBorder(margin);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(buttonPanel, BorderLayout.SOUTH);
        Border emptyMargin = BorderFactory.createEmptyBorder(10,10,10,10);
        gamePanel.setBorder(emptyMargin);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel, BorderLayout.EAST);

        add(mainPanel);
    }



    public GameView(CtrlPresentation ctrlPresentation, Vector<Vector<Integer>> board1, int kenkenId, Vector<Vector<Vector<Integer>>> partialsol, String startTime) {

        this(ctrlPresentation, board1, kenkenId, startTime);

        for (int i = 0; i < partialsol.size(); i++) {
            for (int j = 0; j < partialsol.get(i).size(); ++j) {
                int value = partialsol.get(i).get(j).get(0);
                if (value != 0) {
                    CellPanel cellPanel = (CellPanel) gameBoard[i][j];
                    cellPanel.getCell().setText(value + "");
                    cellPanel.getCell().setVisible(true);
                    cellPanel.getNoteField().setVisible(false);
                    cellPanel.changeColor(correctAnswerColor);
                    cellPanel.getCell().setEnabled(false);

                }
                gameBoard[i][j].updateNoteField(ctrlP.getSquareValue(i, j));
            }

        }


    }

    private void initializeFonts() {

        String path = "./Data";
        Icon customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");

        try {
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File(path+"/Lettering/timeBurner.ttf"));
            buttonFont = buttonFont.deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(buttonFont);
            UIManager.put("OptionPane.buttonFont", new FontUIResource(buttonFont));
        } catch (IOException | FontFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, customIcon);
        }
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File(path+"/Lettering/cartesian.otf"));
            textFont = textFont.deriveFont(Font.PLAIN, 15);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(textFont);
            UIManager.put("OptionPane.messageFont", new FontUIResource(textFont));
        } catch (IOException | FontFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, customIcon);
        }
    }

    public void InitTimer(Duration StartTime) {

        start = Instant.now();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Duration duration = Duration.between(start, Instant.now()).plus(StartTime).plus(elapsedTime);;
                timerLabel.setText("TIME: " + duration.toMinutes() + "m " + duration.toSecondsPart() + "s");
            }
        });

        timer.start();
    }

    class CellPanel extends JPanel {
        int xCoord;
        int yCoord;
        JPanel noteField;
        JTextField cell;
        JLabel infoLabel;

        public CellPanel(JTextField cell, int x, int y) {
            super();
            this.xCoord = x;
            this.yCoord = y;
            this.cell = cell;
            this.infoLabel = new JLabel();

            setLayout(new BorderLayout());
            cell.setHorizontalAlignment(JTextField.CENTER);
            int fontSize = Math.max(5, 40 - 2 * boardSize);
            cell.setFont(buttonFont.deriveFont(Font.BOLD, fontSize));
            cell.setForeground(Color.BLACK);
            cell.setDisabledTextColor(Color.BLACK);
            cell.setBackground(Color.WHITE);
            cell.setOpaque(true);
            cell.setVisible(true);

            noteField = new JPanel(new FlowLayout());
            noteField.setOpaque(true);
            for (int i = 1; i <= KKSize; i++) {
                JLabel noteLabel = new JLabel(String.valueOf(i));
                noteLabel.setVisible(false);
                int fontSizeInfo = Math.max(5, 20 - boardSize);
                noteLabel.setFont(buttonFont.deriveFont(Font.PLAIN, fontSizeInfo));
                noteField.add(noteLabel);
                noteField.setBackground(Color.WHITE);
            }

            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    for (int i = 1; i <= KKSize; i++) {
                        JLabel noteLabel = (JLabel) noteField.getComponent(i - 1);
                        noteLabel.setFont(buttonFont.deriveFont(Font.PLAIN, (int) Math.min(12, 1.5*getHeight()/KKSize)));
                        infoLabel.setFont(buttonFont.deriveFont(Font.PLAIN, (int) Math.min(12, getHeight()/4)));
                    }
                }
            });

            JLayeredPane layeredPane = new JLayeredPane() {
                @Override
                public Dimension getPreferredSize() {
                    return CellPanel.this.getSize();
                }
            };

            infoLabel.setBackground(Color.WHITE);
            infoLabel.setVisible(true);
            infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoLabel.setVerticalAlignment(SwingConstants.TOP);
            infoLabel.setBounds(5, 5, 100, 20);
            infoLabel.setOpaque(false);
            layeredPane.add(noteField, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(cell, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(infoLabel, JLayeredPane.PALETTE_LAYER);
            add(layeredPane, BorderLayout.CENTER);
        }

        @Override
        public void doLayout() {
            super.doLayout();
            cell.setBounds(0, 0, getWidth(), getHeight());
            noteField.setBounds(getWidth() / 2, 0, getWidth()/2, getHeight());
        }

        public int getXCoord() {
            return xCoord;
        }

        public int getYCoord() {
            return yCoord;
        }

        public JTextField getCell() {
            return cell;
        }

        public JPanel getNoteField() {
            return noteField;
        }

        public void addInfoLabel(JLabel infoL) {
            infoLabel.setText(infoL.getText());
            infoLabel.setOpaque(false);
        }

        public void changeColor(Color color) {
            this.cell.setBackground(color);
            this.noteField.setBackground(color);
            this.infoLabel.setBackground(color);

        }


        @Override
        public Dimension getPreferredSize() {
            Container parent = getParent();
            if (parent != null) {
                int size = Math.min(parent.getWidth(), parent.getHeight());
                return new Dimension(size, size);
            } else {
                return super.getPreferredSize();
            }
        }

        public void updateNoteField(Vector<Integer> notes) {
            for (int i = 1; i <= KKSize; i++) {
                JLabel noteLabel = (JLabel) noteField.getComponent(i - 1);
                if (notes.get(i) == 1) {
                    noteLabel.setVisible(true);
                } else {
                    noteLabel.setVisible(false);
                }
            }
        }
    }

    private MouseAdapter createCellMouseListener(CellPanel cellPanel, JTextField cell) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cell.isEnabled()) {
                    if (selectedCell != null) {
                        int x = ((CellPanel) selectedCell.getParent().getParent()).getXCoord();
                        int y = ((CellPanel) selectedCell.getParent().getParent()).getYCoord();
                        selectedCell.setForeground(Color.BLACK);
                        paintRegion(x, y, Color.WHITE, Color.WHITE);

                    }

                    selectedCell = cellPanel.getCell();
                    selectedCellCoords = new int[]{cellPanel.getXCoord(), cellPanel.getYCoord()};
                    selectedCell.setForeground(Color.BLACK);
                    if (!noteMode) paintRegion(selectedCellCoords[0], selectedCellCoords[1], cellSelectedColor, regionSelectedColor);
                    else paintRegion(selectedCellCoords[0], selectedCellCoords[1], cellNotedColor, regionNotedColor);
                }
            }

        };
    }

    private void paintRegion(int x, int y, Color selectedColor, Color regionColor) {
        Vector<Vector<Integer>> regionBoard = ctrlP.getRegionBoard(idKenken);
        int regionId = regionBoard.get(x).get(y);
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (i == x && j == y) {
                    ((CellPanel) gameBoard[i][j]).changeColor(selectedColor);
                }
                else if (regionBoard.get(i).get(j) == regionId && gameBoard[i][j].getCell().isEnabled()) {
                    ((CellPanel) gameBoard[i][j]).changeColor(regionColor);
                }
            }
        }
    }


    private void navigateMainMenu() {

        ctrlP.setMainView();

    }

    private void gameWon(){


        String PATH = "./Data";
        ImageIcon original = new ImageIcon(PATH+"/Icons/victoryAnimation.gif");
        float scaleFactor = 0.5f;

        Duration duration = Duration.between(start, Instant.now()).plus(Duration.parse(startTimeF)).plus(elapsedTime);;
        String timeSpent = "Time spent: " + duration.toMinutes() + "m " + (duration.toSeconds() % 60) + "s";
        timer.stop();

        int gamePoints = ctrlP.getGamePoints();
        ImageIcon victoryAnimation = new ImageIcon(original.getImage().getScaledInstance((int) (original.getIconWidth()*scaleFactor), (int) (original.getIconHeight()*scaleFactor), Image.SCALE_DEFAULT));
        JOptionPane.showMessageDialog(mainPanel, "Victory! You have completed the puzzle.\n" + timeSpent + "\n" + "Total points obtained: " + gamePoints, "Game Won", JOptionPane.INFORMATION_MESSAGE, victoryAnimation);
        navigateMainMenu();

    }

    private void restartGame(Duration startTime) {

        timer.stop();
        InitTimer(Duration.ZERO);
        noteMode = false;
        selectedCell = null;
        selectedCellCoords = null;

        gamePanel.remove(boardPanel);

        createBoardPanel();

        gamePanel.add(boardPanel, BorderLayout.CENTER);

        timer.start();
    }

    private void createBoardPanel() {
        boardSize = board.get(0).get(0);
        regionNumber = board.get(0).get(1);
        selectedCell = null;

        boardPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension original = super.getPreferredSize();
                int length = Math.max(original.width, original.height);
                return new Dimension(length, length);
            }
        };

        boardPanel.setLayout(new GridLayout(boardSize, boardSize));

        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        boardPanel.setBorder(blackBorder);

        gameBoard = new CellPanel[boardSize][boardSize];
        for (int i = 0; i < regionNumber; i++) {
            for (int j = 3; j < board.get(i + 1).size(); j += 2) {
                JTextField cell = new JTextField();
                cell.setEditable(false);
                CellPanel cellPanel = new CellPanel(cell, board.get(i + 1).get(j) - 1, board.get(i + 1).get(j + 1) - 1);
                cell.addMouseListener(createCellMouseListener(cellPanel, cell));
                gameBoard[board.get(i + 1).get(j) - 1][board.get(i + 1).get(j + 1) - 1] = cellPanel;

            }
        }

        Vector<Vector<Integer>> regionBoard = ctrlP.getRegionBoard(idKenken);
        Vector<Vector<Integer>> regionInfo = ctrlP.getRegionInfo(idKenken);

        boolean[] regionVisited = new boolean[regionInfo.size()];
        for (int i = 0; i < regionVisited.length; i++) {
            regionVisited[i] = false;
        }
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                int regionId = regionBoard.get(i).get(j);
                boolean needsLabel = false;
                JLabel infoLabel = new JLabel();
                if (!regionVisited[regionId]) {
                    regionVisited[regionId] = true;
                    int op = regionInfo.get(regionId).get(0);
                    String opSymbol = ctrlP.getOpSymbols()[op];
                    int target = regionInfo.get(regionId).get(1);
                    infoLabel = new JLabel(String.valueOf(target) + opSymbol);
                    int size = (int) (500/(1.1*KKSize));
                    infoLabel.setFont(buttonFont.deriveFont(Font.PLAIN, size));
                    infoLabel.setBounds(0, 0, 0, 0);
                    infoLabel.setVisible(true);
                    needsLabel = true;
                }

                if (needsLabel) {
                    gameBoard[i][j].addInfoLabel(infoLabel);
                }

                int topBorder = (i == 0) ? 2 : (regionBoard.get(i-1).get(j) != regionId) ? 1 : 0;
                int leftBorder = (j == 0) ? 2 : (regionBoard.get(i).get(j-1) != regionId) ? 1 : 0;
                int bottomBorder = (i == gameBoard.length - 1) ? 2 : (regionBoard.get(i+1).get(j) != regionId) ? 1 : 0;
                int rightBorder = (j == gameBoard.length - 1) ? 2 : (regionBoard.get(i).get(j+1) != regionId) ? 1 : 0;

                Border thinBorder = BorderFactory.createMatteBorder(1-topBorder, 1-leftBorder, 1-bottomBorder, 1-rightBorder, Color.LIGHT_GRAY);

                Border thickBorder = BorderFactory.createMatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, Color.BLACK);

                Border compoundBorder = BorderFactory.createCompoundBorder(thickBorder, thinBorder);

                gameBoard[i][j].setBorder(compoundBorder);

            }
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardPanel.add(gameBoard[i][j]);
            }
        }
    }
}