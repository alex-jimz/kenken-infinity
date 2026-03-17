package LogicTier.Region;
import java.util.*;

import LogicTier.Coordinates.Coordinates;
import LogicTier.Operation.Operation;

import java.lang.IllegalArgumentException;

public class Region {
    private Operation op;
    private int target;
    private Vector <Coordinates> cells;

    public Region() {
        op = null;
        target = 0;
        cells = new Vector<Coordinates>();
    }

    public Region(int opId, int res, Vector<Integer> positions) {
        op = Operation.getOperationById(opId);
        target = res;
        if (positions.size()%2 != 0) throw new IllegalArgumentException("Vector length is not even.");
        int n = positions.size()/2;
        cells = new Vector<Coordinates>();
        for (int i = 0; i < n; ++i) {
            cells.add(new Coordinates(positions.get(2*i), positions.get(2*i+1))); 
        }
    }

    public Operation getOperation() {
        return op;
    }

    public Integer getTarget() {
        return target;
    }

    public Vector<Coordinates> getCells() {
        return cells;
    }

}

