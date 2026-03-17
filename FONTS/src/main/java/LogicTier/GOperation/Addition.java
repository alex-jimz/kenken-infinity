package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.Coordinates.Coordinates;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class Addition extends GOperation {
    
    @Override
    public String getName() {
        return "Addition (+)";
    }

    @Override
    public String getSymbol() {
        return "+";
    }
    
    @Override
    public int operate(Vector<Integer> operands) {
        if (!correctNumOperands(operands.size())) {
            throw new IllegalArgumentException("Number of operands not correct");
        }
        return operands.stream().reduce(0, Integer::sum);
    }

    @Override
    public void linearize(GSolver s, Region r) {
        MPConstraint sumConstraint1 = s.solver.makeConstraint(-s.infinity, r.getTarget(), "");
        MPConstraint sumConstraint2 = s.solver.makeConstraint(r.getTarget(), s.infinity,  "");

        for (Coordinates c : r.getCells()) {
            // sum of the values of the cells of the region <= target
            sumConstraint1.setCoefficient(s.vars[c.getX()][c.getY()], 1);
            // sum of the values of the cells of the region >= target
            sumConstraint2.setCoefficient(s.vars[c.getX()][c.getY()], 1);
        } 
    }
}