package LogicTier.Game;
import java.util.*;

import LogicTier.IllegalCellSelection;
import LogicTier.IllegalGameAction;
import LogicTier.IllegalMatrixPosition;
import LogicTier.Coordinates.Coordinates;
import LogicTier.Kenken.Kenken;
import LogicTier.User.User;

import java.time.LocalTime;
import java.time.Duration;
import static java.lang.Math.max;

public class Game {

    private Kenken template;
    private User player;

    public class Cell {
        public int selection;
        public boolean[] notes;

        public Cell(int n) {
            selection = 0;
            notes = new boolean[n];
        }

        public void setCellSelection(int selection) {
            this.selection = selection;
        }

        public void setCellNotes(boolean[] notes) {
            this.notes = notes;
        }
    }

    public Cell[][] board;
    private int lives;
    private int hints;
    private Set<Coordinates> freePositions;

    private LocalTime startCounter;
    private LocalTime stopCounter;
    private Duration timeElapsed;
    private boolean onGoing;
    private int score;

    private static final int MAX_LIVES = 3;
    private static final int MAX_HINTS = 10;


    public Game(Kenken kenkenTemplate, User playerUsr) {
        this.template = kenkenTemplate;
        this.player = playerUsr;
        setBoard();
        this.lives = MAX_LIVES;
        this.hints = 0;
        setFreePositions();
        startCounter = LocalTime.now();
        timeElapsed = Duration.ZERO;
        onGoing = true;
        score = 0;
    }

    public Game(Kenken kenkenTemplate, User playerUsr, Vector<Vector<Vector<Integer>>> auxBoard, int auxLives, int auxHints,
            Vector<Integer> auxFreePos, String time) {
        template = kenkenTemplate;
        player = playerUsr;
        board = getBoardFromCtrl(auxBoard);
        lives = auxLives;
        hints = auxHints;
        freePositions = getFreePositionsFromCtrl(auxFreePos);
        timeElapsed = getDurationFromCtrl(time);
        startCounter = LocalTime.now();
        onGoing = true;
        score = 0;
    }

    public int getGameId() {
        return template.getIdKenken();
    }
    
    public User getPlayer() {
        return player;
    }

    public int[][] getSolution() {
        return template.getSolution();
    }

    public int getSize() {
        return template.getBoardSize();
    }

    public Cell[][] getBoard() {
        return board;
    }

    public int getLives() {
        return lives;
    }

    public int getHints() {
        return hints;
    }

    public Duration getDuration() {
        updateTimeElapsed();
        return timeElapsed;
    }

    public boolean getStatus() {
        return onGoing;
    }

    public int getScore() {
        return score;
    }

    public int getFreePositionsSize() {
        return freePositions.size();
    }

    private Vector<Integer> getCellAsVector(Cell c) {
        Vector<Integer> aux = new Vector<Integer>();
        aux.add(c.selection);
        for (int i = 0; i < c.notes.length; ++i) {
            if (c.notes[i]) aux.add(1);
            else aux.add(0);
        }
        return aux;
    }
    
    private Cell setVectorToCell(Vector<Integer> v) {
        int n = (v.size() - 1);
        Cell c = new Cell(n);
        c.setCellSelection(v.get(0));
        boolean[] aux = new boolean[n];
        for (int i = 1; i <= n; ++i) {
            if (v.get(i) == 0) aux[i-1] = false;
            else aux[i-1] = true;
        }
        c.setCellNotes(aux);
        return c;
    }

    public Vector<Vector<Vector<Integer>>> getBoardForCtrl() {
        Vector<Vector<Vector<Integer>>> aux = new Vector<>();
        for (int i = 0; i < template.getBoardSize(); ++i) {
            Vector<Vector<Integer>> aux2 = new Vector<>();
            for (int j = 0; j < template.getBoardSize(); ++j) {
                Vector<Integer> aux3 = getCellAsVector(board[i][j]);
                aux2.add(aux3);
            }
            aux.add(aux2);
        }
        return aux;
    }

    public Vector<Integer> getCoordinateAsVector(int x, int y){
        return getCellAsVector(board[x][y]);
    }

