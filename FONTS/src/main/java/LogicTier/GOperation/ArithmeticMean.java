package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.Coordinates.Coordinates;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class ArithmeticMean extends GOperation {
    
    @Override
    public String getName() {
        return "Arithmetic Mean (avg)";
    }

    @Override
    public String getSymbol() {
        return "avg";
    }

    @Override
    public int operate(Vector<Integer> operands) {
        if (!correctNumOperands(operands.size())) {
            throw new IllegalArgumentException("Number of operands not correct");
        }
        int sum = operands.stream().reduce(0, Integer::sum);
        if (sum % operands.size() != 0) {
            throw new IllegalArgumentException("The division is not exact");
        }
        return sum / operands.size();
    }

    @Override
    public void linearize(GSolver s, Region r) {
        MPConstraint sumConstraint1 = s.solver.makeConstraint(-s.infinity, r.getTarget()*r.getCells().size() , "");
        MPConstraint sumConstraint2 = s.solver.makeConstraint(r.getTarget()*r.getCells().size(), s.infinity,  "");

        for (Coordinates c : r.getCells()) {
            // sum of the values of the cells of the region <= target*amount_cells
            sumConstraint1.setCoefficient(s.vars[c.getX()][c.getY()], 1);
            // sum of the values of the cells of the region >= target*amount_cells
            sumConstraint2.setCoefficient(s.vars[c.getX()][c.getY()], 1);
        } 
    }
}