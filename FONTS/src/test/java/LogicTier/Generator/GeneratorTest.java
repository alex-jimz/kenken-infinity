package LogicTier.Generator;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Vector;

import LogicTier.Translator.Translator;
import LogicTier.Verifier.Verifier;
import LogicTier.GSolver.GSolver;
import LogicTier.Operation.Operation;
import LogicTier.Region.Region;
import LogicTier.TestRunner.TestRunner;

public class GeneratorTest {
    public static void main(String[] args) {
      TestRunner.runTestClass(GeneratorTest.class);
    }

    private static boolean isLatinSquare(int[][] matrix, int n) {
        // Check if all rows and columns have unique elements
        for (int i = 0; i < n; i++) {
            if (!hasUniqueElements(matrix[i]) || !hasUniqueElements(getColumn(matrix, i))) {
                return false;
            }
        }
        
        // Check if each element is between 1 and n
        for (int[] row : matrix) {
            for (int num : row) {
                if (num < 1 || num > n) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // Method to check if an array has unique elements
    private static boolean hasUniqueElements(int[] v) {
        boolean[] seen = new boolean[v.length];
        for (int num : v) {
            if (seen[num - 1]) {
                return false;
            }
            seen[num - 1] = true;
        }
        return true;
    }
    
    // Method to extract a column from a matrix
    private static int[] getColumn(int[][] matrix, int col) {
        int[] column = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][col];
        }
        return column;
    }

    @Test
    public void latinSquareSmallN() {
        for (int n = 3; n <= 9; ++n) {
            int[][] solution = Generator.latinSquare(n);
            assertTrue(isLatinSquare(solution, n));
        }
    }

    @Test
    public void latinSquareMediumN() {
        for (int n = 10; n <= 19; ++n) {
            int[][] solution = Generator.latinSquare(n);
            assertTrue(isLatinSquare(solution, n));
        }
    }

    @Test
    public void latinSquareBigN() {
        for (int n = 20; n <= 50; ++n) {
            int[][] solution = Generator.latinSquare(n);
            assertTrue(isLatinSquare(solution, n));
        }
    }

    private static Vector<String> convertVector(Vector<Vector<Integer>> input) {
        Vector<String> output = new Vector<>();

        for (Vector<Integer> innerVector : input) {
            StringBuilder sb = new StringBuilder();
            for (Integer num : innerVector) {
                sb.append(num).append(" ");
            }
            // Remove the trailing space
            sb.deleteCharAt(sb.length() - 1);
            output.add(sb.toString());
        }

        return output;
    }

    @Test
    public void generateRegularKenken() {
        Vector<Integer> operations = new Vector<>(java.util.List.of(0, 1, 2, 3, 4));
        Translator translator = new Translator();
        Verifier verifier = new Verifier();
        for (int n = 3; n <= 9; ++n) {
            int[][] solution = Generator.latinSquare(n);
            Vector<Vector<Integer>> output = Generator.generateRandom(n, operations, solution);
            if (output != null) {
                Vector<String> stringFormat = convertVector(output);
                assertTrue(verifier.verify(stringFormat));
                assertTrue(output.equals(verifier.getIntegerFormat()));
                Vector<Region> regions = translator.translateKenken(output);
                GSolver solver = new GSolver(regions, n);
                solver.solve();
                int[][] solverSolution = solver.getSolution();
                assertTrue(solver.isSolved() && solver.isUnique() && Arrays.deepEquals(solution, solverSolution));
            }
        }
    }

    @Test
    public void generateWithAllOperations() {
        Vector<Integer> operations = new Vector<>(); 
        Arrays.stream(Operation.getOperationIds()).forEach(operations::add); // get all operations
        Translator translator = new Translator();
        Verifier verifier = new Verifier();
        for (int n = 3; n <= 9; ++n) {
            int[][] solution = Generator.latinSquare(n);
            Vector<Vector<Integer>> output = Generator.generateRandom(n, operations, solution);
            if (output != null) {
                Vector<String> stringFormat = convertVector(output);
                assertTrue(verifier.verify(stringFormat));
                assertTrue(output.equals(verifier.getIntegerFormat()));
                Vector<Region> regions = translator.translateKenken(output);
                GSolver solver = new GSolver(regions, n);
                solver.solve();
                int[][] solverSolution = solver.getSolution();
                assertTrue(solver.isSolved() && solver.isUnique() && Arrays.deepEquals(solution, solverSolution));
            }
        }
    }

    @Test
    public void generateBigWithProduct() {
        Vector<Integer> operations = new Vector<>(java.util.List.of(0, 3));
        Translator translator = new Translator();
        Verifier verifier = new Verifier();
        for (int n = 3; n <= 15; ++n) {
            int[][] solution = Generator.latinSquare(n);
            Vector<Vector<Integer>> output = Generator.generateRandom(n, operations, solution);
            if (output != null) {
                Vector<String> stringFormat = convertVector(output);
                assertTrue(verifier.verify(stringFormat));
                assertTrue(output.equals(verifier.getIntegerFormat()));
                Vector<Region> regions = translator.translateKenken(output);
                GSolver solver = new GSolver(regions, n);
                solver.solve();
                int[][] solverSolution = solver.getSolution();
                assertTrue(solver.isSolved() && solver.isUnique() && Arrays.deepEquals(solution, solverSolution));
            }
        }
    }

    @Test
    public void generateJustSubtraction() {
        Vector<Integer> operations = new Vector<>(java.util.List.of(0, 2));
        Translator translator = new Translator();
        Verifier verifier = new Verifier();
        for (int n = 3; n <= 6; ++n) {
            int[][] solution = Generator.latinSquare(n);
            Vector<Vector<Integer>> output = Generator.generateRandom(n, operations, solution);
            if (output != null) {
                Vector<String> stringFormat = convertVector(output);
                assertTrue(verifier.verify(stringFormat));
                assertTrue(output.equals(verifier.getIntegerFormat()));
                Vector<Region> regions = translator.translateKenken(output);
                GSolver solver = new GSolver(regions, n);
                solver.solve();
                int[][] solverSolution = solver.getSolution();
                assertTrue(solver.isSolved() && solver.isUnique() && Arrays.deepEquals(solution, solverSolution));
            }
        }
    }
}