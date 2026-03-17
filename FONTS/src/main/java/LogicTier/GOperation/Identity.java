package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class Identity extends GOperation {
    private final int numOperands = 1;  // Identity requires exactly 1 operand

    @Override
    public String getName() {
        return "Identity";
    }

    @Override
    public String getSymbol() {
        return "";
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
        // Identity operation simply returns the input operand
        return operands.get(0);
    }

    @Override
    public void linearize(GSolver s, Region r) {
        MPConstraint varEqualsTarget1 = s.solver.makeConstraint(-s.infinity, r.getTarget(), "");
        MPConstraint varEqualsTarget2 = s.solver.makeConstraint(r.getTarget(), s.infinity, "");
        varEqualsTarget1.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], 1);
        varEqualsTarget2.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], 1);
    }
}