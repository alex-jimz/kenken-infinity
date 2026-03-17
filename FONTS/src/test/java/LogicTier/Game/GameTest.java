package LogicTier.Game;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import LogicTier.Coordinates.Coordinates;
import LogicTier.Kenken.Kenken;
import LogicTier.Region.Region;
import LogicTier.User.User;
import LogicTier.TestRunner.TestRunner;
import LogicTier.*;
import java.time.Duration;
import java.time.LocalTime;

public class GameTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(GameTest.class);
    }

    //Test creators
    @Test
    public void testGameCreator() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Paco", "SuperSecurePassword");
        Duration e, d;
        LocalTime start = LocalTime.now();
        Game g = new Game(k, u);
        assertEquals("Paco", g.getPlayer().getUsername());
        assertEquals(3, g.getLives());
        LocalTime stop = LocalTime.now();
        e = g.getDuration();
        d = Duration.between(stop, start);
        assertEquals(d.toMinutes(), e.toMinutes());
    }

    @Test 
    public void testGameCreatorFromMemory() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Bb8", "NotSoSecurePassword");
        Vector<Vector<Vector<Integer>>> aux = new Vector<>();
        Vector<Vector<Integer>> aux1 = new Vector<>();
        Vector<Integer> aux2 = new Vector<Integer>();
        Integer[] elements00 = {1, 0, 0, 0};
        aux2.addAll(Arrays.asList(elements00));
        aux2.add(1);
        aux2.add(0);
        aux2.add(0);
        aux2.add(0);
        aux1.add(aux2); // [0][0]
        aux2 = new Vector<Integer>();
        Integer[] elements01 = {0, 0, 1, 1};
        aux2.addAll(Arrays.asList(elements01));
        aux1.add(aux2); //[0][1]
        aux2 = new Vector<Integer>();
        Integer[] elements02 = {0, 1, 0, 1};
        aux2.addAll(Arrays.asList(elements02));
        aux1.add(aux2); //[0][2]
        aux.add(aux1);
        aux1 = new Vector<>();
        aux2 = new Vector<Integer>();
        Integer[] elements10 = {0, 0, 1, 0};
        aux2.addAll(Arrays.asList(elements10));
        aux1.add(aux2); //[1][0]
        aux2 = new Vector<Integer>();
        Integer[] elements11 = {1, 0, 0, 0};    //1 error almenos
        aux2.addAll(Arrays.asList(elements11));
        aux1.add(aux2); //[1][1]
        aux2 = new Vector<Integer>();
        Integer[] elements12 = {3, 0, 0, 0};    //asumamos que hinted
        aux2.addAll(Arrays.asList(elements12));
        aux1.add(aux2); //[1][2]
        aux.add(aux1);
        aux1 = new Vector<>();
        aux2 = new Vector<Integer>();
        Integer[] elements20 = {0, 0, 0, 0};    //Aqui no se habia puesto nada
        aux2.addAll(Arrays.asList(elements20));
        aux1.add(aux2); //[2][0]
        aux2 = new Vector<Integer>();
        Integer[] elements21 = {0, 0, 0, 0};    //Aqui no se habia puesto nada
        aux2.addAll(Arrays.asList(elements21));
        aux1.add(aux2); //[2][1]
        aux2 = new Vector<Integer>();
        Integer[] elements22 = {0, 0, 0, 0};    //Aqui no se habia puesto nada
        aux2.addAll(Arrays.asList(elements22));
        aux1.add(aux2); //[2][0]
        aux.add(aux1);
        int remainingLives = 2;
        int hintsSoFar = 1;
        Vector<Integer> stillFreePos = new Vector<Integer>();
        Integer[] pos = {0, 1, 0, 2, 1, 0, 2, 0, 2, 1, 2, 2};
        stillFreePos.addAll(Arrays.asList(pos));
        String time = "PT18M36S";
        Game g = new Game(k, u, aux, remainingLives, hintsSoFar, stillFreePos, time);
        assertEquals(6, g.getFreePositionsSize());
        assertEquals(2, g.getLives());
        assertEquals(1, g.getHints());
        assertEquals(true, g.getDuration().compareTo(Duration.parse(time)) >= 0);
        assertEquals(3, g.getBoard()[1][2].selection);
        boolean[] n = {false, false, false};
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[1][2].notes[t]);
        }
        n[0] = n[2] = true;
        assertEquals(0, g.getBoard()[0][2].selection);
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][2].notes[t]);
        }
        assertEquals(0, g.getScore());
        assertEquals(true, g.getStatus());
    }

    @Test
    public void testGameForCtrl() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Bb8", "NotSoSecurePassword");
        Game g = new Game(k, u);
        g.setNote(0, 0, 1);
        g.setNote(0, 0, 2);
        g.setCell(2, 2, 2);
        g.setCell(0,2,2);   //error
        Vector<Vector<Vector<Integer>>> boardCtrl = g.getBoardForCtrl();
        Vector<Integer> freePosCtrl = g.getFreePositionsForCtrl();
        int numFree = freePosCtrl.size();
        assertEquals(16, numFree);
        int sel22 = boardCtrl.get(2).get(2).get(0);
        assertEquals(2, sel22);
        int note22_0 = boardCtrl.get(2).get(2).get(1);
        assertEquals(0, note22_0);
        int note22_1 = boardCtrl.get(2).get(2).get(2);
        assertEquals(0, note22_1);
        int note22_2 = boardCtrl.get(2).get(2).get(3);
        assertEquals(0, note22_2);
        int sel00 = boardCtrl.get(0).get(0).get(0);
        assertEquals(0, sel00);
        int note00_0 = boardCtrl.get(0).get(0).get(1);
        assertEquals(1, note00_0);
        int note00_1 = boardCtrl.get(0).get(0).get(2);
        assertEquals(1, note00_1);
        int note00_2 = boardCtrl.get(0).get(0).get(3);
        assertEquals(0, note00_2);
    }
    @Test
    public void testCoordinateAsVector() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Bb8", "NotSoSecurePassword");
        Game g = new Game(k, u);
        g.setNote(0, 0, 1);
        g.setNote(0, 0, 2);
        g.setCell(2, 2, 2);
        g.setCell(0,2,2);   //error
        
        Vector<Integer> freePosCtrl = g.getFreePositionsForCtrl();
        int numFree = freePosCtrl.size();
        assertEquals(16, numFree);

        int sel22 = g.getCoordinateAsVector(2, 2).get(0);
        assertEquals(2, sel22);
        int note22_0 = g.getCoordinateAsVector(2, 2).get(1);
        assertEquals(0, note22_0);
        int note22_1 = g.getCoordinateAsVector(2, 2).get(2);
        assertEquals(0, note22_1);
        int note22_2 = g.getCoordinateAsVector(2, 2).get(3);
        assertEquals(0, note22_2);
        int sel00 = g.getCoordinateAsVector(0, 0).get(0);
        assertEquals(0, sel00);
        int note00_0 = g.getCoordinateAsVector(0, 0).get(1);
        assertEquals(1, note00_0);
        int note00_1 = g.getCoordinateAsVector(0, 0).get(2);
        assertEquals(1, note00_1);
        int note00_2 = g.getCoordinateAsVector(0, 0).get(3);
        assertEquals(0, note00_2);
    }
    //Test pause and resume

    @Test 
    public void testGamePauseAndGo() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Bb8", "NotSoSecurePassword");
        Game g = new Game(k, u);
        g.setNote(0, 0, 1);
        g.setNote(0, 0, 2);
        g.setCell(2, 2, 2);
        g.setCell(0,2,2);   //error
        g.pauseGame();
        assertEquals(false, g.getStatus());
        try { 
            g.setCell(1, 1, 3);
        }
        catch (IllegalGameAction e) {
            assertEquals("Game is not ongoing or over.", e.getMessage());
        }
        assertEquals(0, g.getBoard()[1][1].selection);
        try {
            g.setCell(1, 0, 3); //error but paused
        }
        catch (IllegalGameAction e) {
            assertEquals("Game is not ongoing or over.", e.getMessage());
        }
        assertEquals(0, g.getBoard()[1][0].selection);
        assertEquals(2, g.getLives());
        int aux = g.getFreePositionsSize();
        try {
            g.askHint();
        }
        catch (IllegalGameAction e) {
            assertEquals("Game is not ongoing or over.", e.getMessage());
        }
        assertEquals(0, g.getHints());
        assertEquals(aux, g.getFreePositionsSize());
        g.resumeGame();
        g.setCell(1, 1, 3);
        assertEquals(3, g.getBoard()[1][1].selection);
    }

    //Test restart

    @Test
    public void testGameRestart() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Bb8", "NotSoSecurePassword");
        Game g = new Game(k, u);
        g.setNote(0, 0, 1);
        g.setNote(0, 0, 2);
        g.setCell(2, 2, 2);
        g.setCell(0,2,2);   //error
        assertEquals(2, g.getLives());
        assertEquals(true, g.getStatus());
        assertEquals(2, g.getBoard()[2][2].selection);
        boolean[] n = {true, true, false};  
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][0].notes[t]);
        }
        g.setCell(0, 0, 3); //error
        assertEquals(1, g.getLives());
        g.askHint();
        g.askHint();
        assertEquals(2, g.getHints());
        assertEquals(6, g.getFreePositionsSize());
        g.restartGame();
        n[0] = n[1] = false;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][0].notes[t]);
        }
        assertEquals(0, g.getBoard()[0][2].selection);
        assertEquals(0, g.getBoard()[2][2].selection);
    }

    //Test setCell

    @Test 
    public void testSetCellWrong_i() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.setCell(-1, 2, 2);    //Wrong matrix "i" position (< 0)
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
        try {
            g.setCell(3, 2, 2);    //Wrong matrix "i" position (>= N)
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
    }

    @Test 
    public void testSetCellWrong_j() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.setCell(1, -4, 2);    //Wrong matrix "j" position (< 0)
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
        try {
            g.setCell(2, 5, 2);    //Wrong matrix "j" position (>= N)
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
    }

    @Test 
    public void testSetCellWrong_k() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.setCell(0, 2, 10);    //Wrong matrix selection (> N)
        }
        catch (IllegalCellSelection e) {
            assertEquals("Illegal cell selection: must be between 1 and board size.", e.getMessage());
        }
        assertFalse(g.getBoard()[0][2].selection == 10);
        try {
            g.setCell(1, 1, -1);    //Wrong matrix selection (<= 0)
        }
        catch (IllegalCellSelection e) {
            assertEquals("Illegal cell selection: must be between 1 and board size.", e.getMessage());
        }
        assertFalse(g.getBoard()[0][2].selection == -1);
        try {
            g.setCell(1, 1, 0);    //Wrong matrix selection (<= 0)
        }
        catch (IllegalCellSelection e) {
            assertEquals("Illegal cell selection: must be between 1 and board size.", e.getMessage());
        }
    }

    @Test 
    public void testSetCellRight_k() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        //Correct selection
        assertTrue(g.setCell(2, 2, 2));   
        assertTrue(g.getBoard()[2][2].selection == 2);
        //Wrong but valid selection
        assertFalse(g.setCell(1,1,2));
    }

    //Test unsetCell
    @Test
    public void testUnsetCellWrong_i() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.unsetCell(-1, 2);
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
        try {
            g.unsetCell(3, 1);
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
    }

    @Test
    public void testUnsetCellWrong_j() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
        g.unsetCell(1, -2);
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
        try {
            g.unsetCell(2, 4);
        }
        catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
    }

    @Test
    public void testUnsetCell1() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        g.unsetCell(1, 2);
        assertEquals(0, g.getBoard()[1][2].selection);
    }

    @Test
    public void testUnsetCell2() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        g.setCell(1, 2, 1);
        assertEquals(1, g.getBoard()[1][2].selection);
        g.unsetCell(1, 2);
        assertEquals(0, g.getBoard()[1][2].selection);
    }

    //Test setNote
    @Test 
    public void setNoteWrong_i() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.setNote(-1, 0, 3);
        } catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
        try {
            g.setNote(3, 0, 1);
        } catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
    }

    @Test 
    public void setNoteWrong_j() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.setNote(1, -2, 3);
        } catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
        try {
            g.setNote(2, 3, 1);
        } catch (IllegalMatrixPosition e) {
            assertEquals("Illegal board position: can't be accessed", e.getMessage());
        }
    }

    @Test 
    public void setNoteWrong_k() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        try {
            g.setNote(0, 0, -5);
        } catch (IllegalCellSelection e) {
            assertEquals("Illegal cell note: must be between 1 and board size.", e.getMessage());
        }
        try {
            g.setNote(2, 0, 4);
        } catch (IllegalCellSelection e) {
            assertEquals("Illegal cell note: must be between 1 and board size.", e.getMessage());
        }
    }

    @Test 
    public void setNoteNotOngoing() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        g.pauseGame();
        try {
            g.setNote(0, 0, 3);
        } catch (IllegalGameAction e) {
            assertEquals("Game is not ongoing or over.", e.getMessage());
        }
    }

    @Test 
    public void setNoteCorrect() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        g.setNote(0, 0, 1);
        g.setNote(0, 0, 2);
        boolean[] n = {true, true, false};
        for (int i = 0; i < n.length; ++i) {
            assertEquals(n[i], g.getBoard()[0][0].notes[i]);
        }
        g.setNote(0, 0, 3);
        n[2] = true;
        for (int i = 0; i < n.length; ++i) {
            assertEquals(n[i], g.getBoard()[0][0].notes[i]);
        }
        g.setNote(0, 0, 2);
        n[1] = false;
        for (int i = 0; i < n.length; ++i) {
            assertEquals(n[i], g.getBoard()[0][0].notes[i]);
        }
    }

    @Test
    public void testAskMaxHints() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(2, 5, new Vector<Integer>(Arrays.asList(0, 0, 1, 0))));
        board.add(new Region(1, 6, new Vector<Integer>(Arrays.asList(0, 1, 0, 2, 1, 1))));
        board.add(new Region(3, 24, new Vector<Integer>(Arrays.asList(0, 3, 0, 4, 1, 4))));
        board.add(new Region(2, 1, new Vector<Integer>(Arrays.asList(1, 2, 1, 3))));
        board.add(new Region(1, 9, new Vector<Integer>(Arrays.asList(2, 0, 3, 0, 4, 0))));
        board.add(new Region(1, 15, new Vector<Integer>(Arrays.asList(2, 1, 3, 1, 4, 1))));
        board.add(new Region(1, 12, new Vector<Integer>(Arrays.asList(2, 2, 2, 3, 3, 2, 3, 3))));
        board.add(new Region(2, 1, new Vector<Integer>(Arrays.asList(2, 4, 2, 5))));
        board.add(new Region(2, 5, new Vector<Integer>(Arrays.asList(3, 4, 3, 5))));
        board.add(new Region(2, 2, new Vector<Integer>(Arrays.asList(4, 2, 4, 3))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(5, 0))));
        board.add(new Region(2, 4, new Vector<Integer>(Arrays.asList(5, 1, 5, 2))));
        board.add(new Region(1, 10, new Vector<Integer>(Arrays.asList(4, 4, 5, 3, 5, 4))));
        board.add(new Region(4, 2, new Vector<Integer>(Arrays.asList(4, 5, 5, 5))));
        int[][] solution = {
            {6, 1, 2, 3, 4, 5},
            {1, 3, 5, 6, 2, 4},
            {4, 6, 1, 5, 3, 2},
            {3, 5, 4, 2, 6, 1},
            {2, 4, 3, 1, 5, 6},
            {5, 2, 6, 4, 1, 3}
        };
        Kenken k = new Kenken(2, 6, board, solution);
        User u = new User("Russell", "JackRussell");
        Game g = new Game(k, u);
        Coordinates c;
        c = g.askHint();            // 1
        assertFalse(c == null);
        c = g.askHint();            // 2
        assertFalse(c == null);
        c = g.askHint();            // 3
        assertFalse(c == null);
        c = g.askHint();            // 4
        assertFalse(c == null);
        c = g.askHint();            // 5
        assertFalse(c == null);
        c = g.askHint();            // 6
        assertFalse(c == null);
        c = g.askHint();            // 7
        assertFalse(c == null);
        c = g.askHint();            // 8
        assertFalse(c == null);
        c = g.askHint();            // 9
        assertFalse(c == null);
        c = g.askHint();            // 10
        assertFalse(c == null);
        c = g.askHint();            // Over maximum amount of hints
        assertTrue(c == null);
        assertTrue(g.getFreePositionsSize() == 26);     //All possible hints have been given.
    }

    @Test
    public void testAskHintInPause() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        Game g = new Game(k, u);
        g.pauseGame();
        try {
            g.askHint();
        } catch (IllegalGameAction e) {
            assertEquals("Game is not ongoing or over.", e.getMessage());
        }
    }

    @Test
    public void testGameSimulation3x3() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        User u = new User("Laura", "SuperSecurePassword2");
        LocalTime start, stop;
        Duration e, d;
        Game g = new Game(k, u);
        start = LocalTime.now();
        g.setNote(0, 0, 1);
        g.setNote(0, 0, 2);
        g.setCell(2, 2, 2);
        g.setCell(0,2,2);
        assertEquals(2, g.getLives());
        assertEquals(true, g.getStatus());
        assertEquals(2, g.getBoard()[2][2].selection);
        boolean[] n = {true, true, false};  // [0][0]: 1 si, 2 si, 3 no
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][0].notes[t]);
        }
        stop = LocalTime.now();
        d = Duration.between(stop, start);
        e = g.getDuration();
        assertEquals(d.toMinutes(), e.toMinutes());
        g.setNote(1, 1, 3);
        g.setNote(1, 1, 1);
        n[0] = true;
        n[1] = false;
        n[2] = true;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[1][1].notes[t]);
        }
        g.setCell(1, 1, 3);
        n[0] = false;
        n[1] = false;
        n[2] = false;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[1][1].notes[t]);
        }
        assertEquals(3, g.getBoard()[1][1].selection);
        g.setCell(0, 1, 2);
        assertEquals(2, g.getBoard()[0][1].selection);
        g.setCell(0, 2, 3);
        assertEquals(3, g.getBoard()[0][2].selection);
        assertEquals(5, g.getFreePositionsSize());
        stop = LocalTime.now();
        d = Duration.between(stop, start);
        g.pauseGame();
        assertEquals(false, g.getStatus());
        g.resumeGame();
        start = LocalTime.now();
        assertEquals(true, g.getStatus());
        g.unsetCell(0, 2);
        assertEquals(0, g.getBoard()[0][2].selection);
        assertEquals(6, g.getFreePositionsSize());
        n[0] = false;
        n[1] = false;
        n[2] = false;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][2].notes[t]);
        }
        g.setCell(0, 0, 1);
        g.setCell(0, 2, 3);
        g.setCell(1, 0, 2);
        assertEquals(3, g.getFreePositionsSize());
        Coordinates c = g.askHint();  
        assertEquals(2, g.getFreePositionsSize());
        assertEquals(1, g.getHints());
        if (c.getX() != 1 && c.getY() != 1) g.setCell(1, 1, 3);
        assertEquals(2, g.getFreePositionsSize());
        if (c.getX() != 1 || c.getY() != 2) g.setCell(1, 2, 1);
        if (c.getX() != 2 || c.getY() != 0) g.setCell(2, 0, 3); 
        if (c.getX() != 2 || c.getY() != 1) g.setCell(2, 1, 1); 
        assertEquals(0, g.getFreePositionsSize());
        assertEquals(true, g.boardIsComplete());
        stop = LocalTime.now();
        d = d.plus(Duration.between(stop, start));
        e = g.getDuration();
        assertEquals(d.toMinutes(), e.toMinutes());
    }

    @Test
    public void testGameSimulation6x6Loss() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(2, 5, new Vector<Integer>(Arrays.asList(0, 0, 1, 0))));
        board.add(new Region(1, 6, new Vector<Integer>(Arrays.asList(0, 1, 0, 2, 1, 1))));
        board.add(new Region(3, 24, new Vector<Integer>(Arrays.asList(0, 3, 0, 4, 1, 4))));
        board.add(new Region(2, 1, new Vector<Integer>(Arrays.asList(1, 2, 1, 3))));
        board.add(new Region(1, 9, new Vector<Integer>(Arrays.asList(2, 0, 3, 0, 4, 0))));
        board.add(new Region(1, 15, new Vector<Integer>(Arrays.asList(2, 1, 3, 1, 4, 1))));
        board.add(new Region(1, 12, new Vector<Integer>(Arrays.asList(2, 2, 2, 3, 3, 2, 3, 3))));
        board.add(new Region(2, 1, new Vector<Integer>(Arrays.asList(2, 4, 2, 5))));
        board.add(new Region(2, 5, new Vector<Integer>(Arrays.asList(3, 4, 3, 5))));
        board.add(new Region(2, 2, new Vector<Integer>(Arrays.asList(4, 2, 4, 3))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(5, 0))));
        board.add(new Region(2, 4, new Vector<Integer>(Arrays.asList(5, 1, 5, 2))));
        board.add(new Region(1, 10, new Vector<Integer>(Arrays.asList(4, 4, 5, 3, 5, 4))));
        board.add(new Region(4, 2, new Vector<Integer>(Arrays.asList(4, 5, 5, 5))));
        int[][] solution = {
            {6, 1, 2, 3, 4, 5},
            {1, 3, 5, 6, 2, 4},
            {4, 6, 1, 5, 3, 2},
            {3, 5, 4, 2, 6, 1},
            {2, 4, 3, 1, 5, 6},
            {5, 2, 6, 4, 1, 3}
        };
        Kenken k = new Kenken(2, 6, board, solution);
        User u = new User("Russell", "JackRussell");
        Game g = new Game(k, u);
        g.setCell(0,0, 5);
        g.setCell(0, 0, 3);
        g.setCell(0, 0, 2);
        assertEquals(0, g.getLives());
        assertEquals(0, g.getScore());
    }

    @Test
    public void testGameSimulation6x6() {
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(2, 5, new Vector<Integer>(Arrays.asList(0, 0, 1, 0))));
        board.add(new Region(1, 6, new Vector<Integer>(Arrays.asList(0, 1, 0, 2, 1, 1))));
        board.add(new Region(3, 24, new Vector<Integer>(Arrays.asList(0, 3, 0, 4, 1, 4))));
        board.add(new Region(2, 1, new Vector<Integer>(Arrays.asList(1, 2, 1, 3))));
        board.add(new Region(1, 9, new Vector<Integer>(Arrays.asList(2, 0, 3, 0, 4, 0))));
        board.add(new Region(1, 15, new Vector<Integer>(Arrays.asList(2, 1, 3, 1, 4, 1))));
        board.add(new Region(1, 12, new Vector<Integer>(Arrays.asList(2, 2, 2, 3, 3, 2, 3, 3))));
        board.add(new Region(2, 1, new Vector<Integer>(Arrays.asList(2, 4, 2, 5))));
        board.add(new Region(2, 5, new Vector<Integer>(Arrays.asList(3, 4, 3, 5))));
        board.add(new Region(2, 2, new Vector<Integer>(Arrays.asList(4, 2, 4, 3))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(5, 0))));
        board.add(new Region(2, 4, new Vector<Integer>(Arrays.asList(5, 1, 5, 2))));
        board.add(new Region(1, 10, new Vector<Integer>(Arrays.asList(4, 4, 5, 3, 5, 4))));
        board.add(new Region(4, 2, new Vector<Integer>(Arrays.asList(4, 5, 5, 5))));
        int[][] solution = {
            {6, 1, 2, 3, 4, 5},
            {1, 3, 5, 6, 2, 4},
            {4, 6, 1, 5, 3, 2},
            {3, 5, 4, 2, 6, 1},
            {2, 4, 3, 1, 5, 6},
            {5, 2, 6, 4, 1, 3}
        };
        Kenken k = new Kenken(2, 6, board, solution);
        User u = new User("Russell", "JackRussell");
        Game g = new Game(k, u);
        //  __ __ __ __ __ __ 
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        g.setNote(0, 0, 6);
        g.setNote(0, 0, 3);
        g.setNote(0, 0, 4);
        // Notas en [0][0]: _ _ 3 4 _ 6
        boolean[] n = {false, false, true, true, false, true};
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][0].notes[t]);
        }
        assertEquals(true, g.getStatus());
        if (g.getStatus()) g.pauseGame();
        assertEquals(false, g.getStatus());
        if (!g.getStatus()) g.resumeGame();
        assertEquals(true, g.getStatus());
        g.setNote(1,1, 5);
        g.setNote(1,1,6);
        // Notas en [1][1]: _ _ _ _ 5 6
        assertEquals(36, g.getFreePositionsSize());
        g.setCell(2, 2, 1);
        assertEquals(35, g.getFreePositionsSize());
        g.setCell(2, 2, 1);
        assertEquals(35, g.getFreePositionsSize());
        g.setCell(2, 2, 4);
        assertEquals(36, g.getFreePositionsSize());
        assertEquals(2, g.getLives());
        g.setCell(2, 2, 1);
        assertEquals(35, g.getFreePositionsSize());
        g.unsetCell(2, 2);
        assertEquals(36, g.getFreePositionsSize());
        g.setCell(2, 2, 1);
        //  __ __ __ __ __ __ 
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |1 |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        g.setCell(2, 3, 5);
        g.setCell(2, 4, 3);
        assertEquals(33, g.getFreePositionsSize());
        //  __ __ __ __ __ __ 
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |1 |5 |3 |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        g.setNote(0, 0, 6);
        n[5] = false;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[0][0].notes[t]);
        }
        g.setCell(0, 0, 6);
        g.setCell(0, 1, 1);
        g.setCell(0, 2, 2);
        g.setCell(0, 3, 3);
        g.setCell(0, 4, 4);
        g.setCell(0, 5, 5);
        assertEquals(27, g.getFreePositionsSize());
        //  __ __ __ __ __ __ 
        // |6 |1 |2 |3 |4 |5 |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |1 |5 |3 |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        g.pauseGame();
        assertEquals(false, g.getStatus());
        g.resumeGame();
        g.setCell(5, 0, 5);
        g.setCell(5, 1, 2);
        g.setCell(5, 2, 6);
        g.setCell(5, 3, 4);
        g.setCell(5, 4, 1);
        g.setCell(5, 5, 3);
        assertEquals(21, g.getFreePositionsSize());
        g.setCell(5, 5, 2);
        assertEquals(22, g.getFreePositionsSize());
        assertEquals(1, g.getLives());  //Queda 1 vida
        g.setCell(5, 5, 3);
        assertEquals(21, g.getFreePositionsSize());
        //  __ __ __ __ __ __ 
        // |6 |1 |2 |3 |4 |5 |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |1 |5 |3 |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |5 |2 |6 |4 |1 |3 |
        //  -- -- -- -- -- --
        g.setNote(4, 0, 1);
        g.setNote(4, 0 ,4);
        g.setNote(4, 0, 5);
        n[0] = n[3] = n[4] = true;
        n[1] = n[2] = n[5] = false;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[4][0].notes[t]);
        }
        g.setCell(4, 0, 2);
        n[0] = n[3] = n[4] = false;
        for (int t = 0; t < n.length; ++t) {
            assertEquals(n[t],g.getBoard()[4][0].notes[t]);
        }
        assertEquals(2, g.getBoard()[4][0].selection);
        g.setCell(4, 1, 4);
        g.setCell(4, 2, 3);
        g.setCell(4, 3, 1);
        g.setCell(4, 4, 5);
        g.setCell(4, 5, 6);
        assertEquals(1, g.getLives());
        assertEquals(15, g.getFreePositionsSize());
        g.setCell(3, 0, 3);
        g.setCell(3, 1, 5);
        g.setCell(3, 2, 4);
        g.setCell(3, 3, 2);
        g.setCell(3, 4, 6);
        g.setCell(3, 5, 1);
        assertEquals(9, g.getFreePositionsSize());
        //  __ __ __ __ __ __ 
        // |6 |1 |2 |3 |4 |5 |
        //  -- -- -- -- -- --
        // |  |  |  |  |  |  |
        //  -- -- -- -- -- --
        // |  |  |1 |5 |3 |  |
        //  -- -- -- -- -- --
        // |3 |5 |4 |2 |6 |1 |
        //  -- -- -- -- -- --
        // |2 |4 |3 |1 |5 |6 |
        //  -- -- -- -- -- --
        // |5 |2 |6 |4 |1 |3 |
        //  -- -- -- -- -- --
        g.setCell(1, 0, 1);
        g.setCell(1, 1, 3);
        g.setCell(1, 2, 5);
        g.setCell(1, 3, 6);
        g.setCell(1, 4, 2);
        g.setCell(1, 5, 4);
        assertEquals(3, g.getFreePositionsSize());
        g.setCell(2, 0, 4);
        g.setCell(2, 1, 6);
        assertEquals(1, g.getFreePositionsSize());
        //  __ __ __ __ __ __ 
        // |6 |1 |2 |3 |4 |5 |
        //  -- -- -- -- -- --
        // |1 |3 |5 |6 |2 |4 |
        //  -- -- -- -- -- --
        // |4 |6 |1 |5 |3 |  |
        //  -- -- -- -- -- --
        // |3 |5 |4 |2 |6 |1 |
        //  -- -- -- -- -- --
        // |2 |4 |3 |1 |5 |6 |
        //  -- -- -- -- -- --
        // |5 |2 |6 |4 |1 |3 |
        //  -- -- -- -- -- --
        g.askHint();
        assertEquals(1, g.getHints());
        assertEquals(0, g.getFreePositionsSize());
        assertEquals(true, g.boardIsComplete());
        //Game ended with 2 errors, 1 hint and under 3 minutes -> score should be 10
        assertEquals(10, g.getScore());
    }
}
