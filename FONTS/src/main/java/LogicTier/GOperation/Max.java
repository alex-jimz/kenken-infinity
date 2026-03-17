package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.Coordinates.Coordinates;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class Max extends GOperation {
    
    @Override
    public String getName() {
        return "Maximum (max)";
    }

    @Override
    public String getSymbol() {
        return "max";
    }
    
    @Override
    public int operate(Vector<Integer> operands) {
        if (!correctNumOperands(operands.size())) {
            throw new IllegalArgumentException("Number of operands not correct");
        }
        return Collections.max(operands);
    }

    @Override
    public void linearize(GSolver s, Region r) {
        // the value of each cell <= target
        for (Coordinates c : r.getCells()) {
            MPConstraint valueSmallerThanTarget = s.solver.makeConstraint(-s.infinity, r.getTarget() , "");
            valueSmallerThanTarget.setCoefficient(s.vars[c.getX()][c.getY()], 1);
        } 

        // there exists a cell whose value is target
        MPConstraint existsValueTarget = s.solver.makeConstraint(1.0, s.infinity,  "");
        for (Coordinates c : r.getCells()) {
            existsValueTarget.setCoefficient(s.boolVars[c.getX()][c.getY()][r.getTarget()-1], 1);
        }     
    }
}