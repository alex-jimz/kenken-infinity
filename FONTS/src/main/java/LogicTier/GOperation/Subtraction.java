package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class Subtraction extends GOperation {
    private final int numOperands = 2;  // fixed number of operands

    @Override
    public String getName() {
        return "Substraction (-)";
    }

    @Override
    public String getSymbol() {
        return "-";
    }
    
    @Override
    public int getMinOperands() {
        return numOperands;
    }

    @Override
    public int getMaxOperands() {
        return numOperands;
    }

    @Override
    public int operate(Vector<Integer> operands) {
        if (!correctNumOperands(operands.size())) {
            throw new IllegalArgumentException("Number of operands not correct");
        }
        return Math.abs(operands.get(0) - operands.get(1));
    }

    @Override
    public void linearize(GSolver s, Region r) {
        MPConstraint difConstraint1 = s.solver.makeConstraint(-s.infinity, r.getTarget(), "");
        MPConstraint difConstraint2 = s.solver.makeConstraint(r.getTarget(), s.infinity,  "");
        
        difConstraint1.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], 1);
        difConstraint2.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], 1);

        difConstraint1.setCoefficient(s.vars[r.getCells().get(1).getX()][r.getCells().get(1).getY()], -1);
        difConstraint2.setCoefficient(s.vars[r.getCells().get(1).getX()][r.getCells().get(1).getY()], -1);    

        MPVariable u = s.solver.makeBoolVar("");
        difConstraint1.setCoefficient(u, (double) 2*r.getTarget());
        difConstraint2.setCoefficient(u, (double) 2*r.getTarget());

        // y_0 - y_1 = target - 2*target*u
    }
}
