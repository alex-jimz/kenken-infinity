package LogicTier.Verifier;
import java.util.*;
import LogicTier.Coordinates.Coordinates;
import LogicTier.Operation.Operation;


public class Verifier {

    private Vector<Vector<Integer>> integerFormat;
    private Vector<Vector<Integer>> hintsPositions;

    public Verifier(){
        integerFormat = new Vector<>();
        hintsPositions = new Vector<>();
    }

    private Vector<Vector<String>> chop (Vector<String> stringFormat){

        Vector<Vector<String>> ChoppedStringFormat = new Vector<>();
        // Loop through each string in the input vector
        for (String str : stringFormat) {
            // Split the string by space 
            String[] parts = str.split(" ");
            Vector<String> partsVector = new Vector<>(Arrays.asList(parts));
            ChoppedStringFormat.add(partsVector);
        }
        return ChoppedStringFormat;
    }

    public boolean verify (Vector<String> stringFormat){

        Vector<Vector<String>> ChoppedStringFormat = new Vector<>();
        integerFormat = new Vector<>();
        hintsPositions = new Vector<>();

        ChoppedStringFormat = chop(stringFormat);

        if (ChoppedStringFormat.size() == 0){
            return false;
        }

        if (ChoppedStringFormat.get(0).size() != 2){
                return false;
        }

        int N;
        int R;

        try {
                N = Integer.parseInt(ChoppedStringFormat.get(0).get(0));
                R = Integer.parseInt(ChoppedStringFormat.get(0).get(1));
       } catch (NumberFormatException e){
                return false;
        }  
        Vector<Integer> header = new Vector<>(Arrays.asList(N, R));
        integerFormat.add(header);

        //checks condtions for N and R
        if (N < 1 || R > N*N || R < 1) return false;

        int cellsCovered = 0;
        boolean[][] appearanceMatrix = new boolean[N][N];
        //initialize the appearance matrix to all false
        for (boolean[] row : appearanceMatrix) {
            Arrays.fill(row, false);
        }

        if (ChoppedStringFormat.size() != R + 1) {
            return false;
        } 

        //treats each region
        for  (int i = 1; i < ChoppedStringFormat.size(); ++i){

            Vector<Integer> region = new Vector<>();
            int operValue;
            int e;
            int result;

            try {
                //read the header of the region
                operValue = Integer.parseInt(ChoppedStringFormat.get(i).get(0));
                result = Integer.parseInt(ChoppedStringFormat.get(i).get(1));
                e = Integer.parseInt(ChoppedStringFormat.get(i).get(2));
                region.add(operValue);
                region.add(result);
                region.add(e);

            } catch (NumberFormatException ex){
                return false;
            }   

            if (result < 0) return false;

            //check that the operation read is valid and that the number of cells of the region is correct
            if (!Operation.isValidOperation(operValue) || !Operation.correctNumOperandsById(operValue, e)) {
                return false;
            }

            boolean[][]  navigationMatrix = new boolean[N][N];
            for (boolean[] row : navigationMatrix) {
                 Arrays.fill(row, false);
            }

            cellsCovered += e;
            Set<int[]> region_coords = new HashSet<>();
           
        
            boolean isParenthesisReady = false;

            int coord_x = -1;
            int coord_y = -1;

            int j = 3;

            //translates the region into integer format and checks that the coordinates are valid
            while (j < ChoppedStringFormat.get(i).size()){
                
               if (!isParenthesisReady){
                    try {
                        coord_x = Integer.parseInt(ChoppedStringFormat.get(i).get(j));
                        coord_y = Integer.parseInt(ChoppedStringFormat.get(i).get(j+1));
                        isParenthesisReady = true;
                        j = j + 2;
                    } catch (NumberFormatException | IndexOutOfBoundsException ex){
                        return false;
                    }
               } else if (isParenthesisReady){
                    if (!(ChoppedStringFormat.get(i).get(j).charAt(0) == '[')){
                        try {
                            coord_x = Integer.parseInt(ChoppedStringFormat.get(i).get(j));
                            coord_y = Integer.parseInt(ChoppedStringFormat.get(i).get(j+1));
                            j = j + 2;
                        } catch (NumberFormatException | IndexOutOfBoundsException ex){
                            return false;
                        }
                    } else {
                        int hint ;
                        try {
                            String StringHint = ChoppedStringFormat.get(i).get(j).substring(1, ChoppedStringFormat.get(i).get(j).length()-1);
                            hint = Integer.parseInt(StringHint);
                            Vector<Integer> hintVec = new Vector<>();
                            hintVec.add(coord_x-1);
                            hintVec.add(coord_y-1);
                            hintVec.add(hint);
                            hintsPositions.add(hintVec);
                        } catch ( NumberFormatException | StringIndexOutOfBoundsException ex){
                            return false;
                        }
                        char c = ChoppedStringFormat.get(i).get(j).charAt(ChoppedStringFormat.get(i).get(j).length()-1);
                        if (!(c == ']')){
                            return false;
                        } else{
                            isParenthesisReady = false;
                            j = j + 1;
                        }
    
                 }
                }

                
                if (isParenthesisReady){
                    // checks that the coordinate is within bounds
                    if (coord_x < 1 || coord_x > N || coord_y < 1 || coord_y > N){
                        return false;
                    }
                    //checks that the coordinate has not been used in another region
                    if (appearanceMatrix[coord_x-1][coord_y-1]){
                     return false;
                     }
                    else {
                        appearanceMatrix[coord_x-1][coord_y-1] = true;
                        navigationMatrix[coord_x-1][coord_y-1] = true;
                        region_coords.add(new int[]{coord_x-1, coord_y-1});
                    }
                    region.add(coord_x);
                    region.add(coord_y);
                }
            }

            //perfoms a DFS to check whether the region has only a connex component
            boolean isConnex =  checkConnexComponent (navigationMatrix, region_coords);
            if (!isConnex) {
                return false;
            }
            if (region.size() != 2*e + 3){
                return false;
            } 
            integerFormat.add(region); 
        }
        //checks that all the regions cover the totatility of the board
        if (cellsCovered != N*N) {
            return false;
        }
        return true;
    } 

