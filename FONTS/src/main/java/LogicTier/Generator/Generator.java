package LogicTier.Generator;

import java.util.concurrent.*;
import java.util.*;

import LogicTier.Coordinates.Coordinates;
import LogicTier.GSolver.GSolver;
import LogicTier.Operation.Operation;
import LogicTier.Region.Region;
import LogicTier.Translator.Translator;

public class Generator {
    // Static data
    private static Random rand = new Random();
    private static Translator translator = new Translator();
    private static double[] probabilities = new double[]{0.00, 0.40, 0.35, 0.19, 0.02, 0.01, 0.005};
    private static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // Constructor without values
    private Generator() {}

    // Generates a random integer between 0 and n-1
    private static int randInt(int n) {
        return rand.nextInt(n);
    }

    // Returns an array with the values a and b, randomly ordered
    private static int[] rand2(int a, int b) {
        if (randInt(2) == 0) {
            return new int[]{a, b};
        }
        return new int[]{b, a};
    }

    // Returns a Latin Square of size nxn
    public static int[][] latinSquare(int n) {
        int[][] xy = new int[n][n];
        int[][] xz = new int[n][n];
        int[][] yz = new int[n][n];

        // Initial cyclic cube
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int k = (i + j) % n;
                xy[i][j] = k;
                xz[i][k] = j;
                yz[j][k] = i;
            }
        }

        int mxy = 0, mxz = 0, myz = 0;
        int[] m = new int[3];
        boolean proper = true;

        int minIter = n*n*n;
        for (int iter = 0; iter < minIter || !proper; iter++) {
            int i, j, k, i2, j2, k2, i2_, j2_, k2_;

            if (proper) {
                i = randInt(n);
                j = randInt(n);
                k = randInt(n);
                while (xy[i][j] == k) {
                    i = randInt(n);
                    j = randInt(n);
                    k = randInt(n);
                }
                i2 = yz[j][k];
                j2 = xz[i][k];
                k2 = xy[i][j];
                i2_ = i;
                j2_ = j;
                k2_ = k;
            } 
            else {
                i = m[0];
                j = m[1];
                k = m[2];
                int[] temp_i2 = rand2(yz[j][k], myz);
                i2 = temp_i2[0];
                i2_ = temp_i2[1];
                int[] temp_j2 = rand2(xz[i][k], mxz);
                j2 = temp_j2[0];
                j2_ = temp_j2[1];
                int[] temp_k2 = rand2(xy[i][j], mxy);
                k2 = temp_k2[0];
                k2_ = temp_k2[1];
            }

            // check if proper and save relevant information
            proper = xy[i2][j2] == k2;
            if (!proper) {
                m[0] = i2;
                m[1] = j2;
                m[2] = k2;
                mxy = xy[i2][j2];
                myz = yz[j2][k2];
                mxz = xz[i2][k2];
            }

            xy[i][j] = k2_;
            xy[i][j2] = k2;
            xy[i2][j] = k2;
            xy[i2][j2] = k;

            yz[j][k] = i2_;
            yz[j][k2] = i2;
            yz[j2][k] = i2;
            yz[j2][k2] = i;

            xz[i][k] = j2_;
            xz[i][k2] = j2;
            xz[i2][k] = j2;
            xz[i2][k2] = j;
        }
        
        // Add 1 to each cell so that the numbers go from 1 to n
        for (int i = 0; i < xy.length; i++) {
            for (int j = 0; j < xy[i].length; j++) {
                xy[i][j] += 1;
            }
        }

        return xy;
    }

    // Returns an integer between 1 and 7, according to the probabilities of scaledProb
    private static int getRegionSize(double[] scaledProb) {
        // Generating random number according to probabilities
        double randNum = rand.nextDouble(); // random number between 0 and 1
        double cumulativeProb = 0;
        int randomNumber = 0;
        for (int i = 0; i < scaledProb.length; i++) {
            cumulativeProb += scaledProb[i];
            if (randNum < cumulativeProb) {
                randomNumber = i + 1;
                return randomNumber;
            }
        }
        // should never get here
        return -1;
    }

    // Defines a potential Kenken with the given solution, using no other operations than the given ones
    private static Vector<Vector<Integer>> defineRegions(int[][] solution, Vector<Integer> operations, double[] scaledProb) {
        int n = solution.length;
        Vector<Vector<Integer>> output = new Vector<Vector<Integer>>();
        Vector<Integer> firstRow = new Vector<>();
        output.add(firstRow);
        int nRegions = 0;

        // Declare and initialize the visited matrix
        Boolean[][] vis = new Boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(vis[i], false);
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!vis[i][j]) {
                    // Generate the size of the region, using the probabilities in scaledProb
                    int regionSize = getRegionSize(scaledProb);
                    Vector<Integer> coords = new Vector<>();

                    // We expand the region until we reach its size or we have no available directions left
                    expandRegion(new int[]{regionSize}, vis, coords, i, j);
                    ++nRegions;

                    // We save the numbers of the solution in the cells of the region
                    Vector<Integer> operands = new Vector<>();
                    for (int x = 0; x < coords.size(); x += 2) {
                        operands.add(solution[coords.get(x)-1][coords.get(x+1)-1]);
                    }
                    regionSize = operands.size();

                    // We choose an operation and calculate the result
                    Collections.shuffle(operations);
                    int op = 0;
                    int result = -1;
                    while (op < operations.size()) {
                        try {
                            result = Operation.operateById(operations.get(op), operands);
                            break;
                        } catch (Exception e) {
                            ++op;
                        }
                    }
                    if (op == operations.size()) return null;

                    coords.add(0, regionSize);
                    coords.add(0, result);
                    coords.add(0, operations.get(op));
                    output.add(coords);
                }
            }
        }
        firstRow.add(n);
        firstRow.add(nRegions);
        return output;
    }

    // Returns true if size is a valid number of operands for at least 1 of the operations in ops
    private static boolean isRegionSizeValidForSomeOp(int size, Vector<Integer> ops) {
        for (int opId : ops) {
            if (Operation.correctNumOperandsById(opId, size)) return true;
        }
        return false;
    }

    // Returns the scaled probabilities using the given operations
    private static double[] scaleProbabilities(int n, Vector<Integer> ops) {
        if (n < 3) n = 3;
        if (n > 7) n = 7;

        // Scaling probabilities for other values of n
        double[] scaledProb = new double[n];
        double totalProbability = 0;
        for (int i = 0; i < n; i++) {
            scaledProb[i] = isRegionSizeValidForSomeOp(i+1, ops) ? probabilities[i] : 0;
            totalProbability += scaledProb[i];
        }
        if (totalProbability == 0) scaledProb[0] = 1.0;
        // Normalize probabilities to ensure the total probability sums up to 1
        for (int i = 0; i < n; i++) {
            scaledProb[i] /= totalProbability;
        }
        return scaledProb;
    }

    // Returns a valid Kenken of size nxn with solution as its unique solution, using only operations in operations
    // If it does not generate a Kenken with those conditions in 200 iterations, it returns null
    public static Vector<Vector<Integer>> generateRandom(int n, Vector<Integer> operations, int[][] solution){
        double[] scaledProb = scaleProbabilities(n/2, operations);
        Vector<Vector<Integer>> output = null;
        Boolean uniqueSol = false;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            for (int iters = 0; iters < 200 && !uniqueSol; ++iters) {
                final Vector<Vector<Integer>> finalOutput = defineRegions(solution, operations, scaledProb);
                // Skip iteration if could not define regions
                if (finalOutput == null) {
                    continue;  
                }
                Callable<Boolean> task = () -> {  
                    Vector<Region> regions = translator.translateKenken(finalOutput);
                    GSolver solver = new GSolver(regions, n, solution);
                    return solver.isUnique();
                };
                Future<Boolean> future = executor.submit(task);
                try {
                    uniqueSol = future.get((n <= 12) ? 10 : (n <= 15) ? 30 : 60, TimeUnit.SECONDS);
                    if (uniqueSol) output = finalOutput;
                } catch (TimeoutException e) {
                    // Handle timeout
                    future.cancel(true); // Cancel the task
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            
        } finally {
            executor.shutdown(); // Shutdown the executor service
        }
        if(output == null){
            return output;
        }
        return setCanonicalFormat(output);
    }

    // Returns the canonical format of the standard format (sets a canonical order in the regions
    // and cells within regions so that the system can detect repeated kenkens)
    private static Vector<Vector<Integer>> setCanonicalFormat(Vector<Vector<Integer>> integerFormat) {
        Vector<Map.Entry<Coordinates, Integer>> vector = new Vector<>();
        // We ignore the first line with N and R
        for (int i = 1; i < integerFormat.size(); ++i) {
            // We check every region
            Vector<Coordinates> v = new Vector<Coordinates>();
            for (int j = 3; j < integerFormat.get(i).size(); j += 2) {
                v.add(new Coordinates(integerFormat.get(i).get(j), integerFormat.get(i).get(j+1)));
            }
            // We get every cell of the region and sort them following the criteria in the documentation
            Collections.sort(v, new Comparator<Coordinates>() {
                @Override
                public int compare(Coordinates p1, Coordinates p2) {
                    if (p1.getX() != p2.getX()) {
                        return Integer.compare(p1.getX(), p2.getX());
                    } else {
                        return Integer.compare(p1.getY(), p2.getY());
                    }
                }
            });
            // We store which is the "smaller" cell of the region
            vector.add(new AbstractMap.SimpleEntry<>(v.get(0), i));
            // We overwrite the cells in order
            int k = 0;
            for (int j = 3; j < integerFormat.get(i).size(); j += 2) {
                integerFormat.get(i).set(j, v.get(k).getX());
                integerFormat.get(i).set(j+1, v.get(k).getY());
                ++k;
            }
        }
        // We sort the regions
        Collections.sort(vector, new Comparator<Map.Entry<Coordinates, Integer>>() {
            @Override
            public int compare(Map.Entry<Coordinates, Integer> p1, Map.Entry<Coordinates, Integer> p2) {
                if (!p1.getKey().equals(p2.getKey())) {
                    if (p1.getKey().getX() != p2.getKey().getX()) {
                        return Integer.compare(p1.getKey().getX(), p2.getKey().getX());
                    } else {
                        return Integer.compare(p1.getKey().getY(), p2.getKey().getY());
                    }
                } else {
                    return p1.getValue().compareTo(p2.getValue());
                }
            }
        });
        // We build and return the canonical form of the format
        Vector<Vector<Integer>> canonicalFormat = new Vector<Vector<Integer>>();
        canonicalFormat.add(integerFormat.get(0));
        for (int i = 0; i < vector.size(); ++i) {
            canonicalFormat.add(integerFormat.get(vector.get(i).getValue()));
        }
        return canonicalFormat;
    }

    // Expands the region until we reach its size or we have no available directions left
    private static void expandRegion(int[] regionSize, Boolean[][] vis, Vector<Integer> coords, int i, int j) {
        int n = vis.length;
        if (regionSize[0] == 0 || i < 0 || i >= n || j < 0 || j >= n || vis[i][j]) return;
        coords.add(i+1); coords.add(j+1);
        vis[i][j] = true;
        --regionSize[0];

        shuffleArray(directions);
        for (int[] dir : directions) {
            if (regionSize[0] == 0) break;
            expandRegion(regionSize, vis, coords, i + dir[0], j + dir[1]);
        }
    }

    // Shuffles ramdomly the rows of an array int[][]
    private static void shuffleArray(int[][] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            swap(arr, index, i);
        }
    }

    // Swaps the rows i and j of the array arr
    private static void swap(int[][] arr, int i, int j) {
        int[] temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /*
    public static void main(String[] args) {
        int n = 6;
        Vector<Integer> operations = new Vector<>(java.util.List.of(0, 4,6));

        long startTime = System.currentTimeMillis();
        int[][] solution = latinSquare(n);
        Vector<Vector<Integer>> result = generateRandom(n, operations, solution);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + elapsedTime + " milliseconds");


        if (result == null) System.out.println("Could not generate Kenken, try again with different values");
        else {
            // Print the result
            for (Vector<Integer> vec : result) {
                for (int num : vec) {
                    System.out.print(num + " ");
                }
                System.out.println();
            }
        }
    }
*/
}