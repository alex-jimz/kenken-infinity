package LogicTier.GSolver;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.Region.Region;
import LogicTier.Solver.Solver;

public class GSolver extends Solver {
    // Data
    public MPSolver solver;
    public MPVariable[][][] boolVars;   // [row][col][val]
    public MPVariable[][] vars;         // [row][col]
    // boardFactorization[i] is the prime factorization of i+1 (ex: for 14, [0,1,0,0,0,0,1,0,...])
    public int[][] boardFactorization;
    public final double infinity = java.lang.Double.POSITIVE_INFINITY; 

    // Constructor without solution
    public GSolver(Vector<Region> r, int boardSize) {
        super(r,boardSize);
        Loader.loadNativeLibraries();
        this.solver = MPSolver.createSolver("BOP");
        this.boolVars = new MPVariable[n][n][n];
        this.vars = new MPVariable[n][n];
        this.boardFactorization = new int[n][n];
        this.prefactorization();
    }

    // Constructor with solution
    public GSolver(Vector<Region> r, int boardSize, int[][] solution) {
        super(r, boardSize, solution);
        Loader.loadNativeLibraries();
        this.solver = MPSolver.createSolver("BOP");
        this.boolVars = new MPVariable[n][n][n];
        this.vars = new MPVariable[n][n];
        this.boardFactorization = new int[n][n];
        this.prefactorization();
    }

    // Initialize the variables for the MIP
    private void initializeVariables() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    this.boolVars[i][j][k] = this.solver.makeBoolVar("");
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.vars[i][j] = this.solver.makeIntVar(1.0, (double)(n), "");
            }
        }
    }
    
    // Sets the Latin Square Constraints
    private void setLatinSquareConstraints() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Constraint: each cell contains just one value
                MPConstraint justOneValuePerCell1 = this.solver.makeConstraint(-this.infinity, 1, "");
                MPConstraint justOneValuePerCell2 = this.solver.makeConstraint(1, this.infinity, "");
                // Constraint: the rows don't have repeated values
                MPConstraint valueNotRepeatedRow1 = this.solver.makeConstraint(-this.infinity, 1, "");
                MPConstraint valueNotRepeatedRow2 = this.solver.makeConstraint(1, this.infinity, "");
                // Constraint: the columns don't have repeated values
                MPConstraint valueNotRepeatedCol1 = this.solver.makeConstraint(-this.infinity, 1, "");
                MPConstraint valueNotRepeatedCol2 = this.solver.makeConstraint(1, this.infinity, "");
                // Constraint: if boolVars[i][j][k] = 1, then vars[i][j] = k+1
                MPConstraint varMatchesBoolVar1 = this.solver.makeConstraint(-this.infinity, 0, "");
                MPConstraint varMatchesBoolVar2 = this.solver.makeConstraint(0, this.infinity, "");
                
                
                for (int k = 0; k < n; k++) {
                    // boolVars[i][j][0] + ... + boolVars[i][j][n-1] <= 1
                    justOneValuePerCell1.setCoefficient(this.boolVars[i][j][k], 1);
                    // boolVars[i][j][0] + ... + boolVars[i][j][n-1] >= 1
                    justOneValuePerCell2.setCoefficient(this.boolVars[i][j][k], 1);

                    // boolVars[i][0][j] + ... + boolVars[i][n-1][j] <= 1
                    valueNotRepeatedRow1.setCoefficient(this.boolVars[i][k][j], 1);
                    // boolVars[i][0][j] + ... + boolVars[i][n-1][j] >= 1
                    valueNotRepeatedRow2.setCoefficient(this.boolVars[i][k][j], 1);

                    // boolVars[0][i][j] + ... + boolVars[n-1][i][j] <= 1
                    valueNotRepeatedCol1.setCoefficient(this.boolVars[k][i][j], 1);
                    // boolVars[0][i][j] + ... + boolVars[n-1][i][j] >= 1
                    valueNotRepeatedCol2.setCoefficient(this.boolVars[k][i][j], 1);

                    // 1*boolVars[i][j][0] + 2*boolVars[i][j][1] ... + n*boolVars[i][j][n-1] - vars[i][j] <= 0
                    varMatchesBoolVar1.setCoefficient(this.boolVars[i][j][k], k + 1);
                    // 1*boolVars[i][j][0] + 2*boolVars[i][j][1] ... + n*boolVars[i][j][n-1] - vars[i][j] >= 0
                    varMatchesBoolVar2.setCoefficient(this.boolVars[i][j][k], k + 1);
                }
                varMatchesBoolVar1.setCoefficient(this.vars[i][j], -1);
                varMatchesBoolVar2.setCoefficient(this.vars[i][j], -1);
            }
        }
    }

    // Sets the constraints for each region, based on the operation
    private void setRegionConstraints() {
        for (Region r : regions) {
            r.getOperation().linearize(this, r);
        }
    }

    // Returns the prime factorization
    public int[] primeFactorization(int target) {
        int[] primes = new int[n]; // not all the numbers are prime (but those which are not prime will have a 0)
        int rem = target;
        int q = 2;
        while (rem != 1 && q-1 < n) {
            if (rem % q == 0) {
                primes[q - 1]++;
                rem /= q;
            }
            else q++;
        }
        return primes;
    }

    // Precalculates the factorization of 1,...,n to use it multiple times
    private void prefactorization() {
        // the factorization of 1 is [1,0,...,0]
        this.boardFactorization[0][0] = 1;
        // then we use the primeFactorization method to do the others
        for (int i = 1; i < n; i++) {
            this.boardFactorization[i] = this.primeFactorization(i + 1);
        }
    }

    @Override
    public void solve() {
        this.initializeVariables();
        this.setLatinSquareConstraints(); 
        this.setRegionConstraints();

        final MPSolver.ResultStatus status = this.solver.solve();

        this.solution = new int[n][n];
        if (status == MPSolver.ResultStatus.OPTIMAL) {
            this.solvedStatus = true;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    solution[i][j] = (int) this.vars[i][j].solutionValue();
                }
            }
        }
        else {
            this.solvedStatus = false;
        }
    }

    @Override
    public boolean isUnique() {
        if (!solvedStatus) {
            this.initializeVariables();
            this.setLatinSquareConstraints(); 
            this.setRegionConstraints();
        }

        // we introduce a "no-good" constraint that disallows that solution
        MPConstraint uniqueness = this.solver.makeConstraint(1.0 - n*n, this.infinity, "");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                uniqueness.setCoefficient(this.boolVars[i][j][this.solution[i][j] - 1], -1); 
            }
        }

        // the puzzle has a unique solutions if and only if the problem is infeasible
        final MPSolver.ResultStatus status = this.solver.solve();
        return status == MPSolver.ResultStatus.INFEASIBLE;
    }
}