    private static boolean checkConnexComponent(boolean[][] grid, Set<int[]> coordinates) {
        //empty set is connex by definition
        if (coordinates.isEmpty()) {
            return true; 
        }

        // Perform DFS to check if all coordinates are in the same connected component
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[] start = coordinates.iterator().next(); 
        dfs(grid, visited, start[0], start[1]);

        // Check if all coordinates are visited
        for (int[] coord : coordinates) {
            if (!visited[coord[0]][coord[1]]) {
                return false;
            }
        }
        return true;
    }

     private static void dfs(boolean[][] grid, boolean[][] visited, int row, int col) {
       
        int rows = grid.length;
        int cols = grid[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col] || grid[row][col] == false) {
            return;
        }
        
        visited[row][col] = true;
        //perfons the DFS in the four directions
        dfs(grid, visited, row + 1, col); 
        dfs(grid, visited, row - 1, col); 
        dfs(grid, visited, row, col + 1); 
        dfs(grid, visited, row, col - 1); 
    }

    public Vector<Vector<Integer>> getHintsPositions(){
        return hintsPositions;
    }

    public Vector<Vector<Integer>> getIntegerFormat(){
        return setCanonicalFormat(integerFormat);
    }

    private Vector<Vector<Integer>> setCanonicalFormat( Vector<Vector<Integer>> integerFormat){
        Vector<Map.Entry<Coordinates, Integer>> vector = new Vector<>();
        //We ignore the first line with N and R
        for(int i = 1; i < integerFormat.size(); ++i){
            //We check every region
            Vector<Coordinates> v = new Vector<Coordinates>();
            for(int j = 3; j < integerFormat.get(i).size(); j += 2){
                v.add(new Coordinates(integerFormat.get(i).get(j), integerFormat.get(i).get(j+1)));
            }
            //We get every cell of the region and sort them following the criteria in the coumentation
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
            //We store which is the "smaller" cell of the region
            vector.add(new AbstractMap.SimpleEntry<>(v.get(0), i));
            //We overwrite the cells in order
            int k = 0;
            for(int j = 3; j < integerFormat.get(i).size(); j += 2){
                integerFormat.get(i).set(j, v.get(k).getX());
                integerFormat.get(i).set(j+1, v.get(k).getY());
                ++k;
            }
        }
        //We sort the regions
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
        //We build and return the canonical form of the format
        Vector<Vector<Integer>> canonicalFormat = new Vector<Vector<Integer>>();
        canonicalFormat.add(integerFormat.get(0));
        for(int i = 0; i < vector.size(); ++i){
            canonicalFormat.add(integerFormat.get(vector.get(i).getValue()));
        }
        return canonicalFormat;
    }
}

