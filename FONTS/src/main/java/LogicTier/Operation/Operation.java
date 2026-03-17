package LogicTier.Operation;

import java.util.*;

import LogicTier.GOperation.*;
import LogicTier.Region.Region;
import LogicTier.Solver.Solver;

public abstract class Operation {
    // Returns the name of the operation
    public abstract String getName();
    // Returns the symbol of the operation
    public abstract String getSymbol();
    // Returns the result of applying the operation to the operands
    public abstract int operate(Vector<Integer> operands);
    // Adds to the solver s the restriction of the region r
    public abstract void linearize(Solver s, Region r);

    // Static map to store operation instances by ID
    private static final Map<Integer, Operation> opMap = new HashMap<>();

    // Static initializer block to populate the operation map
    static {
        opMap.put(0, new Identity());
        opMap.put(1, new Addition());
        opMap.put(2, new Subtraction());
        opMap.put(3, new Product());
        opMap.put(4, new Division());
        opMap.put(5, new ArithmeticMean());
        opMap.put(6, new Max());
    }

    // Static method to get an operation instance by ID
    public static Operation getOperationById(int opId) {
        Operation operation = opMap.get(opId);
        if (operation == null) {
            throw new IllegalArgumentException("Invalid operation ID");
        }
        return operation;
    }

    // Static method to operate using the operation with id = opId
    public static int operateById(int opId, Vector<Integer> operands) {
        Operation operation = getOperationById(opId);
        return operation.operate(operands);
    }

    // Method to get the operation ID by class name
    public int getOperationId() {
        for (Map.Entry<Integer, Operation> entry : opMap.entrySet()) {
            if (entry.getValue().getClass().equals(getClass())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Operation class not found in the map");
    }

    // Static method to get the operand limit by operation ID
    public static boolean correctNumOperandsById(int opId, int operands) {
        Operation operation = getOperationById(opId);
        return operation.correctNumOperands(operands);
    }

    // Static method to get the operation count
    public static int getOperationCount() {
        return opMap.size();
    }

    // Static method that returns true if opId is the id of an existing operation
    public static boolean isValidOperation(int opId) {
        return opMap.get(opId) != null;
    }

    // Returns the minimum operands of the operation
    public int getMinOperands() {
        return 2;
    }

    // Returns the maximum operands of the operation
    public int getMaxOperands() {
        return Integer.MAX_VALUE;
    }

    // Returns true if the operation supports an amount of "operands" operands
    public boolean correctNumOperands(int operands) {
        return getMinOperands() <= operands && operands <= getMaxOperands();
    }

    // Returns the IDs of all existing operations
    public static int[] getOperationIds() {
        int[] ids = new int[getOperationCount()];
        int index = 0;
        for (int key : opMap.keySet()) {
            ids[index++] = key;
        }
        return ids;
    }

    // Returns the names of all existing operations
    public static String[] getOperationNames() {
        String[] names = new String[getOperationCount()];
        int index = 0;
        for (Operation op : opMap.values()) {
            names[index++] = op.getName();
        }
        return names;
    }

    // Returns the symbols of all existing operations
    public static String[] getOperationSymbols() {
        String[] sym = new String[getOperationCount()];
        int index = 0;
        for (Operation op : opMap.values()) {
            sym[index++] = op.getSymbol();
        }
        return sym;
    }
}