package LogicTier.GOperation;

import com.google.ortools.linearsolver.MPVariable;
import com.google.ortools.linearsolver.MPConstraint;

import java.util.*;
import LogicTier.GSolver.GSolver;
import LogicTier.Region.Region;

public final class Division extends GOperation {
    private final int numOperands = 2;  // fixed number of operands

    @Override
    public String getName() {
        return "Division (\u00F7)";
    }

    @Override
    public String getSymbol() {
        return "\u00F7";
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
        if (operands.get(1) == 0) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }
        if (operands.get(0) % operands.get(1) != 0) {
            if (operands.get(1) % operands.get(0) != 0) {
                throw new IllegalArgumentException("The division is not exact");
            }
            return operands.get(1) / operands.get(0);
        }
        
        return operands.get(0) / operands.get(1);
    }

    @Override
    public void linearize(GSolver s, Region r) {
        double M = r.getTarget() * (s.n - (s.n%r.getTarget())) - s.n/r.getTarget();
        MPVariable u = s.solver.makeBoolVar("");

        // y_0 - target*y_1 + M*u >= 0
        MPConstraint divConstraint1 = s.solver.makeConstraint(0.0, s.infinity, "");
        // y_0 - target*y_1 - M*u <= 0
        MPConstraint divConstraint2 = s.solver.makeConstraint(-s.infinity, 0.0,  "");
        // y_1 - target*y_0 - M*u >= -M
        MPConstraint divConstraint3 = s.solver.makeConstraint(-M, s.infinity,  "");
        // y_1 - target*y_0 + M*u <= M
        MPConstraint divConstraint4 = s.solver.makeConstraint(-s.infinity, M,  "");

        divConstraint1.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], 1);
        divConstraint1.setCoefficient(s.vars[r.getCells().get(1).getX()][r.getCells().get(1).getY()], -r.getTarget());
        divConstraint1.setCoefficient(u, M);

        divConstraint2.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], 1);
        divConstraint2.setCoefficient(s.vars[r.getCells().get(1).getX()][r.getCells().get(1).getY()], -r.getTarget());
        divConstraint2.setCoefficient(u, -M);

        divConstraint3.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], -r.getTarget());
        divConstraint3.setCoefficient(s.vars[r.getCells().get(1).getX()][r.getCells().get(1).getY()], 1);
        divConstraint3.setCoefficient(u, -M);

        divConstraint4.setCoefficient(s.vars[r.getCells().get(0).getX()][r.getCells().get(0).getY()], -r.getTarget());
        divConstraint4.setCoefficient(s.vars[r.getCells().get(1).getX()][r.getCells().get(1).getY()], 1);
        divConstraint4.setCoefficient(u, M);
    }
}