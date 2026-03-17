package PresentationTier;

import LogicTier.KenkenAlreadyInPersistence;

import javax.swing.*;
import java.awt.event.KeyEvent;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class GeneratorView extends JFrame {

    private CtrlPresentation ctrlPresentation;
    private JPanel generatorPanel;
    private JPanel gridPanel;
    private JPanel regionPanel;
    private GridButton[][] grid;
    private int kenkenSize;
    private JButton addRegionButton;
    private JButton cancelRegionButton;
    private JButton returnButton;
    private ActionListener selectCell;
    private ActionListener addRegion;
    private ActionListener cancelRegion;
    private ActionListener returnToMain;
    private int numCells;
    private Vector<String> kenken;
    private int counter;
    private Color regionColor;
    private int numRegions;
    private int regionSize;
    private int regionTarget;
    private int opId;
    private String currentRegion;

    private Font buttonFont;
    private Font textFont;

    static class GridButton extends JButton {
        private int i;
        private int j;
        private boolean selected;

        public GridButton(int i, int j) {
            super();
            this.i = i;
            this.j = j;
            this.selected = false;
        }

        public int get_i() {
            return i;
        }

        public int get_j() {
            return j;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean b) {
            selected = b;
        }
    }

    public GeneratorView(CtrlPresentation cp, int size) {
        ctrlPresentation = cp;
        kenkenSize = size;
        numCells = size*size;
        counter = 0;
        numRegions = 0;
        regionColor = Color.WHITE;
        kenken = new Vector<String>();
        regionSize = -1;
        regionTarget = -1;
        opId = -1;
        currentRegion = null;
        setMinimumSize(new Dimension(500, 500));
        setSize(800, 600);
        setTitle("Generate Personalized Kenken");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createListeners();
        initializeFonts();
        setGeneratorLayout();
        setContentPane(generatorPanel);
    }

    private void initializeFonts() {

        String path = "./Data";
        Icon customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
        try {
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File(path+"/Lettering/timeBurner.ttf"));
            buttonFont = buttonFont.deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(buttonFont);
        } catch (IOException | FontFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, customIcon);
        }
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File(path+"/Lettering/cartesian.otf"));
            textFont = textFont.deriveFont(Font.PLAIN, 15);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(textFont);
            UIManager.put("OptionPane.messageFont", new FontUIResource(textFont));
            UIManager.put("OptionPane.buttonFont", new FontUIResource(buttonFont));
        } catch (IOException | FontFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, customIcon);
        }
    }

    private void setGeneratorLayout() {
        generatorPanel = new JPanel(new BorderLayout());

        gridPanel = new JPanel(new GridLayout(kenkenSize, kenkenSize));
        gridPanel.setSize(new Dimension(kenkenSize*50, kenkenSize*50));

        grid = new GridButton[kenkenSize][kenkenSize];

        for (int ii = 0; ii < kenkenSize; ii++) {
            for (int jj = 0; jj < kenkenSize; jj++) {
                grid[ii][jj] = new GridButton(ii, jj);
                grid[ii][jj].setEnabled(false);
                grid[ii][jj].addActionListener(selectCell);
                grid[ii][jj].setBackground(Color.WHITE);
                grid[ii][jj].setOpaque(true);
                gridPanel.add(grid[ii][jj]);
            }
        }

        generatorPanel.add(gridPanel, BorderLayout.CENTER);

        addRegionButton = new JButton("Add Region");
        addRegionButton.setFont(buttonFont);
        addRegionButton.addActionListener(addRegion);
        cancelRegionButton = new JButton("Cancel");
        cancelRegionButton.setFont(buttonFont);
        cancelRegionButton.setEnabled(false);
        cancelRegionButton.addActionListener(cancelRegion);

        regionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        regionPanel.add(new JLabel(" "), gbc);
        gbc.gridy++;
        regionPanel.add(addRegionButton, gbc);
        gbc.gridy++;
        regionPanel.add(new JLabel(" "), gbc);
        gbc.gridy++;
        regionPanel.add(cancelRegionButton, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JPanel returnPanel = new JPanel(new BorderLayout());
        returnButton = new JButton("Return");
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(returnToMain);

        returnButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "clickReturnButton");
        returnButton.getActionMap().put("clickReturnButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnButton.doClick();
            }
        });


        returnPanel.add(returnButton, BorderLayout.SOUTH);
        regionPanel.add(returnPanel, gbc);

        generatorPanel.add(regionPanel, BorderLayout.EAST);

        createListeners();
    }

    private void createListeners() {
        returnToMain = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Are you sure you want to return to the Main Menu? Any progress won't be saved.";
                String path = "./Data";
                Icon customIcon = new ImageIcon(path + "/Icons/mainMenuIcon.png");
                int result = JOptionPane.showConfirmDialog(generatorPanel, message, "Return to Main Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, customIcon);
                if (result == JOptionPane.OK_OPTION) {
                    navigateMainMenu();
                }
            }
        };

        addRegion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();

                String[] operations = ctrlPresentation.getOpNames();
                int[] opIds = ctrlPresentation.getOpIds();

                JTextField newRegionSize = new JTextField(3);
                JLabel regionSizeLabel = new JLabel("Region Size:");
                regionSizeLabel.setFont(textFont);
                panel.add(regionSizeLabel);
                panel.add(newRegionSize);
                JTextField newRegionTarget = new JTextField(3);
                JLabel regionTargetLabel = new JLabel("Region Target:");
                regionTargetLabel.setFont(textFont);
                panel.add(regionTargetLabel);
                panel.add(newRegionTarget);
                JLabel regionOpLabel = new JLabel("Region Operation");
                regionOpLabel.setFont(textFont);
                panel.add(regionOpLabel);
                JComboBox<String> comboBox = new JComboBox<>(operations);
                panel.add(comboBox);

                String path = "./Data";
                Icon customIcon = new ImageIcon(path + "/Icons/operationIcon.png");
                int result = JOptionPane.showConfirmDialog(generatorPanel, panel, "Size, target and operation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, customIcon);

                if (result == JOptionPane.OK_OPTION) {
                    String stringSize = newRegionSize.getText();
                    String stringTarget = newRegionTarget.getText();
                    String stringSelectedOperation = (String) comboBox.getSelectedItem();

                    if (stringSize != null && !stringSize.isEmpty()) {
                        regionSize = Integer.parseInt(stringSize);
                        if (regionSize <= 0) {
                            customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                            JOptionPane.showMessageDialog(generatorPanel, "The number of cells must be a positive integer.", "Wrong input", JOptionPane.ERROR_MESSAGE, customIcon);
                        } else if (regionSize > numCells) {
                            customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                            JOptionPane.showMessageDialog(generatorPanel, "The number of cells in the region must be smaller than or equal to " + numCells + ".", "Wrong input", JOptionPane.ERROR_MESSAGE, customIcon);
                        }
                        else {

                            if (stringTarget != null && !stringTarget.isEmpty()) {
                                regionTarget = Integer.parseInt(stringTarget);
                                if (regionTarget <= 0) {
                                    customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                                    JOptionPane.showMessageDialog(generatorPanel, "The target number must be a positive integer.", "Wrong input", JOptionPane.ERROR_MESSAGE, customIcon);
                                }
                                else {

                                    opId = -1;
                                    for (int k = 0; k < operations.length; k++) {
                                        if (operations[k].equals(stringSelectedOperation)) opId = opIds[k];
                                    }

                                    if (opId < 0) {
                                        customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                                        JOptionPane.showMessageDialog(generatorPanel, "Invalid operation.", "Wrong input", JOptionPane.ERROR_MESSAGE, customIcon);
                                    }
                                    else {
                                        addRegionButton.setEnabled(false);
                                        cancelRegionButton.setEnabled(true);
                                        addRegion();
                                    }

                                }
                            }
                            else {
                                customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                                JOptionPane.showMessageDialog(generatorPanel, "Invalid target.", "Wrong input", JOptionPane.ERROR_MESSAGE, customIcon);
                            }
                        }
                    }
                    else {
                        customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                        JOptionPane.showMessageDialog(generatorPanel, "Invalid size.", "Wrong input", JOptionPane.ERROR_MESSAGE, customIcon);
                    }
                }
            }
        };

        cancelRegion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRegion != null) {
                    String[] parts = currentRegion.split(" ");
                    if ((parts.length - 3) % 2 == 0) {
                        for (int i = 3; i < parts.length; i += 2) {
                            int x = Integer.parseInt(parts[i]) - 1;
                            int y = Integer.parseInt(parts[i + 1]) - 1;
                            grid[x][y].setBackground(Color.WHITE);
                            grid[x][y].setEnabled(true);
                            grid[x][y].setSelected(false);
                        }
                        deactivateAllButtons();
                        addRegionButton.setEnabled(true);
                        currentRegion = null;
                        counter = 0;
                        regionSize = -1;
                        regionTarget = -1;
                        opId = -1;
                        cancelRegionButton.setEnabled(false);
                    } else {
                        String path = "./Data";
                        Icon customIcon = new ImageIcon(path + "/Icons/wrongIcon.png");
                        JOptionPane.showMessageDialog(generatorPanel, "Region cannot be removed", "", JOptionPane.ERROR_MESSAGE, customIcon);
                    }
                }
            }
        };

        selectCell = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridButton clickedButton = (GridButton) e.getSource();
                clickedButton.setSelected(!clickedButton.isSelected());

                String path = "./Data";
                Icon customIcon;

                if (clickedButton.isSelected()) {
                    if (counter < regionSize) {
                        counter++;
                        clickedButton.setBackground(regionColor);
                        clickedButton.setOpaque(true);
                        currentRegion += String.valueOf(clickedButton.get_i() + 1) + " " + String.valueOf(clickedButton.get_j() + 1) + " ";
                    }
                    else {
                        clickedButton.setSelected(false);
                        customIcon = new ImageIcon(path + "/Icons/warningIcon.png");
                        JOptionPane.showMessageDialog(gridPanel, "The region is at maximum capacity. Remove a cell before continuing.", "Full region.", JOptionPane.INFORMATION_MESSAGE, customIcon);
                    }
                } else {
                    counter--;
                    clickedButton.setBackground(Color.WHITE);
                    clickedButton.setOpaque(true);
                    int ii = clickedButton.get_i();
                    int jj = clickedButton.get_j();
                    boolean found = false;
                    String targetIi = String.valueOf(ii+1);
                    String targetJj = String.valueOf(jj+1);

                    for (int i = currentRegion.length() - 1; i >= 3 && !found; i -= 4) {
                        if (Character.isDigit(currentRegion.charAt(i-1)) && Character.isDigit(currentRegion.charAt(i-3))) {
                            String jjString = currentRegion.substring(i-1, i);
                            String iiString = currentRegion.substring(i-3, i-2);
                            if (jjString.equals(targetJj) && iiString.equals(targetIi)) {
                                currentRegion = currentRegion.substring(0, i-3) + currentRegion.substring(i+1);
                                found = true;
                            }
                        }
                    }
                }

                boolean regionCompleted = false;
                if (counter >= regionSize) {
                    String message = "Are you satisfied with your region?";
                    String[] options = {"Confirm", "Keep editing"};
                    customIcon = new ImageIcon(path + "/Icons/editIcon.png");
                    int choice = JOptionPane.showOptionDialog(gridPanel, message, "Confirm Region", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, customIcon, options, options[0]);
                    if (choice == 0) regionCompleted = true;
                }

                if (regionCompleted) {
                    deactivateAllButtons();
                    addRegionButton.setEnabled(true);
                    cancelRegionButton.setEnabled(false);
                    String[] ops = ctrlPresentation.getOpSymbols();
                    clickedButton.setText(String.valueOf(regionTarget)+ops[opId]);
                    clickedButton.setFont(buttonFont.deriveFont(Font.PLAIN, Math.min(20, getHeight()/(4*kenkenSize))));
                    ++numRegions;
                    numCells -= regionSize;
                    currentRegion = currentRegion.substring(0,currentRegion.length()-1);
                    kenken.add(currentRegion);
                    currentRegion = null;
                    counter = 0;
                    regionSize = -1;
                    regionTarget = -1;
                    opId = -1;
                }

                if (numCells == 0) {
                    currentRegion = String.valueOf(kenkenSize)+" "+String.valueOf(numRegions);
                    kenken.add(0,currentRegion);
                    addRegionButton.setEnabled(false);

                    JDialog dialog = new JDialog(GeneratorView.this, "Generating Kenken...", true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setSize(200, 100);
                    dialog.setLocationRelativeTo(GeneratorView.this);
                    ImageIcon loadingIcon = new ImageIcon(path+"/Icons/squareWaiting.gif");
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

                    new Thread(() -> {
                        int kenkenId = -1;
                        Icon customIconAux;
                        boolean exists = false;
                        try {
                            kenkenId = ctrlPresentation.generatePersonalizedKenken(kenken);
                        }
                        catch (KenkenAlreadyInPersistence er) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.dispose();
                            exists = true;
                            customIconAux = new ImageIcon(path + "/Icons/duplicateIcon.png");
                            JOptionPane.showMessageDialog(gridPanel, "Kenken already exists.", "", JOptionPane.INFORMATION_MESSAGE, customIconAux);
                        }
                        if (kenkenId < 0 && !exists) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            dialog.dispose();
                            customIconAux = new ImageIcon(path + "/Icons/invalidKenkenIcon.png");
                            JOptionPane.showMessageDialog(gridPanel, "The Kenken you generated is not valid.", "", JOptionPane.INFORMATION_MESSAGE, customIconAux);
                            navigateMainMenu();
                        }
                        else {
                            dialog.dispose();
                            JPanel containerPanel = new JPanel(new BorderLayout());
                            JPanel displayPanel = new JPanel();
                            KenkenDisplayPanel kenkenPanel = new KenkenDisplayPanel(ctrlPresentation, kenkenId);
                            displayPanel.addComponentListener(new ComponentAdapter() {
                                @Override
                                public void componentResized(ComponentEvent e) {
                                    kenkenPanel.updateCellSize(getWidth(), getHeight());
                                }
                            });
                            displayPanel.add(kenkenPanel);
                            JLabel kenkenIdLabel = new JLabel("This is the id of the new Kenken: "+String.valueOf(kenkenId+1));
                            displayPanel.add(kenkenIdLabel);
                            containerPanel.add(displayPanel, BorderLayout.CENTER);
                            JPanel buttonsPanel = new JPanel();
                            containerPanel.add(buttonsPanel, BorderLayout.SOUTH);
                            JButton returnButton = new JButton("Return to Main Menu");
                            returnButton.setFont(buttonFont.deriveFont(Font.PLAIN, 12));
                            returnButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    navigateMainMenu();
                                }
                            });
                            buttonsPanel.add(returnButton);
                            JButton playKenkenButton = new JButton("Play Kenken");
                            playKenkenButton.setFont(buttonFont.deriveFont(Font.PLAIN, 12));
                            int finalKenkenId = kenkenId;
                            playKenkenButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    boolean available = ctrlPresentation.newGame(finalKenkenId);
                                    Vector<Vector<Integer>> board = ctrlPresentation.getBoard(finalKenkenId);
                                    GeneratorView.this.setVisible(false);
                                    if (available) ctrlPresentation.startGameView(board, finalKenkenId);
                                }
                            });
                            buttonsPanel.add(playKenkenButton);
                            setContentPane(containerPanel);
                            setPreferredSize(new Dimension(800, 600));
                            pack();
                            revalidate();
                            repaint();
                            setVisible(true);
                        }
                    }).start();

                    dialog.setVisible(true);
                }
            }
        };
    }

    private static Color generateRandomColor() {
        Random random = new Random();
        int base = 127;
        int range = 128;

        int red = base + random.nextInt(range);
        int green = base + random.nextInt(range);
        int blue = base + random.nextInt(range);

        return new Color(red, green, blue);
    }

    private void activateUnselectedButtons() {
        for (int i = 0; i < kenkenSize; i++) {
            for (int j = 0; j < kenkenSize; j++) {
                if (!grid[i][j].selected) {
                    grid[i][j].setEnabled(true);
                }
            }
        }
    }

    private void deactivateAllButtons() {
        for (int i = 0; i < kenkenSize; i++) {
            for (int j = 0; j < kenkenSize; j++) {
                grid[i][j].setEnabled(false);
                grid[i][j].requestFocus();
            }
        }
    }

    private void addRegion() {
        currentRegion = String.valueOf(opId)+" "+String.valueOf(regionTarget)+" "+String.valueOf(regionSize)+" ";
        activateUnselectedButtons();
        regionColor = generateRandomColor();
    }

    private void navigateMainMenu() {
        GeneratorView.this.setVisible(false);
        ctrlPresentation.navigateToMainMenuFromGenerator();
    }
}
