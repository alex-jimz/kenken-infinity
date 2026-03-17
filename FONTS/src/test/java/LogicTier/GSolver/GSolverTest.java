package LogicTier.GSolver;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import LogicTier.Region.Region;
import LogicTier.Solver.Solver;
import LogicTier.TestRunner.TestRunner;

public class GSolverTest {

    public static void main(String[] args) {
      TestRunner.runTestClass(GSolverTest.class);
    }

    @Test
    public void easy3x3Kenken() {
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(0, 3, new Vector<Integer>() {{ add(0); add(0); }}));
      r.add(new Region(1, 5, new Vector<Integer>() {{ add(0); add(1); add(1); add(1); }}));
      r.add(new Region(1, 3, new Vector<Integer>() {{ add(0); add(2); add(1); add(2); }}));
      r.add(new Region(3, 2, new Vector<Integer>() {{ add(1); add(0); add(2); add(0); }}));
      r.add(new Region(2, 2, new Vector<Integer>() {{ add(2); add(1); add(2); add(2); }}));
      int n = 3; 
      Solver solver = new GSolver(r, n);
      int[][] expected = {
        {3,2,1},
        {1,3,2},
        {2,1,3}
      };
      solver.solve();
      assertTrue(Arrays.deepEquals(solver.getSolution(), expected));
      assertTrue(solver.isUnique());
    }

    @Test
    public void medium6x6Kenken() {
      // wikipedia
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(1, 11, new Vector<Integer>() {{ add(0); add(0); add(1); add(0); }}));
      r.add(new Region(4, 2, new Vector<Integer>() {{ add(0); add(1); add(0); add(2); }}));
      r.add(new Region(3, 20, new Vector<Integer>() {{ add(0); add(3); add(1); add(3); }}));
      r.add(new Region(3, 6, new Vector<Integer>() {{ add(0); add(4); add(0); add(5); add(1); add(5); add(2); add(5); }}));
      r.add(new Region(2, 3, new Vector<Integer>() {{ add(1); add(1); add(1); add(2); }}));
      r.add(new Region(4, 3, new Vector<Integer>() {{ add(1); add(4); add(2); add(4); }}));
      r.add(new Region(3, 240, new Vector<Integer>() {{ add(2); add(0); add(2); add(1); add(3); add(0); add(3); add(1); }}));
      r.add(new Region(3, 6, new Vector<Integer>() {{ add(2); add(2); add(2); add(3); }}));
      r.add(new Region(3, 6, new Vector<Integer>() {{ add(3); add(2); add(4); add(2); }}));
      r.add(new Region(1, 7, new Vector<Integer>() {{ add(3); add(3); add(4); add(3); add(4); add(4); }}));
      r.add(new Region(3, 30, new Vector<Integer>() {{ add(3); add(4); add(3); add(5); }}));
      r.add(new Region(3, 6, new Vector<Integer>() {{ add(4); add(0); add(4); add(1); }}));
      r.add(new Region(1, 9, new Vector<Integer>() {{ add(4); add(5); add(5); add(5); }}));
      r.add(new Region(1, 8, new Vector<Integer>() {{ add(5); add(0); add(5); add(1); add(5); add(2); }}));
      r.add(new Region(4, 2, new Vector<Integer>() {{ add(5); add(3); add(5); add(4); }}));

      int n = 6; 
      Solver solver = new GSolver(r, n);
      int[][] expected = {
        {5,6,3,4,1,2},
        {6,1,4,5,2,3},
        {4,5,2,3,6,1},
        {3,4,1,2,5,6},
        {2,3,6,1,4,5},
        {1,2,5,6,3,4}
      };
      solver.solve();
      assertTrue(Arrays.deepEquals(solver.getSolution(), expected));
      assertTrue(solver.isUnique());
    }

    @Test
    public void hard9x9Kenken() {
      // PUZZLE NO. 209510, 9X9, MEDIUM
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(2, 6, new Vector<Integer>() {{ add(0); add(0); add(1); add(0); }}));
      r.add(new Region(2, 1, new Vector<Integer>() {{ add(0); add(1); add(1); add(1); }}));
      r.add(new Region(4, 2, new Vector<Integer>() {{ add(0); add(2); add(1); add(2); }}));
      r.add(new Region(2, 2, new Vector<Integer>() {{ add(0); add(3); add(0); add(4); }}));
      r.add(new Region(1, 17, new Vector<Integer>() {{ add(1); add(3); add(1); add(4); }}));
      r.add(new Region(3, 15, new Vector<Integer>() {{ add(0); add(5); add(0); add(6); }}));
      r.add(new Region(3, 280, new Vector<Integer>() {{ add(0); add(7); add(0); add(8); add(1); add(6); add(1); add(7); }}));
      r.add(new Region(1, 14, new Vector<Integer>() {{ add(1); add(5); add(2); add(4); add(2); add(5); }}));
      r.add(new Region(1, 3, new Vector<Integer>() {{ add(1); add(8); add(2); add(8); }}));
      r.add(new Region(2, 8, new Vector<Integer>() {{ add(2); add(0); add(2); add(1); }}));
      r.add(new Region(3, 21, new Vector<Integer>() {{ add(2); add(2); add(2); add(3); }}));
      r.add(new Region(1, 19, new Vector<Integer>() {{ add(2); add(6); add(2); add(7); add(3); add(6); add(4); add(6); }}));
      r.add(new Region(3, 128, new Vector<Integer>() {{ add(3); add(0); add(4); add(0); add(4); add(1); }}));
      r.add(new Region(3, 810, new Vector<Integer>() {{ add(3); add(1); add(3); add(2); add(3); add(3); add(4); add(2); }}));
      r.add(new Region(2, 2, new Vector<Integer>() {{ add(3); add(4); add(3); add(5); }}));
      r.add(new Region(4, 2, new Vector<Integer>() {{ add(4); add(4); add(4); add(5); }}));
      r.add(new Region(3, 280, new Vector<Integer>() {{ add(3); add(7); add(3); add(8); add(4); add(8); }}));
      r.add(new Region(3, 42, new Vector<Integer>() {{ add(4); add(3); add(5); add(3); add(5); add(4); }}));
      r.add(new Region(3, 21, new Vector<Integer>() {{ add(4); add(7); add(5); add(7); }}));
      r.add(new Region(1, 14, new Vector<Integer>() {{ add(5); add(0); add(5); add(1); add(6); add(0); }}));
      r.add(new Region(0, 8, new Vector<Integer>() {{ add(5); add(2); }}));
      r.add(new Region(3, 15, new Vector<Integer>() {{ add(6); add(1); add(6); add(2); add(6); add(3); }}));
      r.add(new Region(1, 18, new Vector<Integer>() {{ add(6); add(4); add(7); add(2); add(7); add(3); add(7); add(4); }}));
      r.add(new Region(2, 8, new Vector<Integer>() {{ add(5); add(5); add(5); add(6); }}));
      r.add(new Region(1, 13, new Vector<Integer>() {{ add(6); add(5); add(6); add(6); }}));
      r.add(new Region(2, 2, new Vector<Integer>() {{ add(5); add(8); add(6); add(8); }}));
      r.add(new Region(3, 72, new Vector<Integer>() {{ add(6); add(7); add(7); add(7); add(7); add(8); }}));
      r.add(new Region(2, 5, new Vector<Integer>() {{ add(7); add(0); add(7); add(1); }}));
      r.add(new Region(2, 3, new Vector<Integer>() {{ add(8); add(0); add(8); add(1); }}));
      r.add(new Region(1, 15, new Vector<Integer>() {{ add(8); add(2); add(8); add(3); add(8); add(4); }}));
      r.add(new Region(3, 56, new Vector<Integer>() {{ add(7); add(5); add(7); add(6); add(8); add(5); }}));
      r.add(new Region(2, 5, new Vector<Integer>() {{ add(8); add(6); add(8); add(7); }}));
      r.add(new Region(0, 3, new Vector<Integer>() {{ add(8); add(8); }}));

      int n = 9; 
      Solver solver = new GSolver(r, n);
      int[][] expected = {
        {9,7,2,4,6,5,3,1,8},
        {3,6,4,8,9,2,7,5,1},
        {1,9,7,3,4,8,5,6,2},
        {4,2,5,9,1,3,6,8,7},
        {8,4,9,1,3,6,2,7,5},
        {2,5,8,6,7,9,1,3,4},
        {7,3,1,5,8,4,9,2,6},
        {6,1,3,2,5,7,8,4,9},
        {5,8,6,7,2,1,4,9,3}
      };
      solver.solve();
      assertTrue(Arrays.deepEquals(solver.getSolution(), expected));
      assertTrue(solver.isUnique());
    }
    
    @Test
    public void AM5x5Kenken() {
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(3, 40, new Vector<Integer>() {{ add(0); add(0); add(0); add(1); add(1); add(0); add(0); add(2); }}));
      r.add(new Region(5, 3, new Vector<Integer>() {{ add(0); add(3); add(0); add(4); add(1); add(4); }}));
      r.add(new Region(0, 1, new Vector<Integer>() {{ add(1); add(1); }}));
      r.add(new Region(0, 3, new Vector<Integer>() {{ add(1); add(3); }}));
      r.add(new Region(0, 2, new Vector<Integer>() {{ add(2); add(0); }}));
      r.add(new Region(0, 3, new Vector<Integer>() {{ add(3); add(0); }}));
      r.add(new Region(3, 300, new Vector<Integer>() {{ add(1); add(2); add(2); add(1); add(2); add(2); add(2); add(3); }}));
      r.add(new Region(3, 120, new Vector<Integer>() {{ add(3); add(1); add(4); add(0); add(4); add(1); add(4); add(2); }}));
      r.add(new Region(2, 1, new Vector<Integer>() {{ add(3); add(2); add(3); add(3); }}));
      r.add(new Region(3, 4, new Vector<Integer>() {{ add(4); add(3); add(4); add(4); }}));
      r.add(new Region(5, 3, new Vector<Integer>() {{ add(2); add(4); add(3); add(4); }}));
      int n = 5; 
      Solver solver = new GSolver(r, n);
      int[][] expected = {
        {1,5,2,4,3},
        {4,1,5,3,2},
        {2,3,4,5,1},
        {3,4,1,2,5},
        {5,2,3,1,4}
      };
      solver.solve();
      assertTrue(Arrays.deepEquals(solver.getSolution(), expected));
      assertTrue(solver.isUnique());
    }

    @Test
    public void max3x3Kenken() {
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(6, 2, new Vector<Integer>() {{ add(0); add(0); add(0); add(1); add(1); add(0); }}));
      r.add(new Region(6, 3, new Vector<Integer>() {{ add(0); add(2); add(1); add(1); add(1); add(2); add(2); add(0); add(2); add(1); }}));
      r.add(new Region(0, 2, new Vector<Integer>() {{ add(2); add(2); }}));
      int n = 3; 
      Solver solver = new GSolver(r, n);
      int[][] expected = {
        {1,2,3},
        {2,3,1},
        {3,1,2}
      };
      solver.solve();
      assertTrue(Arrays.deepEquals(solver.getSolution(), expected));
      assertTrue(solver.isUnique());
    }

    @Test
    public void multipleSol3x3() {
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(0, 3, new Vector<Integer>() {{ add(0); add(0); }}));
      r.add(new Region(1, 3, new Vector<Integer>() {{ add(0); add(1); add(0); add(2); }}));
      r.add(new Region(4, 2, new Vector<Integer>() {{ add(1); add(0); add(2); add(0); }}));
      r.add(new Region(3, 6, new Vector<Integer>() {{ add(1); add(1); add(1); add(2); }}));
      r.add(new Region(2, 2, new Vector<Integer>() {{ add(2); add(1); add(2); add(2); }}));
      int n = 3; 
      Solver solver = new GSolver(r, n);
      solver.solve();
      assertTrue(!solver.isUnique());
    }
    
    @Test
    public void multipleSol4x4() {
      Vector<Region> r = new Vector<Region>();
      r.add(new Region(0, 4, new Vector<Integer>() {{ add(3); add(0); }}));
      r.add(new Region(1, 13, new Vector<Integer>() {{ add(0); add(0); add(0); add(1); add(1); add(0); add(1); add(1); add(2); add(0); }}));
      r.add(new Region(1, 11, new Vector<Integer>() {{ add(3); add(1); add(3); add(2); add(3); add(3); add(2); add(3); add(1); add(3); }}));
      r.add(new Region(6, 4, new Vector<Integer>() {{ add(0); add(2); add(0); add(3); add(1); add(2); }}));
      r.add(new Region(6, 2, new Vector<Integer>() {{ add(2); add(1); add(2); add(2); }}));
      int n = 4; 
      Solver solver = new GSolver(r, n);
      solver.solve();
      assertTrue(!solver.isUnique());
    }

    @Test
    public void primeFactorizationTest1() {
      Vector<Region> r = new Vector<Region>();
      GSolver solver = new GSolver(r, 10);
      int[] result = solver.primeFactorization(10);
      int[] expected = {0, 1, 0, 0, 1, 0, 0, 0, 0, 0}; // 2^1 * 5^1
      assertArrayEquals(expected, result);
    }

    @Test
    public void primeFactorizationTest2() {
      Vector<Region> r = new Vector<Region>();
      GSolver solver = new GSolver(r, 8);
      int[] result = solver.primeFactorization(24);
      int[] expected = {0, 3, 1, 0, 0, 0, 0, 0}; // 2^3 * 3^1
      assertArrayEquals(expected, result);
    }

    @Test
    public void primeFactorizationTest3() {
      Vector<Region> r = new Vector<Region>();
      GSolver solver = new GSolver(r, 10);
      int[] result = solver.primeFactorization(1);
      int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 1 has no prime factors
      assertArrayEquals(expected, result);
    }

    @Test
    public void primeFactorizationTest4() {
        Vector<Region> r = new Vector<Region>();
        GSolver solver = new GSolver(r, 30);
        int[] result = solver.primeFactorization(29);
        int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}; // 29 is a prime number
        assertArrayEquals(expected, result);
    }

    @Test
    public void primeFactorizationTest5() {
        Vector<Region> r = new Vector<Region>();
        GSolver solver = new GSolver(r, 12);
        int[] result = solver.primeFactorization(33);
        int[] expected = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}; // 3^1 * 11^1
        assertArrayEquals(expected, result);
    }

    @Test
    public void primeFactorizationTest6() {
        Vector<Region> r = new Vector<Region>();
        GSolver solver = new GSolver(r, 5);
        int[] result = solver.primeFactorization(48);
        int[] expected = {0, 4, 1, 0, 0}; // 2^4 * 3^1
        assertArrayEquals(expected, result);
    }

    @Test
    public void primeFactorizationTest7() {
        Vector<Region> r = new Vector<Region>();
        GSolver solver = new GSolver(r, 3);
        int[] result = solver.primeFactorization(64);
        int[] expected = {0, 6, 0}; // 2^6
        assertArrayEquals(expected, result);
    }
}
