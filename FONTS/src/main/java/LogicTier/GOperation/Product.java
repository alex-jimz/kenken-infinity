package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.Coordinates.Coordinates;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class Product extends GOperation {
    
    @Override
    public String getName() {
        return "Product (x)";
    }

    @Override
    public String getSymbol() {
        return "x";
    }
    
    @Override
    public int operate(Vector<Integer> operands) {
        if (!correctNumOperands(operands.size())) {
            throw new IllegalArgumentException("Number of operands not correct");
        }
        return operands.stream().reduce(1, (a, b) -> a * b);
    }

    @Override
    public void linearize(GSolver s, Region r) {
        int[] powers = s.primeFactorization(r.getTarget());

        for (int i = 0; i < powers.length; i++) {
            if (powers[i] != 0) {
                MPConstraint prodConstraint1 = s.solver.makeConstraint(-s.infinity, powers[i], "");
                MPConstraint prodConstraint2 = s.solver.makeConstraint(powers[i], s.infinity,  "");

                for (int k = 0; k < Math.min(s.n,r.getTarget()); k++) {
                    if ((k + 1) % (i + 1) == 0 && r.getTarget() % (k + 1) == 0) {
                        for (Coordinates c : r.getCells()) {
                            prodConstraint1.setCoefficient(s.boolVars[c.getX()][c.getY()][k], s.boardFactorization[k][i]);
                            prodConstraint2.setCoefficient(s.boolVars[c.getX()][c.getY()][k], s.boardFactorization[k][i]);
                        }
                    }
                }
            }
        }
    }
}
