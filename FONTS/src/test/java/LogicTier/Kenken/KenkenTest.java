package LogicTier.Kenken;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import LogicTier.Region.Region;
import LogicTier.TestRunner.TestRunner;

public class KenkenTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(KenkenTest.class);
    }

    @Test
    public void testEmptyKenken() {
        //int[] operations = new int[0];
        Vector<Region> board = new Vector<Region>();
        int[][] solution = new int[0][0];
        Kenken k = new Kenken(0, 0, board, solution);
        assertEquals(0, k.getIdKenken());
        assertEquals(0, k.getBoardSize());
        for (int i = 0; i < solution.length; ++i) {
            for (int j = 0; j < solution.length; ++j) {
                assertEquals(solution[i][j], k.getSolution()[i][j]);
            }
        }
    }

    @Test 
    public void testKenkenCreator() {
        //int[] operations = {1};
        Vector<Region> board = new Vector<Region>();
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(0, 0, 0, 1))));
        board.add(new Region(1, 4, new Vector<Integer>(Arrays.asList(0, 2, 1, 2))));
        board.add(new Region(1, 5, new Vector<Integer>(Arrays.asList(1, 0, 1, 1))));
        board.add(new Region(0, 3, new Vector<Integer>(Arrays.asList(2, 0))));
        board.add(new Region(1, 3, new Vector<Integer>(Arrays.asList(2, 1, 2, 2))));
        int[][] solution = { 
            {1, 2, 3},
            {2, 3, 1},
            {3, 1, 2}
        };
        Kenken k = new Kenken(1, 3, board, solution);
        assertEquals(1, k.getIdKenken());
        assertEquals(3, k.getBoardSize());
        for (int i = 0; i < solution.length; ++i) {
            for (int j = 0; j < solution.length; ++j) {
                assertEquals(solution[i][j], k.getSolution()[i][j]);
            }
        }
    }

    @Test 
    public void testKenkenCreatorMultipleOperations() {
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
        board.add(new Region(0, 5, new Vector<Integer>(Arrays.asList(5, 0))));
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
        int[] operations = {0, 1, 2, 3, 4};
        Kenken k = new Kenken(2, 6, board, solution);
        for (int i = 0; i < solution.length; ++i) {
            for (int j = 0; j < solution.length; ++j) {
                assertEquals(solution[i][j], k.getSolution()[i][j]);
            }
        }
        for (int i = 0; i < operations.length; ++i) {
            assertEquals(operations[i], k.getOperations()[i]);
        }
    }
}
