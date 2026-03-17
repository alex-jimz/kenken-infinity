package LogicTier.GOperation;

import LogicTier.GSolver.GSolver;
import LogicTier.Operation.Operation;
import LogicTier.Region.Region;
import LogicTier.Solver.Solver;

public abstract class GOperation extends Operation {
    public abstract void linearize(GSolver s, Region r);

    @Override
    public void linearize(Solver s, Region r) {
        if (!(s instanceof GSolver)) return;
        GSolver gsolver = (GSolver) s;
        linearize(gsolver, r);
    }
}