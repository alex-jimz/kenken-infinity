package LogicTier.Solver;

import java.util.*;
import LogicTier.Region.Region;

public abstract class Solver {
    // Data
    public int n;                       // Size of the potential Kenken
    protected Vector<Region> regions;   // Vector of regions that defines the potential Kenken
    protected boolean solvedStatus;
    protected int[][] solution;

    // Constructor without solution (case: we want to solve the Kenken)
    public Solver(Vector<Region> r, int boardSize) {
        this.n = boardSize;
        this.regions = r;
        this.solvedStatus = false;
    }

    // Constructor with solution (case: we have a solution and we want to check if it is unique)
    public Solver(Vector<Region> r, int boardSize, int[][] solution) {
        this.n = boardSize;
        this.regions = r;
        this.solvedStatus = false;
        this.solution = solution;
    }

    // Solves the kenken
    public abstract void solve();
        
    // Returns true if the solution is unique and false otherwise
    public abstract boolean isUnique();

    public boolean isSolved() {
        return this.solvedStatus;
    }

    public int[][] getSolution() {
        return this.solution;
    }
}