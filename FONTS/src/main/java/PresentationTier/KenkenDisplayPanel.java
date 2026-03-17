package PresentationTier;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class KenkenDisplayPanel extends JPanel {
    private CtrlPresentation ctrlPresentation;
    private Vector<Vector<Integer>> regionBoard;
    private Vector<Vector<Integer>> regionInfo;
    private int kenkenSize;
    private int cellSize;
    private JPanel[][] board;
    private JLabel[][] info;

    private Font buttonFont;
    private Font textFont;

    public KenkenDisplayPanel(CtrlPresentation cp, int idKenken) {
        ctrlPresentation = cp;
        regionBoard = ctrlPresentation.getRegionBoard(idKenken);
        regionInfo = ctrlPresentation.getRegionInfo(idKenken);
        kenkenSize = ctrlPresentation.getKenkenSize(idKenken);
        cellSize = (int) Math.floor((double) 500 /kenkenSize);
        initializeFonts();
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(kenkenSize*cellSize, kenkenSize*cellSize));
        board = new JPanel[kenkenSize][kenkenSize];
        info = new JLabel[kenkenSize][kenkenSize];
        setBoard();
        repaint();
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

    private void setBoard() {
        GridBagConstraints gbc = new GridBagConstraints();
        boolean[] regionVisited = new boolean[regionInfo.size()];
        for (int i = 0; i < regionVisited.length; i++) {
            regionVisited[i] = false;
        }
        for (int i = 0; i < kenkenSize; i++) {
            for (int j = 0; j < kenkenSize; j++) {
                board[i][j] = new JPanel();
                gbc.gridx = j;
                gbc.gridy = i;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                add(board[i][j], gbc);
                board[i][j].setOpaque(true);
                board[i][j].setVisible(true);

                int regionId = regionBoard.get(i).get(j);
                if (!regionVisited[regionId]) {
                    regionVisited[regionId] = true;
                    int op = regionInfo.get(regionId).get(0);
                    String opSymbol = ctrlPresentation.getOpSymbols()[op];
                    int target = regionInfo.get(regionId).get(1);
                    info[i][j] = new JLabel(String.valueOf(target)+opSymbol);
                    info[i][j].setPreferredSize(new Dimension(cellSize, cellSize));
                    info[i][j].setFont(buttonFont.deriveFont(Font.PLAIN, (float) cellSize /5));

                    board[i][j].setLayout(null);

                    info[i][j].setBounds(cellSize/5, cellSize/50, cellSize, cellSize/2);
                    board[i][j].add(info[i][j]);
                }
                else info[i][j] = new JLabel("");
            }
        }
    }

    public void updateCellSize(int width, int height) {
        cellSize = Math.min((width-100)/ kenkenSize, (height-100) / kenkenSize);
        setPreferredSize(new Dimension(kenkenSize*cellSize, kenkenSize*cellSize));
        for (int i = 0; i < kenkenSize; i++) {
            for (int j = 0; j < kenkenSize; j++) {
                Dimension cellDimension = new Dimension(cellSize, cellSize);
                info[i][j].setPreferredSize(cellDimension);
                info[i][j].setFont(buttonFont.deriveFont(Font.PLAIN, (float) cellSize /5));
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        for (int i = 0; i < kenkenSize; i++) {
            for (int j = 0; j < kenkenSize; j++) {
                int regionId = regionBoard.get(i).get(j);
                int topBorder = (i == 0) ? 2 : (regionBoard.get(i-1).get(j) != regionId) ? 1 : 0;
                int leftBorder = (j == 0) ? 2 : (regionBoard.get(i).get(j-1) != regionId) ? 1 : 0;
                int bottomBorder = (i == board.length - 1) ? 2 : (regionBoard.get(i+1).get(j) != regionId) ? 1 : 0;
                int rightBorder = (j == board.length - 1) ? 2 : (regionBoard.get(i).get(j+1) != regionId) ? 1 : 0;

                Border thinBorder = BorderFactory.createMatteBorder(1-topBorder, 1-leftBorder, 1-bottomBorder, 1-rightBorder, Color.LIGHT_GRAY);

                Border thickBorder = BorderFactory.createMatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, Color.BLACK);

                Border compoundBorder = BorderFactory.createCompoundBorder(thickBorder, thinBorder);

                board[i][j].setBorder(compoundBorder);
            }
        }
    }
}