    public Cell[][] getBoardFromCtrl(Vector<Vector<Vector<Integer>>> b) {
        int n = b.size();
        Cell[][] board_aux = new Cell[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                board_aux[i][j] = setVectorToCell(b.get(i).get(j));
            }
        }
        return board_aux;
    }

    public Vector<Integer> getFreePositionsForCtrl() {
        Vector<Integer> setFreePos = new Vector<Integer>();
        Iterator<Coordinates> it = freePositions.iterator();
        while (it.hasNext()) {
            Coordinates c = it.next();
            setFreePos.add(c.getX());
            setFreePos.add(c.getY());
        }
        return setFreePos;
    }

    public Set<Coordinates> getFreePositionsFromCtrl(Vector<Integer> setFreePos) {
        Set<Coordinates> aux_freePos = new HashSet<Coordinates>();
        int x, y;
        for (int i = 0; i < setFreePos.size(); i+=2) {
            x = setFreePos.get(i);
            y = setFreePos.get(i+1);
            aux_freePos.add(new Coordinates(x, y));
        }
        return aux_freePos;
    }

    public String getDurationForCtrl() {
        updateTimeElapsed();
        return timeElapsed.toString();
    }

    public Duration getDurationFromCtrl(String time) {
        return Duration.parse(time);
    }

    private void setBoard() {
        int n = template.getBoardSize();
        board = new Cell[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                board[i][j] = new Cell(n);
            }
        }
    }

    private void setFreePositions() {
        freePositions = new HashSet<Coordinates>();
        int n = template.getBoardSize();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                freePositions.add(new Coordinates(i,j));
            }
        }
    }

    private void removeFreePosition(int i, int j) {
        Iterator<Coordinates> it = freePositions.iterator();
        while (it.hasNext()) {
            Coordinates c = it.next();
            if (c.getX() == i && c.getY() == j) {
                it.remove();
                break;
            }
        }
    }

    public boolean boardIsComplete() {
        if (freePositions.size() == 0) return true;
        return false;
    }

    public boolean gameIsOver() {
        boolean over = ((lives == 0) || boardIsComplete());
        return over;
    }

    private static int generateRandomNumber(int n) {
        Random rand = new Random();
        return rand.nextInt(n);
    }

    private Coordinates getRandomFreePosition() {
        int m = freePositions.size();
        int k = generateRandomNumber(m);
        int r = 0;
        for (Coordinates pos : freePositions) {
            if (r == k) return pos;
            ++r;
        }
        return null;
    }

    public int pointsGame() {
        long hours = timeElapsed.toHours();
        long minutes = timeElapsed.toMinutes() % 60;
        int points = 100;
        points -= 40*(3 - lives);
        points -= 10*hints;
        if (minutes > 3 || hours > 0) points -= (points/25);
        if (minutes > 6 || hours > 0) points -= (points/20);
        if (minutes > 12 || hours > 0) points -= (points/15);
        if (minutes > 24 || hours > 0) points -= (points/10);
        if (hours > 0) points -= (points/5);
        points = max(0, points);
        return points;
    }

    private void updateTimeElapsed() {
        if (onGoing) stopCounter = LocalTime.now();
        Duration difference = Duration.between(startCounter, stopCounter);
        if(difference.isNegative()){
            difference = Duration.ZERO;
        }
        timeElapsed = timeElapsed.plus(difference);
        startCounter = LocalTime.now();
    }

    private void endGame(boolean victory) {
        updateTimeElapsed();
        score = pointsGame();
        boolean errors = (lives < MAX_LIVES);
        boolean hinted = (hints > 0);
        player.updateStatistics(score, timeElapsed, victory, errors, hinted);
        onGoing = false;
    }

    public boolean setCell(int i, int j, int k) {
        int n = template.getBoardSize();
        if (i < 0 || i >= n || j < 0 || j >= n) throw new IllegalMatrixPosition("Illegal board position: can't be accessed");
        if (k <= 0 || k > n) throw new IllegalCellSelection("Illegal cell selection: must be between 1 and board size.");
        if (!onGoing || gameIsOver()) throw new IllegalGameAction("Game is not ongoing or over.");
        
        if (board[i][j].selection != 0) {
            if (board[i][j].selection == template.getSolution()[i][j]) freePositions.add(new Coordinates(i,j));
        }
        board[i][j].selection = k;
        for (int t = 0; t < template.getBoardSize(); ++t) board[i][j].notes[t] = false;
        if (k != template.getSolution()[i][j]) {
            if (--lives == 0) endGame(false);
            return false;
        }
        else {
            removeFreePosition(i, j);
            if (boardIsComplete()) {
                endGame(true);
            }
            return true;
        }
    }

    public void unsetCell(int i, int j) {
        int n = template.getBoardSize();
        if (i < 0 || i >= n || j < 0 || j >= n) throw new IllegalMatrixPosition("Illegal board position: can't be accessed");
        if (!onGoing || gameIsOver()) throw new IllegalGameAction("Game is not ongoing or over.");
        board[i][j].selection = 0;
        for (int k = 0; k < template.getBoardSize(); ++k) board[i][j].notes[k] = false;
        freePositions.add(new Coordinates(i,j));
    }

    public void setNote(int i, int j, int x) {
        int n = template.getBoardSize();
        if (i < 0 || i >= n || j < 0 || j >= n) throw new IllegalMatrixPosition("Illegal board position: can't be accessed");
        if (x <= 0 || x > n) throw new IllegalCellSelection("Illegal cell note: must be between 1 and board size.");
        if (!onGoing || gameIsOver()) throw new IllegalGameAction("Game is not ongoing or over.");
        board[i][j].notes[x-1] = !board[i][j].notes[x-1];
        if (board[i][j].selection > 0) {
            if (board[i][j].selection == template.getSolution()[i][j]) {
                freePositions.add(new Coordinates(i,j));
            }
            board[i][j].selection = 0;
        }
    }

    public Coordinates askHint() {
        if (!onGoing || gameIsOver()) throw new IllegalGameAction("Game is not ongoing or over.");
        if (hints < MAX_HINTS) {
            Coordinates randomPos = getRandomFreePosition();
            int i = randomPos.getX();
            int j = randomPos.getY();
            removeFreePosition(i,j);
            board[i][j].selection = template.getSolution()[i][j];
            for (int t = 0; t < template.getBoardSize(); ++t) board[i][j].notes[t] = false;
            ++hints;
            if (boardIsComplete()) endGame(true);
            return randomPos;
        }
        else return null;
    }

    public void pauseGame() {
        if (!onGoing || gameIsOver()) throw new IllegalGameAction("Game is not ongoing or over.");
        stopCounter = LocalTime.now();
        updateTimeElapsed();
        onGoing = false;
    }

    public void resumeGame() {
        startCounter = LocalTime.now();
        onGoing = true;
    }

    public void restartGame() {
        timeElapsed = Duration.ZERO;
        lives = MAX_LIVES;
        hints = 0;
        setBoard();
        setFreePositions();
        startCounter = LocalTime.now();
        onGoing = true;
    }

}
