package LogicTier.Kenken;
import java.util.*;

import LogicTier.Region.Region;

public class Kenken {
    private int idKenken;
    private int boardSize;
    private int[] operations;
    private Vector<Region> board;
    private int solution[][];
    
    public Kenken(int idKenken, int boardSize, Vector<Region> board, int[][] solution) {
        this.idKenken = idKenken;
        this.boardSize = boardSize;
        this.board = board;
        gatherOperations();
        this.solution = solution;
    }

    private void gatherOperations() {
        int op;
        Set<Integer> ops = new HashSet<>();
        for (int i = 0; i < board.size(); ++i) {
            op = board.get(i).getOperation().getOperationId();
            ops.add(op);
        }
        operations = new int[ops.size()];
        int j = 0;
        for (int o: ops) {
            operations[j] = o;
            ++j;
        }
        Arrays.sort(operations);
    }

    public int getIdKenken() {
            return idKenken;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int[] getOperations() {
        return operations;
    }

    public Vector<Region> getBoard() {
        return board;
    }

    public int[][] getSolution() {
        return solution;
    }

}